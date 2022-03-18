package com.larntech.aliesa.dashboard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.larntech.aliesa.R;
import com.larntech.aliesa.add_restaurant.AddRestaurantActivity;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initData();
    }
    private void initData(){
        floatingActionButton = findViewById(R.id.addRestaurants);
        clickListener();
    }

    private void clickListener(){
        floatingActionButton.setOnClickListener(view -> {
            addNewRestaurant();
        });
    }

    private void addNewRestaurant(){
        Intent intent = new Intent(this, AddRestaurantActivity.class);
        startActivityForResult(intent,1002);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == 1002){
            if(data != null && data.getExtras() != null) {
                String restaurantName = data.getStringExtra("name");
                String restaurantLocation = data.getStringExtra("location");
                String restaurantDesc = data.getStringExtra("desc");

            }
        }
    }

    private void saveNewRestaurant(String name, String location, String description){

    }

    private void initFirebase(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        initFirebase();
        super.onStart();
    }
}