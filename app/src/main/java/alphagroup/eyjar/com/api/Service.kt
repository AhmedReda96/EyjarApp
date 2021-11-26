package alphagroup.eyjar.com.api

import alphagroup.eyjar.com.model.changePassword.ChangePasswordResponse
import alphagroup.eyjar.com.model.home.HomeResponse
import alphagroup.eyjar.com.model.search.SearchResponse
import alphagroup.eyjar.com.model.signIn.SignInResponse
import alphagroup.eyjar.com.model.signUp.SignUpResponse
import alphagroup.eyjar.com.model.editProfileInfo.EditProfileInfoResponse
import retrofit2.Response
import retrofit2.http.*

interface Service {

    @POST("login")
    @FormUrlEncoded
    suspend fun signInResponse(
        @Field("phone") phone: String?,
        @Field("password") password: String?,
        @Header("Accept") Accept: String?,
    ): Response<SignInResponse>?

    @POST("register")
    @FormUrlEncoded
    suspend fun signUpResponse(
        @Field("name") name: String?,
        @Field("phone") phone: String?,
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("sex") sex: String?,
        @Header("Accept") Accept: String?,
    ): Response<SignUpResponse>?


    @GET("home")
    suspend fun homeResponse(
        @Header("Accept") Accept: String?,
        @Header("Authorization") Authorization: String?,
    ): Response<HomeResponse>?

    @POST("shearchCar")
    @FormUrlEncoded
    suspend fun searchResponse(
        @Field("serachKey") searchKey: String?,
        @Header("Authorization") Authorization: String?,
        @Header("Accept") Accept: String?,
    ): Response<SearchResponse>?

    @POST("changePassword")
    @FormUrlEncoded
    suspend fun changePasswordResponse(
        @Field("old_password") old_password: String?,
        @Field("password") password: String?,
        @Field("password_confirmation") password_confirmation: String?,
        @Header("Authorization") Authorization: String?
    ): Response<ChangePasswordResponse>?


    @POST("updateprofile")
    @FormUrlEncoded
    suspend fun editProfileInfoResponse(
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("sex") sex: String?,
        @Header("Authorization") Authorization: String?
    ): Response<EditProfileInfoResponse>?
}