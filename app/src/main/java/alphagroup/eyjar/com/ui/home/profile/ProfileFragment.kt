package alphagroup.eyjar.com.ui.home.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.commons.ProfileInfoSP
import kotlinx.android.synthetic.main.main_toolbar.view.*
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {
    private lateinit var profileInfoSP: ProfileInfoSP

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
        profileInfoSP = ProfileInfoSP(requireContext())
        getData()
        toolbar.title.text=resources.getString(R.string.profile)


    }

    private fun getData() {
        name.text = profileInfoSP.getName()
        email.text = profileInfoSP.getEmail()
        phoneNumber.text = profileInfoSP.getPhone()
        val genderTxt=profileInfoSP.getGender()
        if (genderTxt=="male"){
            gender.text =resources.getString(R.string.male)

        }else{
            gender.text =resources.getString(R.string.female)

        }


    }

}