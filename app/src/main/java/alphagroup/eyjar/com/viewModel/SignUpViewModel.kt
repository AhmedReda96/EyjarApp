package alphagroup.eyjar.com.viewModel

import alphagroup.eyjar.com.utlis.checkNetwork
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    private val TAG: String = SignUpViewModel::class.java.simpleName
    var resultMLD = MutableLiveData<String>()

    fun checkData(name: String, phoneNumber: String, email: String, password: String) {
        if (name.isEmpty() || name.length < 2) {
            resultMLD.value = "invalid name"
            Log.d(
                TAG,
                "testTag checkData: invalid name"
            )

        } else {
            if (phoneNumber.isEmpty() || phoneNumber.length < 10) {
                resultMLD.value = "invalid phoneNumber"
                Log.d(
                    TAG,
                    "testTag checkData: invalid phoneNumber"
                )
            } else {
                if (email.isEmpty() || !email.contains("@")) {
                    resultMLD.value = "invalid email"
                    Log.d(
                        TAG,
                        "testTag checkData: invalid email"
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

        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
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

    fun sendRequest() {

        resultMLD.value = "validRequest"
        Log.d(
            TAG,
            "testTag sendRequest:  validRequest"
        )
    }
}