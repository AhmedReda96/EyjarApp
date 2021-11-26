package alphagroup.eyjar.com.viewModel

import alphagroup.eyjar.com.repository.SignUpRepository
import alphagroup.eyjar.com.commons.ProfileInfoSP
import alphagroup.eyjar.com.commons.TestLogin
import alphagroup.eyjar.com.commons.checkNetwork
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class SignUpViewModel @Inject constructor(
    private val repository: SignUpRepository
) : ViewModel() {
    private val TAG: String = SignUpViewModel::class.java.simpleName
    var resultMLD = MutableLiveData<String>()
    private lateinit var testLogin: TestLogin
    private lateinit var profileInfoSP: ProfileInfoSP
    private lateinit var owner: LifecycleOwner

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
        testLogin = TestLogin(context)
        profileInfoSP = ProfileInfoSP(context)
        owner = context
    }

    fun sendRequest(
        nameTxt: String,
        phoneTxt: String,
        emailTxt: String,
        passwordTxt: String,
        gender: String
    ) {
        viewModelScope.launch {
            repository.getSignUpResponse(nameTxt, phoneTxt, emailTxt, passwordTxt, gender)
                .catch { e ->
                    Log.d(
                        TAG,
                        "testTag getSignUpResponse catch:  ${e.message}"
                    )
                }.collect {

                    if (it?.accessToken != null) {
                        testLogin.setAuth(true)
                        testLogin.setToken(it.accessToken)
                        profileInfoSP.setEmail(it.user?.email)
                        profileInfoSP.setName(it.user?.name)
                        profileInfoSP.setId(it.user?.id.toString())
                        profileInfoSP.setPhone(it.user?.phone)
                        profileInfoSP.setGender(it.user?.sex)
                        resultMLD.value = "validRequest"
                        Log.d(
                            TAG,
                            "testTag sendRequest token=:  ${it.accessToken}"
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
