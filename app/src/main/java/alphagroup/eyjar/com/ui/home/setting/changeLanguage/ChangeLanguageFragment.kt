package alphagroup.eyjar.com.ui.home.setting.changeLanguage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.commons.StoreLanguageData
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_change_language.*
import kotlinx.android.synthetic.main.main_toolbar.view.*


class ChangeLanguageFragment : Fragment(), View.OnClickListener {
    private lateinit var languageData: StoreLanguageData
    private lateinit var language: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        languageData = StoreLanguageData(requireContext())
        language = languageData.getAppLanguage().toString()
        toolbar.title.text = resources.getString(R.string.changeLanguage).toString()
        toolbar.backBtn.visibility = View.VISIBLE
        toolbar.backBtn.setOnClickListener(this)
        arabicLin.setOnClickListener(this)
        englishLin.setOnClickListener(this)
        saveBtn.setOnClickListener(this)

        setChecked(language)

    }

    private fun setChecked(lang: String) {
        if (lang == "ar") {
            arabicRB.isChecked = true
            englishRB.isChecked = false
        } else {
            arabicRB.isChecked = false
            englishRB.isChecked = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_language, container, false)
    }

    override fun onClick(view: View?) {
        if (arabicLin == view) {
            language = "ar"
            setChecked(language)

        }
        if (englishLin == view) {
            language = "en"
            setChecked(language)
        }

        if (saveBtn == view) {
            languageData.setAppLanguage(language)
            findNavController().navigate(
                R.id.action_changeLanguageFragment_to_splashFragment
            )
        }
        if (toolbar.backBtn == view) {
            requireActivity().onBackPressed()

        }


    }


}