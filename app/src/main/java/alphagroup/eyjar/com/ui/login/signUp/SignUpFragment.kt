package alphagroup.eyjar.com.ui.login.signUp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.utlis.TestLogin
import alphagroup.eyjar.com.utlis.showSnackBar
import alphagroup.eyjar.com.viewModel.SignUpViewModel
import android.os.Build
import kotlinx.android.synthetic.main.sign_up_fragment.*
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.mainLin


class SignUpFragment : Fragment(), View.OnClickListener {
    private var gender: String = "male"
    private lateinit var testLogin: TestLogin
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        testLogin = TestLogin(requireContext())

        signUpBtn.setOnClickListener(this)

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

        listenOnMLD()

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun listenOnMLD() {
        viewModel.resultMLD.observe(requireActivity(),{
            disableErrors()
            when (it) {
                "invalid name" -> {
                    nameLin.isErrorEnabled = true
                    nameLin.error = resources.getString(R.string.invalidName)

                }
                "invalid phoneNumber" -> {
                    phoneNumberLin.isErrorEnabled = true
                    phoneNumberLin.error = resources.getString(R.string.invalidPhoneNumber)

                }
                "invalid email" -> {
                    emailLin.isErrorEnabled = true
                    emailLin.error = resources.getString(R.string.invalidEmail)

                }
                "invalid password" -> {
                    passwordLin.isErrorEnabled = true
                    passwordLin.error = resources.getString(R.string.invalidPassword)

                }
                "invalid phoneOrPass" -> {
                    this.showSnackBar(mainLin,R.string.invalidPhoneOrPassword)
                }

                "valid data" -> {
                   disableErrors()
                    viewModel.checkNetwork(requireActivity())
                }
                "noInternetConnection" -> {
                    this.showSnackBar(mainLin,R.string.noInternetConnection)

                }
                "isInternetPresent" -> {
                        viewModel.sendRequest()
                }
                "validRequest" -> {
                    testLogin.setAuth(true)
                    findNavController().navigate(
                        R.id.action_signUpFragment_to_homeFragment
                    )

                }

            }

        })


    }

    private fun disableErrors() {
        phoneNumberLin.isErrorEnabled = false
        passwordLin.isErrorEnabled = false
        emailLin.isErrorEnabled = false
        nameLin.isErrorEnabled = false
    }
    override fun onClick(view: View?) {
        if (view == signUpBtn) {
            viewModel.checkData(
                name.text?.trim().toString(),
                phoneNumber.text?.trim().toString(),
                email.text?.trim().toString(),
                password.text?.trim().toString(),
            )
        }

    }

}