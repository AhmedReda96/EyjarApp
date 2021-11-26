package alphagroup.eyjar.com.repository

import alphagroup.eyjar.com.api.Service
import alphagroup.eyjar.com.model.signIn.SignInResponse
import alphagroup.eyjar.com.model.signIn.Data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


class SignInRepository @Inject constructor(private val service: Service) {
    private val TAG: String = SignInRepository::class.java.simpleName

    fun getSignInResponse(phone: String, password: String): Flow<Data?> = flow {
        val response = service.signInResponse(phone, password, "application/json")
        if (response?.isSuccessful!! && response.body()?.status == true) {
            Log.d(
                TAG,
                "testTag getSignInResponse: response isSuccessful & status is true }"
            )
           emit(response.body()!!.data)
        } else {
            Log.d(
                TAG,
                "testTag getSignInResponse: status is false }"
            )
            emit(null)
        }
    }.flowOn(Dispatchers.IO)

}


