package alphagroup.eyjar.com.ui.home.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.viewModel.HomeViewModel
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.main_toolbar.view.*

class HomeFragment : Fragment(),View.OnClickListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()

    }

    private fun init() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        carsRV.apply {
            layoutManager =
                GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
        }
        filterBtn.setOnClickListener(this)
        toolbar.title.text=resources.getString(R.string.home)

    }

    override fun onClick(view: View?) {
        if (filterBtn==view){

        }

    }

}