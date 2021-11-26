package alphagroup.eyjar.com.viewModel

import alphagroup.eyjar.com.commons.TestLogin
import alphagroup.eyjar.com.commons.checkNetwork
import alphagroup.eyjar.com.repository.ChangePasswordRepository
import alphagroup.eyjar.com.repository.SignInRepository
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: ChangePasswordRepository
) : ViewModel() {
    private val TAG: String = ChangePasswordViewModel::class.java.simpleName
    var resultMLD = MutableLiveData<String>()
    private lateinit var testLogin: TestLogin


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
        testLogin = TestLogin(context)

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

    fun sendRequest(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            repository.changePasswordResponse(
                oldPassword,
                newPassword,
                testLogin.getToken().toString()
            ).catch { e ->
                Log.d(
                    TAG,
                    "testTag sendRequest catch:  ${e.message}"
                )
            }.collect {
                if (it != null) {
                    resultMLD.value = "validRequest"
                    Log.d(
                        TAG,
                        "testTag sendRequest:  validRequest"
                    )

                } else {
                    resultMLD.value = "invalidRequest"
                    Log.d(
                        TAG,
                        "testTag sendRequest:  null"
                    )
                }

            }
        }
    }

}