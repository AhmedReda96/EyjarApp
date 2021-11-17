package alphagroup.eyjar.com.ui.home.setting.editProfileInfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.utlis.ProfileInfo
import alphagroup.eyjar.com.utlis.showSnackBar
import alphagroup.eyjar.com.viewModel.EditProfileInfoViewModel
import android.app.ProgressDialog
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ProgressBar
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.edit_profile_info_fragment.*
import kotlinx.android.synthetic.main.edit_profile_info_fragment.view.*
import kotlinx.android.synthetic.main.main_toolbar.view.*

class EditProfileInfoFragment : Fragment(), View.OnClickListener {
    private lateinit var progressDialog: ProgressDialog
    private lateinit var profileInfo: ProfileInfo
    private var nameTxt: String? = null
    private var emailTxt: String? = null
    private var genderTxt: String? = null
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
        profileInfo = ProfileInfo(requireContext())

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
                    viewModel.sendRequest()
                }
                "validRequest" -> {

                    requireActivity().onBackPressed()
                }

            }

        })


    }

    private fun disableErrors() {
        emailLin.isErrorEnabled = false
        nameLin.isErrorEnabled = false
    }

    private fun getData() {
        nameTxt = profileInfo.getName()
        emailTxt = profileInfo.getEmail()
        genderTxt = profileInfo.getGender()
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
            viewModel.checkData(name.text?.trim().toString(), email.text?.trim().toString())
        }
    }
}