package alphagroup.eyjar.com.model.signUp


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("token_type")
    val tokenType: String?,
    @SerializedName("expires_in")
    val expiresIn: Int?,
    @SerializedName("user")
    val user: User?
)