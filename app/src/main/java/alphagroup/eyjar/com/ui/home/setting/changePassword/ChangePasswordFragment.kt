package alphagroup.eyjar.com.ui.home.setting.changePassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.utlis.showSnackBar
import alphagroup.eyjar.com.viewModel.ChangePasswordViewModel
import android.app.ProgressDialog
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.change_password_fragment.*
import kotlinx.android.synthetic.main.main_toolbar.view.*

class ChangePasswordFragment : Fragment(), View.OnClickListener {
    private lateinit var progressDialog: ProgressDialog
    private lateinit var viewModel: ChangePasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.change_password_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        viewModel = ViewModelProvider(this)[ChangePasswordViewModel::class.java]

        toolbar.title.text =requireActivity().resources.getString(R.string.changePassword)
        toolbar.backBtn.visibility = View.VISIBLE

        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage(this.resources.getString(R.string.loading))
        progressDialog.setCancelable(false)
        val drawable = ProgressBar(requireActivity()).indeterminateDrawable.mutate()
        drawable.setColorFilter(
            ContextCompat.getColor(requireActivity(), R.color.black),
            PorterDuff.Mode.SRC_IN
        )
        progressDialog.setIndeterminateDrawable(drawable)

        toolbar.backBtn.setOnClickListener(this)
        saveBtn.setOnClickListener(this)

        listenOnMLD()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun listenOnMLD() {
        viewModel.resultMLD.observe(requireActivity(), { result ->
            disableErrors()
            when (result) {
                "invalid oldPassword" -> {
                    oldPasswordLin.isErrorEnabled = true
                    oldPasswordLin.error = resources.getString(R.string.invalidOldPhoneNumber)
                }
                "invalid newPassword" -> {
                    newPasswordLin.isErrorEnabled = true
                    newPasswordLin.error = resources.getString(R.string.invalidNewPhoneNumber)
                }
                "valid data" -> {

                    disableErrors()
                    viewModel.checkNetwork(requireActivity())
                }
                "noInternetConnection" -> {
                    this.showSnackBar(mainLin, R.string.noInternetConnection)

                }
                "isInternetPresent" -> {
                    viewModel.sendRequest()
                }
                "validRequest" -> {
                 requireActivity().onBackPressed()

                }

            }
        })

    }


    private fun disableErrors() {
        oldPasswordLin.isErrorEnabled = false
        newPasswordLin.isErrorEnabled = false


    }

    override fun onClick(view: View?) {

        if (saveBtn == view) {
            viewModel.checkData(
                oldPassword.text?.trim().toString(),
                newPassword.text?.trim().toString()
            )

        }
        if (toolbar.backBtn == view) {
            requireActivity().onBackPressed()
        }
    }

}