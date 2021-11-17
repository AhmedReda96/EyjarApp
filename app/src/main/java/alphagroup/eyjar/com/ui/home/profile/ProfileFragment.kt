package alphagroup.eyjar.com.ui.home.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.utlis.ProfileInfo
import kotlinx.android.synthetic.main.main_toolbar.view.*
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {
    private lateinit var profileInfo: ProfileInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        profileInfo = ProfileInfo(requireContext())
        getData()
        toolbar.title.text=resources.getString(R.string.profile)


    }

    private fun getData() {
        name.text = profileInfo.getName()
        email.text = profileInfo.getEmail()
        phoneNumber.text = profileInfo.getPhone()
        val genderTxt=profileInfo.getGender()
        if (genderTxt=="male"){
            gender.text =resources.getString(R.string.male)

        }else{
            gender.text =resources.getString(R.string.female)

        }


    }

}