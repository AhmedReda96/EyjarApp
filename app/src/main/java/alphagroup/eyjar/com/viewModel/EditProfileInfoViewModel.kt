package alphagroup.eyjar.com.viewModel

import alphagroup.eyjar.com.commons.ProfileInfoSP
import alphagroup.eyjar.com.commons.TestLogin
import alphagroup.eyjar.com.commons.checkNetwork
import alphagroup.eyjar.com.repository.EditProfileInfoRepository
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
class EditProfileInfoViewModel @Inject constructor(
    private val repository: EditProfileInfoRepository
) : ViewModel() {
    private val TAG: String = EditProfileInfoViewModel::class.java.simpleName
    var resultMLD = MutableLiveData<String>()
    private lateinit var testLogin: TestLogin
    private lateinit var profileInfoSP: ProfileInfoSP

    fun checkData(name: String, email: String) {
        if (name.isEmpty() || name.length < 2) {
            resultMLD.value = "invalid name"
            Log.d(
                TAG,
                "testTag checkData: invalid name"
            )

        } else {

            if (email.isEmpty() || !email.contains("@")) {
                resultMLD.value = "invalid email"
                Log.d(
                    TAG,
                    "testTag checkData: invalid email"
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
        testLogin = TestLogin(context)
        profileInfoSP = ProfileInfoSP(context)

    }

    fun sendRequest(
        name: String,
        email: String,
        gender: String
    ) {
        viewModelScope.launch {
            repository.updateProfileResponse(name, email, gender, testLogin.getToken().toString())
                .catch { e ->
                    Log.d(
                        TAG,
                        "testTag sendRequest catch:  ${e.message}"
                    )
                }.collect {
                    if (it != null) {
                        profileInfoSP.setEmail(email)
                        profileInfoSP.setName(name)
                        profileInfoSP.setGender(gender)

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