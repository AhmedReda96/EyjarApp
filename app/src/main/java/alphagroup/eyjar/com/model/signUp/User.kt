package alphagroup.eyjar.com.model.signUp


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("sex")
    val sex: String?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any?,
    @SerializedName("current_team_id")
    val currentTeamId: Any?,
    @SerializedName("profile_photo_path")
    val profilePhotoPath: Any?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("profile_photo_url")
    val profilePhotoUrl: String?
)