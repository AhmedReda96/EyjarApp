package alphagroup.eyjar.com.utlis

import android.content.Context
import android.content.SharedPreferences

class TestLogin(private val context: Context) {
    private var auth: Boolean = false
    private var token: String? = null
    private var sharedPreferences: SharedPreferences? = null


    init {
        sharedPreferences = context.getSharedPreferences("TestLogin", Context.MODE_PRIVATE)
    }


    fun remove() {
        setAuth(false)
        setToken(null)
    }

    fun getAuth(): Boolean {
        auth = sharedPreferences!!.getBoolean("auth", auth)
        return auth
    }

    fun setAuth(auth: Boolean) {
        this.auth = auth
        sharedPreferences!!.edit().putBoolean("auth", auth).apply()
    }

    fun getToken(): String? {
        token = sharedPreferences!!.getString("token", token)
        return token
    }

    fun setToken(id: String?) {
        this.token = id
        sharedPreferences!!.edit().putString("token", token).apply()
    }
}