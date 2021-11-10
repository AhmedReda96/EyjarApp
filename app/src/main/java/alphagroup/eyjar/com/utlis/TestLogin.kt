package alphagroup.eyjar.com.utlis

import android.content.Context
import android.content.SharedPreferences

class TestLogin(private val context: Context) {
    private var auth: String? = null
    private var token: String? = null
        get() {
            field = sharedPreferences.getString("Token", field)
            return field
        }
        set(token) {
            field = token
            sharedPreferences.edit().putString("Token", token).commit()
        }
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("TestLogin", Context.MODE_PRIVATE)

    fun remove() {
        loginType = null
        token = null
    }

    private var loginType: String?
        get() {
            auth = sharedPreferences.getString("Auth", auth)
            return auth
        }
        set(auth) {
            this.auth = auth
            sharedPreferences.edit().putString("Auth", auth).commit()
        }

}