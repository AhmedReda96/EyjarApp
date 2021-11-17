package alphagroup.eyjar.com.ui.home.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.viewModel.SearchViewModel
import android.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.main_toolbar.view.*
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.android.synthetic.main.search_fragment.toolbar

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

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
        toolbar.title.text=resources.getString(R.string.search)


        searchBar.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {


            override fun onQueryTextChange(newText: String): Boolean {
                swipe.isRefreshing = newText.isNotEmpty()
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
        })

        carsRV.apply {
            layoutManager =
                GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
        }

    }

}