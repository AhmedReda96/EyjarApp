package alphagroup.eyjar.com.ui.login.signIn

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.commons.StoreLanguageData
import alphagroup.eyjar.com.commons.showSnackBar
import alphagroup.eyjar.com.viewModel.SignInViewModel
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.sign_in_fragment.*

@AndroidEntryPoint
class SignInFragment : Fragment(), View.OnClickListener {
    private lateinit var storeLanguageData: StoreLanguageData
    private lateinit var language: String
    private lateinit var progressDialog: ProgressDialog
    private lateinit var phoneTxt: String
    private lateinit var passwordTxt: String

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_in_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        storeLanguageData = StoreLanguageData(requireActivity())
        language = storeLanguageData.getAppLanguage().toString()
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
        signInBtn.setOnClickListener(this)
        languageLin.setOnClickListener(this)

        checkLanguage()
       listenOnMLD()
        signUpBtn.paintFlags = signUpBtn.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    @SuppressLint("SetTextI18n")
    private fun checkLanguage() {
        if (language == "ar") {
            languageTv.text = "English"
        } else {
            languageTv.text = "العربية"

        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun listenOnMLD() {
        viewModel.resultMLD.observe(requireActivity(), { result ->
            disableErrors()
            when (result) {
                "invalid phoneNumber" -> {
                    phoneNumberLin.isErrorEnabled = true
                    phoneNumberLin.error = resources.getString(R.string.invalidPhoneNumber)

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

                   viewModel.sendRequest(
                       phoneTxt, passwordTxt
                   )
                }
                "validRequest" -> {
                    progressDialog.dismiss()
                    findNavController().navigate(
                        R.id.action_signInFragment_to_homeFragment
                    )

                }
                "invalidRequest" -> {
                    progressDialog.dismiss()
                    this.showSnackBar(mainLin, R.string.invalidPhoneOrPassword)
                }

            }
        })


    }

    private fun disableErrors() {
        phoneNumberLin.isErrorEnabled = false
        passwordLin.isErrorEnabled = false
    }


    override fun onClick(view: View?) {
        if (view == languageLin) {
            if (language == "ar") {
                storeLanguageData.setAppLanguage("en")
                languageTv.text = "العربية"
                findNavController().navigate(
                    R.id.action_signInFragment_to_splashFragment
                )
            } else {
                storeLanguageData.setAppLanguage("ar")
                languageTv.text = "English"
                findNavController().navigate(
                    R.id.action_signInFragment_to_splashFragment
                )
            }
        }
        if (view == signUpBtn) {
            findNavController().navigate(
                R.id.action_signInFragment_to_signUpFragment
            )
        }
        if (view == signInBtn) {
            phoneTxt = phoneNumber.text.toString().trim()
            passwordTxt = password.text.toString().trim()
            viewModel.checkData(
                phoneTxt, passwordTxt


            )
        }
    }
}