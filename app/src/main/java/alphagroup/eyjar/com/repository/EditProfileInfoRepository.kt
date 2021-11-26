package alphagroup.eyjar.com.repository

import alphagroup.eyjar.com.api.Service
import alphagroup.eyjar.com.model.editProfileInfo.EditProfileInfoResponse
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class EditProfileInfoRepository @Inject constructor(private val service: Service) {
    private val TAG: String = EditProfileInfoRepository::class.java.simpleName

    fun updateProfileResponse(
        name: String,
        email: String,
        gender: String,
        token: String
    ): Flow<EditProfileInfoResponse?> = flow {

        val response = service.editProfileInfoResponse(name, email, gender, "Bearer $token")
        if (response?.isSuccessful!! && response.body()?.status == true) {

            Log.d(
                TAG,
                "testTag getHomeResponse: response isSuccessful & status is true }"
            )
            emit(response.body())

        } else {

            Log.d(
                TAG,
                "testTag getHomeResponse: status is false }"
            )
            emit(null)
        }


    }.flowOn(Dispatchers.IO)


}