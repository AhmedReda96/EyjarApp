package alphagroup.eyjar.com.repository

import alphagroup.eyjar.com.api.Service
import alphagroup.eyjar.com.model.home.Data

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


class HomeRepository @Inject constructor(private val service: Service) {
    private val TAG: String = HomeRepository::class.java.simpleName

    fun getHomeResponse(token: String): Flow<List<Data>?> = flow {
        val response = service.homeResponse("application/json", "Bearer $token")
        if (response?.isSuccessful!! && response.body()?.status == true) {
                Log.d(
                    TAG,
                    "testTag getHomeResponse: response isSuccessful & status is true }"
                )
            emit(response.body()?.data)

        } else {

                Log.d(
                    TAG,
                    "testTag getHomeResponse: status is false }"
                )

            emit(null)

            }
    }.flowOn(Dispatchers.IO)
}