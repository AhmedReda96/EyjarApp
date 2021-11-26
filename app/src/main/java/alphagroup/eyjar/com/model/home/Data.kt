package alphagroup.eyjar.com.model.home


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("user_id")
    val userId: String?,
    @SerializedName("user_name")
    val userName: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("brand")
    val brand: String?,
    @SerializedName("model")
    val model: String?,
    @SerializedName("year")
    val year: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("licance_plate")
    val licancePlate: String?,
    @SerializedName("engine_power")
    val enginePower: String?,
    @SerializedName("fuel_type")
    val fuelType: String?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("gear_type")
    val gearType: String?
)