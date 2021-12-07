package net.larntech.retrofit12.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.larntech.retrofit12.R
import net.larntech.retrofit12.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val root = binding.root;
        setContentView(root)
        initData()
    }

    private fun initData(){
        clickListener();
    }

    private fun clickListener(){
        binding.llHaveAccount.setOnClickListener {
            loginUser();
        }

        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun loginUser(){

    }

    private fun registerUser(){

    }

}