package alphagroup.eyjar.com.ui.login.signUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.commons.TestLogin
import alphagroup.eyjar.com.commons.showSnackBar
import alphagroup.eyjar.com.viewModel.SignUpViewModel
import android.app.ProgressDialog
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.sign_up_fragment.*
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(), View.OnClickListener {
    private var gender: String = "male"
    private lateinit var nameTxt: String
    private lateinit var emailTxt: String
    private lateinit var phoneTxt: String
    private lateinit var passwordTxt: String
    private lateinit var testLogin: TestLogin
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var progressDialog: ProgressDialog

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
        testLogin = TestLogin(requireContext())
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage(this.resources.getString(R.string.loading))
        progressDialog.setCancelable(false)
        val drawable = ProgressBar(requireActivity()).indeterminateDrawable.mutate()
        drawable.setColorFilter(
            ContextCompat.getColor(requireActivity(), R.color.black),
            PorterDuff.Mode.SRC_IN
        )
        progressDialog.setIndeterminateDrawable(drawable)

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
        viewModel.resultMLD.observe(requireActivity(), {
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

                "valid data" -> {
                    disableErrors()
                    viewModel.checkNetwork(requireActivity())
                }
                "noInternetConnection" -> {
                    this.showSnackBar(mainLin, R.string.noInternetConnection)

                }
                "isInternetPresent" -> {
                    progressDialog.show()
                    viewModel.sendRequest(nameTxt,phoneTxt,emailTxt,passwordTxt,gender)
                }
                "validRequest" -> {
                    progressDialog.dismiss()
                    findNavController().navigate(
                        R.id.action_signUpFragment_to_homeFragment
                    )

                }
                "invalidRequest" -> {
                    progressDialog.dismiss()
                   this.showSnackBar(mainLin, R.string.invalidPhoneOrEmail)
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
            nameTxt = name.text?.trim().toString()
            phoneTxt = phoneNumber.text?.trim().toString()
            emailTxt = email.text?.trim().toString()
            passwordTxt = password.text?.trim().toString()
            viewModel.checkData(
                nameTxt,
                phoneTxt,
                emailTxt,
                passwordTxt,
            )
        }

    }

}