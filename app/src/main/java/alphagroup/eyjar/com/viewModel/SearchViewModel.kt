package alphagroup.eyjar.com.viewModel

import alphagroup.eyjar.com.commons.TestLogin
import alphagroup.eyjar.com.commons.checkNetwork
import alphagroup.eyjar.com.model.search.Data
import alphagroup.eyjar.com.repository.SearchRepository
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

class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {
    private val TAG: String = SearchViewModel::class.java.simpleName
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

            Log.d(
                TAG,
                "testTag checkNetwork:  isInternetPresent"
            )
            testLogin = TestLogin(context)
            owner = context
            resultMLD.value = "isInternetPresent"
        }


    }

    fun getSearchResponse(query: String) {
        viewModelScope.launch {

            repository.getSearchResponse(query, testLogin.getToken().toString()).catch { e ->
                Log.d(
                    TAG,
                    "testTag getSearchResponse catch:  ${e.message}"
                )
            }.collect {
                if (!(it?.size == 0 || it == null)) {
                    resultMLD.value = "validRequest"
                    Log.d(
                        TAG,
                        "testTag getSearchResponse dataSize==:  ${it.size}"
                    )
                    _MSF.emit(it)

                } else {
                    resultMLD.value = "invalidRequest"
                    Log.d(
                        TAG,
                        "testTag getSearchResponse:  null"
                    )
                }

            }
        }

    }


}