package alphagroup.eyjar.com.repository

import alphagroup.eyjar.com.api.Service
import alphagroup.eyjar.com.model.changePassword.ChangePasswordResponse
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class ChangePasswordRepository @Inject constructor(private val service: Service) {
    private val TAG: String = ChangePasswordRepository::class.java.simpleName

    fun changePasswordResponse(oldPass: String,newPass: String,token: String): Flow<ChangePasswordResponse?> = flow {
        val response = service.changePasswordResponse(oldPass, newPass, newPass, "Bearer $token")
        if (response?.isSuccessful!! && response.body()?.status == true) {
            Log.d(
                TAG,
                "testTag getHomeResponse: response isSuccessful & status is true }"
            )
            emit(response.body())
        }else{
            Log.d(
                TAG,
                "testTag getHomeResponse: status is false }"
            )
            emit(null)
        }
    }.flowOn(Dispatchers.IO)

}