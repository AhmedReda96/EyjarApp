package alphagroup.eyjar.com.ui.login.signIn

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.utlis.StoreLanguageData
import alphagroup.eyjar.com.utlis.showSnackBar
import alphagroup.eyjar.com.viewModel.SignInViewModel
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Paint
import android.graphics.PorterDuff
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.sign_in_fragment.*

class SignInFragment : Fragment(), View.OnClickListener {
    private lateinit var storeLanguageData: StoreLanguageData
    private lateinit var language: String
    private lateinit var progressDialog: ProgressDialog

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_in_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()

    }

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

    private fun listenOnMLD() {
        viewModel.resultMLD.observe(requireActivity(), { result ->
            when (result) {
                "invalid phoneNumber" -> {
                    phoneNumberLin.isErrorEnabled = true
                    phoneNumberLin.error = resources.getString(R.string.invalidPhoneNumber)

                }
                "invalid password" -> {
                    passwordLin.isErrorEnabled = true
                    passwordLin.error = resources.getString(R.string.invalidPassword)
                    Snackbar.make(
                                            mainLin,
                                            resources.getString(R.string.invalidPassword),
                                            Snackbar.LENGTH_LONG
                                        ).show()
                }
                "invalid phoneOrPass" -> {
                    this.showSnackBar(mainLin,R.string.invalidPhoneOrPassword)
                }

                "valid data" -> {
                    phoneNumberLin.isErrorEnabled = false
                    passwordLin.isErrorEnabled = false

                    viewModel.checkNetwork(requireActivity())
                }
                "noInternetConnection" -> {
                    this.showSnackBar(mainLin,R.string.noInternetConnection)

                }
                "isInternetPresent" -> {
                    //    viewModel.sendRequest()
                }
                "validRequest" -> {
                }

            }
        })


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
            viewModel.checkData(
                phoneNumber.text.toString().trim(),
                password.text.toString().trim()
            )
        }
    }
}