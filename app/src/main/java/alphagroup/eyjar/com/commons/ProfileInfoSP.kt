package alphagroup.eyjar.com.commons

import android.content.Context
import android.content.SharedPreferences

class ProfileInfoSP(context: Context) {
    private var sharedPreferences: SharedPreferences? = null
    private var name: String? = null
    private var email: String? =null
    private var phone: String? = null
    private var gender: String? =null
    private var id: String? = null

    init {
        sharedPreferences = context.getSharedPreferences("StoreData", Context.MODE_PRIVATE)
    }

    fun remove() {
        setName(null)
        setEmail(null)
        setPhone(null)
        setGender(null)
        setId(null)
    }

    fun getName(): String? {
        name = sharedPreferences!!.getString("name", name)
        return name
    }

    fun setName(name: String?) {
        this.name = name
        sharedPreferences!!.edit().putString("name", name).apply()
    }


    fun getEmail(): String? {
        email = sharedPreferences!!.getString("email", email)
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
        sharedPreferences!!.edit().putString("email", email).apply()
    }

    fun getPhone(): String? {
        phone = sharedPreferences!!.getString("phone", phone)
        return phone
    }

    fun setPhone(phone: String?) {
        this.phone = phone
        sharedPreferences!!.edit().putString("phone", phone).apply()
    }


    fun getGender(): String? {
        gender = sharedPreferences!!.getString("gender", gender)
        return gender
    }

    fun setGender(gender: String?) {
        this.gender = gender
        sharedPreferences!!.edit().putString("gender", gender).apply()
    }

    fun getId(): String? {
        id = sharedPreferences!!.getString("id", id)
        return id
    }

    fun setId(id: String?) {
        this.id = id
        sharedPreferences!!.edit().putString("id", id).apply()
    }


}