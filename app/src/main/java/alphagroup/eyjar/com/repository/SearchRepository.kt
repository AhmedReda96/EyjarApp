package alphagroup.eyjar.com.repository

import alphagroup.eyjar.com.api.Service
import alphagroup.eyjar.com.model.search.Data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SearchRepository @Inject constructor(private val service: Service) {
    private val TAG: String = SearchRepository::class.java.simpleName

    fun getSearchResponse(query:String,token: String): Flow<List<Data>?> = flow {

                val response = service.searchResponse(query, "Bearer $token","application/json")
                if (response?.isSuccessful!! && response.body()?.status == true) {
                    Log.d(
                        TAG,
                        "testTag getSearchResponse: response isSuccessful & status is true }"
                    )
                   emit(response.body()!!.data)


                } else {

                    Log.d(
                        TAG,
                        "testTag getHomeResponse: status is false }"
                    )
                    emit(null)
                }
            }


    }
