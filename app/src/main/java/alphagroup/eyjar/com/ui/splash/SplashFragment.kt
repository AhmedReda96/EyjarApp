package alphagroup.eyjar.com.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.ui.main.MainActivity
import alphagroup.eyjar.com.commons.TestLogin
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*

class SplashFragment : Fragment() {
    private lateinit var testLogin: TestLogin
    private val parentJob: Job = Job()
    private val coroutineScope = CoroutineScope(
        Dispatchers.Main + parentJob
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_fragment, container, false)

    }

    override fun onStart() {
        super.onStart()

        testLogin = TestLogin(requireContext())
        coroutineScope.launch {
            (activity as MainActivity?)?.changeAppLanguage()

            delay(2000)
            if (testLogin.getAuth()) {
                findNavController().navigate(
                    R.id.action_splashFragment_to_homeFragment
                )

            } else {
                findNavController().navigate(
                    R.id.action_splashFragment_to_signInFragment
                )
            }
        }
    }
}