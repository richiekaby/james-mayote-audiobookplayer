package net.larntech.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class SucussalesActivity extends AppCompatActivity implements SucuSsalesAdapter.SucuSsalesClickListener{

    private List<SucuSsalesResponse> sucuSsalesResponseList;
    private Intent intent;
    private RecyclerView recyclerview;
    private Toolbar toolbar;
    private SucuSsalesAdapter sucuSsalesAdapter;
    private String updatedFileName;
    public String fileDirectory;
    // Progress Dialog
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucussales);
        initData();
    }

    private void initData(){
        recyclerview = findViewById(R.id.recyclerview);
        toolbar = findViewById(R.id.toolbar);
        setToolbar();

        intent = getIntent();
        sucuSsalesResponseList = new ArrayList<>();
        extractSucuSsales();
    }

    private void setToolbar(){
        this.setSupportActionBar(toolbar);
        this.setSupportActionBar(toolbar);
    }




    private void extractSucuSsales(){
        if(intent.getExtras() != null) {
            String resultados = intent.getStringExtra("resultados");

            Type type = new TypeToken<List<SucuSsalesResponse>>() {
            }.getType();
            Gson gson = new Gson();
            sucuSsalesResponseList = gson.fromJson(resultados, type);
            prepareRecyclerView();
        }else{
            Toast.makeText(this," Data not found ", Toast.LENGTH_SHORT).show();
        }

        setTitle();
    }



    private void setTitle(){
        this.getSupportActionBar().setTitle("Sucursales");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24);

    }

    public void prepareRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(linearLayoutManager);
        preAdapter();
    }

    public void preAdapter(){
        sucuSsalesAdapter = new SucuSsalesAdapter(sucuSsalesResponseList,this, this);
        recyclerview.setAdapter(sucuSsalesAdapter);
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
                sucuSsalesAdapter.getFilter().filter(newText);
                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void selectedResult(SucuSsalesResponse sucuSsalesResponse) {
        openMaps(sucuSsalesResponse);
    }

    private void openMaps(SucuSsalesResponse sucuSsalesResponse){
        String strUri = "http://maps.google.com/maps?q=loc:" + sucuSsalesResponse.getNum_latitud() + "," + sucuSsalesResponse.getNum_longitud() + " (" + sucuSsalesResponse.getDsc_direccion() + ")";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

        startActivity(intent);
    }




    private void downloadTask(String url) {

        String name = url.substring(url.lastIndexOf('/') + 1);
        File file = new File(Environment.getExternalStorageDirectory(), "Download");
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        File result = new File(file.getAbsolutePath() + File.separator + name);
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationUri(Uri.fromFile(result));
        request.setTitle("Downloading Please wait...");
        request.setDescription(name);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
        }
        Toast.makeText(this, "Starting download...", Toast.LENGTH_SHORT).show();
        MediaScannerConnection.scanFile(this, new String[]{result.toString()}, null,
                (path, uri) -> Toasty.success(SucussalesActivity.this,"Download complete "));


    }

    private void downloadFileMessage(String url){
        new AlertDialog.Builder(this)
                .setTitle("Download File")
                .setMessage("You are about to download a file, Please yes to proceed.")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        downloadTask(url);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}