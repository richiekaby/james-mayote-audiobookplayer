package net.larntech.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            String username = etEmail.getText().toString();
            getSolicitarClave(Integer.parseInt(username));
            Toast.makeText(this,"Sending otp, Please wait...",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this," Please enter valid username ", Toast.LENGTH_LONG).show();;
        }
    }



    private void getSolicitarClave(int pid) {
        Call<String> allResultados = ApiClient.getService().getSolicitarClave(pid);
        allResultados.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                    String result =  response.body();

                    if(result != null ) {

                        String removeEscape = result.replaceAll("\\\\", "");
                        String results = removeEscape.replace("\"","");
                        Log.e(" result "," ==> "+results);

                        if(results.equals("1")) {
                            moveBackTOlogin();
                        }else{
                            showError("Invalid Username");
                        }

                    }else{
                        Toast.makeText(UsernameActivity.this," No details found ", Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void moveBackTOlogin(){
        Toasty.success(this,"Password reset instruction successfully sent to your email.").show();
        new Handler().postDelayed(() -> {
            startActivity(new Intent(UsernameActivity.this, LoginActivity.class));
            finish();
        },1500);

    }

    private void showError(String message){
        Toasty.error(this,message).show();
    }

}