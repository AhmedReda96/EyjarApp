package alphagroup.eyjar.com.viewModel

import alphagroup.eyjar.com.model.home.Data
import alphagroup.eyjar.com.repository.HomeRepository
import alphagroup.eyjar.com.commons.TestLogin
import alphagroup.eyjar.com.commons.checkNetwork
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val TAG: String = HomeViewModel::class.java.simpleName
    var resultMLD = MutableLiveData<String>()
    private val _MSF = MutableStateFlow<List<Data?>?>(emptyList())
    val MSF: StateFlow<List<Data?>?> get() = _MSF
    private lateinit var testLogin: TestLogin
    private lateinit var owner: LifecycleOwner


    @RequiresApi(Build.VERSION_CODES.M)
    fun checkNetwork(context: FragmentActivity) {
        if (!context.checkNetwork()) {
            Log.d(
                TAG,
                "testTag checkNetwork: noInternetConnection"
            )
            resultMLD.value = "noInternetConnection"

        } else {
            testLogin = TestLogin(context)
            owner = context
            Log.d(
                TAG,
                "testTag checkNetwork:  isInternetPresent"
            )
            resultMLD.value = "isInternetPresent"

        }


    }


    fun sendRequest() {
        viewModelScope.launch {
            repository.getHomeResponse(testLogin.getToken().toString()).catch { e ->
                Log.d(
                    TAG,
                    "testTag getHomeResponse catch:  ${e.message}"
                )
            }.collect {
                if (!(it?.size == 0 || it == null)) {
                    Log.d(
                        TAG,
                        "testTag getHomeResponse dataSize==:  ${it.size}"
                    )
                    resultMLD.value = "validRequest"
                    _MSF.emit(it)

                } else {

                    resultMLD.value = "invalidRequest"
                    Log.d(
                        TAG,
                        "testTag getHomeResponse:  null"
                    )
                    _MSF.emit(null)

                }
            }

        }
    }


}