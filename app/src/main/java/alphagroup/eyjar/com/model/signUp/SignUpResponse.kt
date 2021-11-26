package alphagroup.eyjar.com.model.signUp


import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("errors")
    val errors: String?,
    @SerializedName("data")
    val data: Data?
)