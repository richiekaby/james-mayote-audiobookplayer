package com.larntech.aliesa.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.larntech.aliesa.map.MapsActivity;
import com.larntech.aliesa.R;
import com.larntech.aliesa.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edEmail, edPassword;
    private Button btnLogin;
    private TextView createAccount;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
    }

    private void initData(){
        edEmail = findViewById(R.id.etEmail);
        edPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        createAccount = findViewById(R.id.tvCreateAccount);
        mAuth = FirebaseAuth.getInstance();
        clickListener();
    }

    private void clickListener(){
        btnLogin.setOnClickListener(view -> getUserDetails());
        createAccount.setOnClickListener(view -> goToRegister());
    }

    private void getUserDetails(){
        if(edEmail.getText().length() > 0 && edPassword.getText().length() > 0){
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            performAuth(email,password);
        }else{
            showMessage("All inputs required ...");
        }
    }

    private void goToRegister(){
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    private void performAuth(String email, String password){
        showMessage("Authenticating user ...");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            registerSuccessful(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            showMessage( "Authentication failed.");
                        }
                    }
                });
    }

    private void registerSuccessful(FirebaseUser user){
        startActivity(new Intent(this, MapsActivity.class));
        finish();
    }

    private void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

}