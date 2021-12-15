package net.larntech.retrofit_users.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.larntech.retrofit_users.R
import net.larntech.retrofit_users.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater);
        val root = binding.root;
        setContentView(root)
        initData();
    }


    private fun initData(){
        val intent = intent.extras;

        if(intent != null){
            val username = intent.getString("username")
            setUserName(username!!)
        }


    }


    private fun setUserName(username: String){
        binding.tvUserDetail.text = username

    }


}