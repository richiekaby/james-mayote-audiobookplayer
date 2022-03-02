package net.larntech.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UsernameActivity extends AppCompatActivity {

    private Button btnResetPin;
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        initData();
        clickListener();
    }

    private void initData(){
        btnResetPin = findViewById(R.id.btnResetPin);
        etEmail = findViewById(R.id.et_email);
    }

    private void clickListener(){
        btnResetPin.setOnClickListener(view -> {
            checkUsername();
        });


    }

    private void checkUsername(){
        if(etEmail.getText().length() > 0){
            Toast.makeText(this,"Sending otp ...",Toast.LENGTH_SHORT).show();
            handleDelay();
        }else{
            Toast.makeText(this," Please enter valid username ", Toast.LENGTH_LONG).show();;
        }
    }

    private void handleDelay(){
        new Handler().postDelayed(() -> startActivity(new Intent(UsernameActivity.this,OtpActivity.class)),1500);
    }
}