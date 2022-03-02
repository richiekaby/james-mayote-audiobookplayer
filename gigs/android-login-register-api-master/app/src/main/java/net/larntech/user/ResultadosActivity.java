package net.larntech.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ResultadosActivity extends AppCompatActivity implements ResultadosAdapter.ResultadosResClickListener {

    private List<ResultadosResponse> resultadosResponseList;
    private Intent intent;
    private RecyclerView recyclerview;
    private Toolbar toolbar;
    private ResultadosAdapter resultadosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        initData();
    }

    private void initData(){
        recyclerview = findViewById(R.id.recyclerview);
        toolbar = findViewById(R.id.toolbar);
        setToolbar();

        intent = getIntent();
        resultadosResponseList = new ArrayList<>();
        extractMisResultados();
    }

    private void setToolbar(){
        this.setSupportActionBar(toolbar);
        this.setSupportActionBar(toolbar);
    }




    private void extractMisResultados(){
        if(intent.getExtras() != null) {
            String resultados = intent.getStringExtra("resultados");

            Type type = new TypeToken<List<ResultadosResponse>>() {
            }.getType();
            Gson gson = new Gson();
            resultadosResponseList = gson.fromJson(resultados, type);
            prepareRecyclerView();
        }else{
            Toast.makeText(this," Data not found ", Toast.LENGTH_SHORT).show();
        }

        setTitle();
    }



    private void setTitle(){
            this.getSupportActionBar().setTitle("Ordenes de Laboratorio");
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24);

    }

    public void prepareRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(linearLayoutManager);
        preAdapter();
    }

    public void preAdapter(){
        resultadosAdapter = new ResultadosAdapter(resultadosResponseList,this, this);
        recyclerview.setAdapter(resultadosAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.searchView){
            return true;
        }else if(id == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem menuItem = menu.findItem(R.id.searchView);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                resultadosAdapter.getFilter().filter(newText);
                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void selectedResult(ResultadosResponse resultadosResponse) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(resultadosResponse.getImg_qr_link()));
        startActivity(browserIntent);
//        startActivity(new Intent(this,WebActivity.class).putExtra("data",resultadosResponse.getImg_qr_link()));
//        Toast.makeText(this,resultadosResponse.getLaboratorio(), Toast.LENGTH_LONG).show();
    }


}