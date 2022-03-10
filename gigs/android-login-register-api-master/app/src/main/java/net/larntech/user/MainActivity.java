package net.larntech.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
   private LoginResponse loginResponse;
   private TextView username;
   private CardView cvMisResultados, cvSucurSales;
   private Intent intent;
   private int pId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();


    }

    private void initData(){
        username = findViewById(R.id.username);
        cvMisResultados = findViewById(R.id.cvMisResultados);
        cvSucurSales = findViewById(R.id.cvSucurSales);
        intent = getIntent();
        setUserName();
        clickListener();
    }

    private void setUserName(){
        if(intent.getExtras() != null){
            loginResponse = (LoginResponse) intent.getSerializableExtra("data");
            pId = intent.getIntExtra("pId",0);
            Log.e(" pid "," ==> "+pId);
            username.setText(loginResponse.getNom_paciente());

        }
    }

    private void clickListener(){

        cvMisResultados.setOnClickListener(view ->
        {
            Toast.makeText(this, "Fetching, Please wait ...", Toast.LENGTH_LONG).show();
            getMisResultados();
        });

        cvSucurSales.setOnClickListener(view -> {
            Toast.makeText(this, "Fetching, Please wait ...", Toast.LENGTH_LONG).show();
            getSucuSsales();

        }
        );



    }

    private void getMisResultados() {
        Call<String> allResultados = ApiClient.getService().getAllResultados(pId);
//        Call<String> allResultados = ApiClient.getService().getAllResultados(118790988);
        allResultados.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                   String result =  response.body();

                   if(result != null ) {


                       //remove back slash
                       String resultadosResponses = result.replaceAll("\\\\", "");
                       showResults(resultadosResponses);

                   }else{
                       Toast.makeText(MainActivity.this," No details found ", Toast.LENGTH_SHORT).show();
                   }


                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    private void getSucuSsales() {
//        Call<String> allResultados = ApiClient.getService().getAllResultados(pId);
        Call<String> allResultados = ApiClient.getService().getAllSucursales(106320395);
        allResultados.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                   String result =  response.body();

                   if(result != null ) {
                       //remove back slash
                       String resultadSucuSsales = result.replaceAll("\\\\", "");
                       Log.e(" data "," ==> "+resultadSucuSsales);
                       showSucusale(resultadSucuSsales);

                   }else{
                       Toast.makeText(MainActivity.this," No details found ", Toast.LENGTH_SHORT).show();
                   }


                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    private void showResults(String resultadosResponseList){

        if(!resultadosResponseList.equals("")){
            startActivity(new Intent(this,ResultadosActivity.class).putExtra("resultados", resultadosResponseList));
        }else{
            Toast.makeText(MainActivity.this," Result list is null ", Toast.LENGTH_SHORT).show();
        }

    }

    private void showSucusale(String sucusale){
        if(!sucusale.equals("")){
            startActivity(new Intent(this,SucussalesActivity.class).putExtra("resultados", sucusale));
        }else{
            Toast.makeText(MainActivity.this," Sucusale list is null ", Toast.LENGTH_SHORT).show();
        }

    }


}