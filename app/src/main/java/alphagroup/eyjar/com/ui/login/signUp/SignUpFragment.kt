package alphagroup.eyjar.com.ui.login.signUp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.viewModel.SignUpViewModel
import kotlinx.android.synthetic.main.sign_up_fragment.*

import android.widget.RadioGroup
import android.widget.Toast


class SignUpFragment : Fragment() {
    private var gender: String = "male"

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]


        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.maleBtn -> {
                    gender = "male"
                }
                R.id.femaleBtn -> {

                    gender = "female"
                }
            }
        })
    }

}