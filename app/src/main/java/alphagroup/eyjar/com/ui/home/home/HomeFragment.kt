package alphagroup.eyjar.com.ui.home.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.adapter.HomeAdapter
import alphagroup.eyjar.com.viewModel.HomeViewModel
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.toolbar
import kotlinx.android.synthetic.main.main_toolbar.view.*
import kotlinx.android.synthetic.main.network_layout.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener {
    private val TAG: String = HomeFragment::class.java.simpleName
    private lateinit var homeAdapter: HomeAdapter
    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        homeAdapter = HomeAdapter()
        viewModel.checkNetwork(requireActivity())
        listenOnMLD()
        dataRV.apply {
            layoutManager =
                GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
        }

        toolbar.title.text = resources.getString(R.string.home)
        networkLin.retryBtn.setOnClickListener(this)


        swipe.setOnRefreshListener() {
            viewModel.checkNetwork(requireActivity())
        }

        dataRV.apply {
            layoutManager =
                GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        viewModel.checkNetwork(requireActivity())
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
                    networkLin.visibility = View.GONE
                    dataRV.visibility = View.VISIBLE
                    collectData()
                }

                "validRequest" -> {
                }

                "invalidRequest" -> {
                    swipe.isRefreshing = false
                }
            }
        })
    }

    private fun collectData() {
        swipe.isRefreshing = true
        lifecycleScope.launch {
            viewModel.sendRequest()
            viewModel.MSF.collect {
                if (it != null) {
                    Log.d(
                        TAG,
                        "testTag collectData:  vm getData : ${it.size}"
                    )
                    homeAdapter.setList(it)
                    dataRV.adapter = homeAdapter
                    swipe.isRefreshing = false

                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(view: View?) {
        if (networkLin.retryBtn == view) {
            viewModel.checkNetwork(requireActivity())
        }
    }
}