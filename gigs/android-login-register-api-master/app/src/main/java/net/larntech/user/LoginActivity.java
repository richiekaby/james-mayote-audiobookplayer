package net.larntech.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
     Button btnLogin;
     EditText edUsername, edPassword;
     TextView tvForgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        edUsername = findViewById(R.id.et_email);
        edPassword = findViewById(R.id.et_password);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);

        tvForgetPassword.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, UsernameActivity.class)));
//
        btnLogin.setOnClickListener(view -> {
            if(TextUtils.isEmpty(edUsername.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString()) ) {
                String message = "All inputs required ..";
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(LoginActivity.this,"Authenticating user ...",Toast.LENGTH_SHORT).show();
                loginUser(1,edUsername.getText().toString(),edPassword.getText().toString());
            }

        });

    }

    public void loginUser(Integer ptipoUsuario, String pUsuario, String pClave){
        Call<String> loginResponseCall = ApiClient.getService().loginUser(ptipoUsuario, pUsuario,pClave);
        loginResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    LoginResponse loginResponse = gson.fromJson(response.body() , LoginResponse.class);

                    if (loginResponse.getNom_paciente().equals("0")) {
                        Toasty.error(LoginActivity.this, "No such user found.", Toast.LENGTH_SHORT, true).show();

                    }else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                .putExtra("data", loginResponse)
                                .putExtra("pId",Integer.parseInt(pUsuario)));
                        finish();
                    }


                }else{
                    String message = "An error occurred please try again later ...";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}