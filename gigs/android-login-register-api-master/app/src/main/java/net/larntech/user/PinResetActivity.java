package net.larntech.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PinResetActivity extends AppCompatActivity {

    private Button btnResetPassword;
    private EditText etPassword;
    private EditText etCpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_reset);
        initData();
        clickListener();
    }

    private void initData(){
        btnResetPassword = findViewById(R.id.btnResetPassword);
        etPassword = findViewById(R.id.et_password);
        etCpassword = findViewById(R.id.et_cpassword);
    }

    private void clickListener(){
        btnResetPassword.setOnClickListener(view -> {
            checkPasswordMatch();
        });


    }

    private void checkPasswordMatch(){
        if(etPassword.getText().length() > 0 &&  etCpassword.getText().length() > 0){
            if(etPassword.getText().toString().equals(etCpassword.getText().toString())) {
                Toast.makeText(this, "Resetting Password ...", Toast.LENGTH_SHORT).show();
                handleDelay();
            }else{
                Toast.makeText(this," Password mismatch ", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this," Please provide all inputs ", Toast.LENGTH_LONG).show();;
        }
    }

    private void handleDelay(){
        new Handler().postDelayed(() -> startActivity(new Intent(PinResetActivity.this,LoginActivity.class)),1500);
    }
}