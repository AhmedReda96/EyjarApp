package alphagroup.eyjar.com.model.home


import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("errors")
    val errors: String?,
    @SerializedName("data")
    val data: List<Data>?
)