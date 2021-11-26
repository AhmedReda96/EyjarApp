package alphagroup.eyjar.com.ui.home.setting.editProfileInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.commons.ProfileInfoSP
import alphagroup.eyjar.com.commons.showSnackBar
import alphagroup.eyjar.com.viewModel.EditProfileInfoViewModel
import android.app.ProgressDialog
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ProgressBar
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.mainLin
import kotlinx.android.synthetic.main.edit_profile_info_fragment.*
import kotlinx.android.synthetic.main.edit_profile_info_fragment.saveBtn
import kotlinx.android.synthetic.main.edit_profile_info_fragment.toolbar
import kotlinx.android.synthetic.main.edit_profile_info_fragment.view.*
import kotlinx.android.synthetic.main.main_toolbar.view.*

@AndroidEntryPoint
class EditProfileInfoFragment : Fragment(), View.OnClickListener {
    private lateinit var progressDialog: ProgressDialog
    private lateinit var profileInfoSP: ProfileInfoSP
    private lateinit var nameTxt: String
    private lateinit var emailTxt: String
    private lateinit var genderTxt: String
    private val viewModel: EditProfileInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_profile_info_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        toolbar.title.text = requireActivity().resources.getString(R.string.accountSetting)
        toolbar.backBtn.visibility = View.VISIBLE
        profileInfoSP = ProfileInfoSP(requireContext())

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

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.maleBtn -> {
                    genderTxt = "male"
                }
                R.id.femaleBtn -> {

                    genderTxt = "female"
                }
            }
        })
        getData()
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

                "invalid email" -> {
                    emailLin.isErrorEnabled = true
                    emailLin.error = resources.getString(R.string.invalidEmail)

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
                    viewModel.sendRequest(nameTxt, emailTxt, genderTxt)
                }
                "validRequest" -> {
                    progressDialog.dismiss()

                    requireActivity().onBackPressed()
                }
                "invalidRequest" -> {
                    progressDialog.dismiss()
                    this.showSnackBar(mainLin, R.string.errorEditProfileData)
                }

            }

        })


    }

    private fun disableErrors() {
        emailLin.isErrorEnabled = false
        nameLin.isErrorEnabled = false
    }

    private fun getData() {
        nameTxt = profileInfoSP.getName().toString()
        emailTxt = profileInfoSP.getEmail().toString()
        genderTxt = profileInfoSP.getGender().toString()
        name.setText(nameTxt.toString())
        email.setText(emailTxt.toString())
        if (genderTxt == "male") {
            radioGroup.maleBtn.isChecked = true
        } else {
            radioGroup.femaleBtn.isChecked = true
        }


    }

    override fun onClick(view: View?) {
        if (toolbar.backBtn == view) {
            requireActivity().onBackPressed()
        }
        if (saveBtn == view) {
            nameTxt = name.text?.trim().toString()
            emailTxt = email.text?.trim().toString()
            viewModel.checkData(nameTxt, emailTxt)
        }
    }
}