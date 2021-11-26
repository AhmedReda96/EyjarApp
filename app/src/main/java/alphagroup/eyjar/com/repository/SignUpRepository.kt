package alphagroup.eyjar.com.repository

import alphagroup.eyjar.com.api.Service
import alphagroup.eyjar.com.model.signUp.Data
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


class SignUpRepository @Inject constructor(private val service: Service) {
    private val TAG: String = SignUpRepository::class.java.simpleName

    fun getSignUpResponse(
        name: String,
        phone: String,
        email: String,
        password: String,
        gender: String
    ): Flow<Data?> =
        flow {
            val response =
                service.signUpResponse(name, phone, email, password, gender, "application/json")

            if (response?.isSuccessful!! && response.body()?.status == true) {
                Log.d(
                    TAG,
                    "testTag getSignUpResponse: response isSuccessful & status is true }"
                )
                emit(response.body()!!.data)
            } else {

                Log.d(
                    TAG,
                    "testTag getSignUpResponse: status is false }"
                )
                emit(null)


            }
        }.flowOn(Dispatchers.IO)


}