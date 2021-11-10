package alphagroup.eyjar.com.utlis

import android.content.Context
import android.content.SharedPreferences

class StoreLanguageData(context: Context) {
    private var sharedPreferences: SharedPreferences? = null
    private var appLanguage: String? = "ar"

    init {
        sharedPreferences = context.getSharedPreferences("StoreData", Context.MODE_PRIVATE)
    }

    fun getAppLanguage(): String? {
        appLanguage = sharedPreferences!!.getString("appLanguage", appLanguage)
        return appLanguage
    }

    fun setAppLanguage(appLanguage: String?) {
        this.appLanguage = appLanguage
        sharedPreferences!!.edit().putString("appLanguage", appLanguage).apply()
    }

}