package com.larntech.aliesa.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.larntech.aliesa.R;
import com.larntech.aliesa.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText edEmail, edPassword, edCpassword;
    private Button btnRegister;
    private TextView tvLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initData();
    }

    private void initData(){
        edEmail = findViewById(R.id.etEmail);
        edPassword = findViewById(R.id.etPassword);
        edCpassword = findViewById(R.id.etcPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);
        mAuth = FirebaseAuth.getInstance();
        clickListener();
    }

    private void clickListener(){
        btnRegister.setOnClickListener(view -> getUserDetails());
        tvLogin.setOnClickListener(view -> goToLogin());
    }

    private void getUserDetails(){

        if(edEmail.getText().length() > 0 && edPassword.getText().length() > 0 && edCpassword.getText().length() > 0){
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            String cPassword = edCpassword.getText().toString();

            if(password.equals(cPassword)){
                performRegister(email,password);
            }else{
                showMessage("Password MissMatch");
            }
        }else{
            showMessage("All inputs required ...");
        }

    }


    private void goToLogin(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void performRegister(String email, String password){
        showMessage("Registering user ...");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            registerSuccessful(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            showMessage("Authentication failed.");

                        }
                    }
                });
    }

    private void registerSuccessful(FirebaseUser user){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}