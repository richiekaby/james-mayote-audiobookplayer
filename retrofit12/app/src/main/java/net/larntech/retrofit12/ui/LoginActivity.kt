package net.larntech.retrofit12.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.larntech.retrofit12.R
import net.larntech.retrofit12.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val root = binding.root;
        setContentView(root)
        initData();
    }

    private fun initData(){
        clickListener();
    }

    private fun clickListener(){
        binding.btnLogin.setOnClickListener {
            loginUser();
        }

        binding.llNoAccount.setOnClickListener {
            registerUser();
        }

    }

    private fun registerUser(){
        startActivity(Intent(this, RegisterActivity::class.java))
        finishAffinity()

    }

    private fun loginUser(){

    }



}