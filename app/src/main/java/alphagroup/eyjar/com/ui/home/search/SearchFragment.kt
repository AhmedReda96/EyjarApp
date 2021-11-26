package alphagroup.eyjar.com.ui.home.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.adapter.SearchAdapter
import alphagroup.eyjar.com.viewModel.SearchViewModel
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_toolbar.view.*
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.android.synthetic.main.search_fragment.dataRV
import kotlinx.android.synthetic.main.search_fragment.networkLin
import kotlinx.android.synthetic.main.search_fragment.swipe
import kotlinx.android.synthetic.main.search_fragment.toolbar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val TAG: String = SearchFragment::class.java.simpleName
    private lateinit var searchAdapter: SearchAdapter
    private var query: String = ""
    private lateinit var viewModel: SearchViewModel
    private var parentJob: Job = Job()
    private var scope: CoroutineScope = CoroutineScope(Dispatchers.Main + parentJob)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        toolbar.title.text = resources.getString(R.string.search)
        searchAdapter = SearchAdapter()

        searchBar.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onQueryTextChange(newText: String): Boolean {
                swipe.isRefreshing = newText.isNotEmpty()
                scope.launch {
                    disableViews()
                    if (newText.isNotEmpty()) {
                        query = newText
                        delay(2000)
                        viewModel.checkNetwork(requireActivity())
                    } else {
                        disableViews()
                    }
                }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
        })

        dataRV.apply {
            layoutManager =
                GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            dataRV.adapter = searchAdapter
        }
        listenOnMLD()

    }

    private fun listenOnMLD() {

        viewModel.resultMLD.observe(requireActivity(), { result ->
            when (result) {
                "noInternetConnection" -> {
                    networkLin.visibility = View.VISIBLE
                    dataRV.visibility = View.GONE
                    swipe.isEnabled = false

                }
                "isInternetPresent" -> {
                    disableViews()
                    collectData()
                }
                "validRequest" -> {

                }
                "invalidRequest" -> {
                    swipe.isRefreshing = false
                    swipe.isEnabled = false
                    networkLin.visibility = View.GONE
                    dataRV.visibility = View.GONE
                    noData.visibility = View.VISIBLE
                }

            }
        })
    }


    private fun disableViews() {
        networkLin.visibility = View.GONE
        dataRV.visibility = View.GONE
        noData.visibility = View.GONE
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun collectData() {
        swipe.isRefreshing = true

        lifecycleScope.launch {
            viewModel.getSearchResponse(query)
            viewModel.MSF.collect {

                searchAdapter.setList(it)
                searchAdapter.notifyDataSetChanged()
                swipe.isRefreshing = false
                swipe.isEnabled = false
                dataRV.visibility = View.VISIBLE

            }

        }

    }

}