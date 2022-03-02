package net.larntech.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OtpActivity extends AppCompatActivity {

    private Button btnValidateOtp;
    private EditText etOpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        initData();
        clickListener();
    }


    private void initData(){
        btnValidateOtp = findViewById(R.id.btnValidOtp);
        etOpt = findViewById(R.id.etOpt);
    }

    private void clickListener(){
        btnValidateOtp.setOnClickListener(view -> {
            checkUsername();
        });


    }

    private void checkUsername(){
        if(etOpt.getText().length() > 0){
            Toast.makeText(this,"Validating OTP ...",Toast.LENGTH_SHORT).show();
            handleDelay();
        }else{
            Toast.makeText(this," Please enter valid OTP ", Toast.LENGTH_LONG).show();;
        }
    }

    private void handleDelay(){
        new Handler().postDelayed(() -> startActivity(new Intent(OtpActivity.this,PinResetActivity.class)),1500);
    }
}