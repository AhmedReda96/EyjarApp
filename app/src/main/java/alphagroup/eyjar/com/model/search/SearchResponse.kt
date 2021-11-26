package alphagroup.eyjar.com.model.search


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("errors")
    val errors: String?,
    @SerializedName("data")
    val data: List<Data>?
)