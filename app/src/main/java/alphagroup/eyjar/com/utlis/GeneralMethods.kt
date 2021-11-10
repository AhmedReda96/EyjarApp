package alphagroup.eyjar.com.utlis

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import java.util.*

import androidx.core.content.ContextCompat

import com.google.android.material.snackbar.Snackbar


fun Activity.changeLanguage(relativeLayout: RelativeLayout) {
    val storeLanguageData = StoreLanguageData(this)

    val myLocale = Locale(storeLanguageData.getAppLanguage())
    val res: Resources = this.resources
    val dm = res.displayMetrics
    val conf = res.configuration
    conf.locale = myLocale
    res.updateConfiguration(conf, dm)

    if (storeLanguageData.getAppLanguage().equals("en")) {
        relativeLayout.layoutDirection = View.LAYOUT_DIRECTION_LTR
    }
    if (storeLanguageData.getAppLanguage().equals("ar")) {
        relativeLayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
    }

}

fun Activity.checkNetwork(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        Log.d(
            "TAG",
            "testTag checkNetwork: true"
        )
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    } else {
        Log.d(
            "TAG",
            "testTag checkNetwork: false"
        )
    }
    return false
}

fun Fragment.showSnackBar(view:RelativeLayout, message: Int){

    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    val sbView = snackbar.view
    sbView.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark));
    snackbar.show()


}
