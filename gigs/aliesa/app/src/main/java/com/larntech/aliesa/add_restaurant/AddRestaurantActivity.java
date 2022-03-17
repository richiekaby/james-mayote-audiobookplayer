package com.larntech.aliesa.add_restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.larntech.aliesa.R;

public class AddRestaurantActivity extends AppCompatActivity {

    private EditText edName, edLocation, edDescription;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        initData();
    }

    private void initData(){
        edName = findViewById(R.id.edName);
        edLocation  = findViewById(R.id.edLocation);
        edDescription = findViewById(R.id.edDescription);
        btnAdd = findViewById(R.id.btnAdd);
        clickListener();
    }

    private void clickListener(){
        btnAdd.setOnClickListener(view -> {
            getUserDetails();
        });
    }

    private void getUserDetails(){
        if(edName.getText().length() > 0 && edLocation.getText().length() > 0 && edDescription.getText().length() > 0) {
            String name = edName.getText().toString();
            String location = edLocation.getText().toString();
            String description = edDescription.getText().toString();
            setData(name,location,description);
        }else{
            showMessage("All inputs required ...");
        }
    }

    private void setData(String name, String location, String description){
        Intent intent=new Intent();
        intent.putExtra("name",name);
        intent.putExtra("location",location);
        intent.putExtra("desc",description);
        setResult(1002,intent);
        finish();

    }



    private void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

}