package alphagroup.eyjar.com.ui.main

import alphagroup.eyjar.com.R
import alphagroup.eyjar.com.utlis.changeLanguage
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeAppLanguage()

    }

    fun changeAppLanguage() {
        this.changeLanguage(mainLin)
    }

}