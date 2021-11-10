package alphagroup.eyjar.com.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.ui.main.MainActivity
import alphagroup.eyjar.com.utlis.changeLanguage
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*

class SplashFragment : Fragment() {
    private val parentJob: Job = Job()
    private val coroutineScope = CoroutineScope(
        Dispatchers.Main + parentJob
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coroutineScope.launch {
           (activity as MainActivity?)?.changeAppLanguage()

            delay(2000)
            findNavController().navigate(
                R.id.action_splashFragment_to_signInFragment
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)

    }
}