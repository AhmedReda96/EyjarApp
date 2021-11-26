package alphagroup.eyjar.com.ui.main

import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.commons.changeLanguage
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeAppLanguage()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainNavHost) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottomNavigation).setupWithNavController(
            navController
        )


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.d(
                "TAG",
                "testTag getFragment: ${destination.label}"
            )
            when (destination.id) {
                R.id.homeFragment, R.id.searchFragment, R.id.profileFragment, R.id.settingFragment -> {
                    bottomNavigation.visibility = View.VISIBLE
                }
                else -> {
                    bottomNavigation.visibility = View.GONE
                }
            }

        }
    }

    fun changeAppLanguage() {
        this.changeLanguage(mainLin)
    }

}