package alphagroup.eyjar.com.viewModel

import alphagroup.eyjar.com.utlis.checkNetwork
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {
    private val TAG: String = SignInViewModel::class.java.simpleName
    var resultMLD = MutableLiveData<String>()

    fun checkData(phoneNumber: String, password: String) {
        if (phoneNumber.isEmpty() || phoneNumber.length < 11) {
            resultMLD.value = "invalid phoneNumber"
            Log.d(
                TAG,
                "testTag checkData: invalid phoneNumber"
            )
        } else {

            if (password.length < 6) {
                resultMLD.value = "invalid password"
                Log.d(
                    TAG,
                    "testTag checkData: invalid password"
                )
            } else {
                resultMLD.value = "valid data"
                Log.d(
                    TAG,
                    "testTag checkData: valid data"
                )


            }
        }

    }
    fun checkNetwork(context: FragmentActivity) {
        if (!context.checkNetwork()) {
            resultMLD.value = "noInternetConnection"
            Log.d(
                TAG,
                "testTag checkNetwork: noInternetConnection"
            )

        } else {
            resultMLD.value = "isInternetPresent"
            Log.d(
                TAG,
                "testTag checkNetwork:  isInternetPresent"
            )
        }
    }
}