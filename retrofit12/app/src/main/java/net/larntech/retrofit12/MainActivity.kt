package net.larntech.retrofit12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import net.larntech.retrofit12.R
import net.larntech.retrofit12.ui.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData();
    }

    private fun initData(){
        Handler().postDelayed(Runnable {
        startActivity(Intent(this, LoginActivity::class.java))
        },2000)
    }
}