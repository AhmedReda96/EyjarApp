package alphagroup.eyjar.com.model.signIn


import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("errors")
    val errors: String?,
    @SerializedName("data")
    val data: Data?
)