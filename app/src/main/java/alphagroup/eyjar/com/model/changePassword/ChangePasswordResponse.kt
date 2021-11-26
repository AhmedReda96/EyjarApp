package alphagroup.eyjar.com.model.changePassword


import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("errors")
    val errors: String?,
    @SerializedName("data")
    val data: String?
)