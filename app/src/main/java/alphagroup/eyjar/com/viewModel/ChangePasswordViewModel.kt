package alphagroup.eyjar.com.viewModel

import alphagroup.eyjar.com.utlis.checkNetwork
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChangePasswordViewModel : ViewModel() {
    private val TAG: String = ChangePasswordViewModel::class.java.simpleName
    var resultMLD = MutableLiveData<String>()


    fun checkData(oldPassword: String, newPassword: String) {
        if (oldPassword.length < 6) {
            resultMLD.value = "invalid oldPassword"
            Log.d(
                TAG,
                "testTag checkData: invalid oldPassword"
            )
        } else {

            if (newPassword.length < 6) {
                resultMLD.value = "invalid newPassword"
                Log.d(
                    TAG,
                    "testTag checkData: invalid newPassword"
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