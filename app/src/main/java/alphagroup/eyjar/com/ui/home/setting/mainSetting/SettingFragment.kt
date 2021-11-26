package alphagroup.eyjar.com.ui.home.setting.mainSetting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.commons.ProfileInfoSP
import alphagroup.eyjar.com.commons.TestLogin
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.main_toolbar.view.*
import kotlinx.android.synthetic.main.setting_fragment.*

class SettingFragment : Fragment(), View.OnClickListener {
    private lateinit var testLogin: TestLogin
    private lateinit var profileInfoSP: ProfileInfoSP

    companion object {
        fun newInstance() = SettingFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.setting_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        testLogin = TestLogin(requireContext())
        profileInfoSP = ProfileInfoSP(requireContext())

        toolbar.title.text = resources.getString(R.string.setting)

        editProfileInfoLin.setOnClickListener(this)
        changePasswordLin.setOnClickListener(this)
        changeLanguageLin.setOnClickListener(this)
        logoutLin.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        if (editProfileInfoLin == view) {
            findNavController().navigate(
                R.id.action_settingFragment_to_editProfileInfoFragment
            )
        }
        if (changePasswordLin == view) {
            findNavController().navigate(
                R.id.action_settingFragment_to_changePasswordFragment
            )
        }
        if (changeLanguageLin == view) {
            findNavController().navigate(
                R.id.action_settingFragment_to_changeLanguageFragment
            )
        }
        if (logoutLin == view) {
            testLogin.remove()
            profileInfoSP.remove()
            findNavController().navigate(
                R.id.action_settingFragment_to_signInFragment
            )

        }
    }

}