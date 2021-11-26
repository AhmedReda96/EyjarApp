package alphagroup.eyjar.com.model.editProfileInfo


import com.google.gson.annotations.SerializedName

data class EditProfileInfoResponse(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("errors")
    val errors: String?,
    @SerializedName("data")
    val data: String?
)