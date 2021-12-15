package net.larntech.retrofit_users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import net.larntech.retrofit_users.R
import net.larntech.retrofit_users.databinding.ActivityMainBinding
import net.larntech.retrofit_users.ui.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)
        initData();
    }

    private fun initData(){
        Handler().postDelayed(Runnable {
            startActivity(Intent(this, LoginActivity::class.java))
        },2000)

    }
}