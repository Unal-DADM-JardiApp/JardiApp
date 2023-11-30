package com.unal.jardiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unal.jardiapp.adapters.PlantAdapterList;
import com.unal.jardiapp.dao.OnGetDataListener;
import com.unal.jardiapp.dao.PlantDAO;
import com.unal.jardiapp.model.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class PlantListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView plantList;
    ArrayList<Plant> plantArrayList;
    PlantAdapterList plantAdapter;
    SearchView searchText;
    private ImageView photoImageView;
    private String nameText;
    private String emailText;

    Intent globalIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        FirebaseApp.initializeApp(this);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        globalIntent = getIntent();
        photoImageView = (ImageView) mToolbar.findViewById(R.id.profile_circlex);
        Glide.with(this).load(globalIntent.getData()).into(photoImageView);

        plantList = findViewById(R.id.plantList);
        searchText = findViewById(R.id.searchText);

        //PlantDAO plantDAO = new PlantDAO(this);
        //plantList.setLayoutManager(new LinearLayoutManager(this));
        //plantArrayList  = new ArrayList<>();
        //plantAdapter = new PlantAdapterList(plantDAO.showPlants());
        //plantList.setAdapter(plantAdapter);

        searchText.setOnQueryTextListener(this);
        showPlants();
    }


    public void showPlants(){

        plantArrayList = new ArrayList<>();

        readData(new OnGetDataListener() {
            @Override
            public void onCallback(ArrayList<Plant> plants) {
                plantList.setLayoutManager(new LinearLayoutManager(PlantListActivity.this));

                plantAdapter = new PlantAdapterList(plants);
                plantList.setAdapter(plantAdapter);
            }

            @Override
            public void onCallback(Plant plant) {

            }

        });
    }


    public void readData(OnGetDataListener myCallback) {
        database.getReference().child("plants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Plant plant = dataSnapshot.getValue(Plant.class);
                    Objects.requireNonNull(plant).setId(dataSnapshot.getKey());
                    plantArrayList.add(plant);
                }
                myCallback.onCallback(plantArrayList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error: ", error.getMessage());
            }

        });
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        plantAdapter.filter(s);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem mainScreenItem = (MenuItem) menu.findItem(R.id.main_screen);
        mainScreenItem.setVisible(false);
        this.invalidateOptionsMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.profile) {
            goProfileScreen();
            return true;
        } else if (itemId == R.id.new_plant) {
            goNewPlantScreen();
            return true;
        } else if (itemId == R.id.my_plants) {
            return true;
        } else if (itemId == R.id.products) {
            return true;
        } else if (itemId == R.id.care_tips) {
            return true;
        } else if (itemId == R.id.support) {
            return true;
        } else if (itemId == R.id.quit) {
            //logOut();
            //revoke();
            goLogInScreen();
            return true;
        }
        return false;
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goProfileScreen() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("nombres", nameText);
        intent.putExtra("email", emailText);
        intent.setData(globalIntent.getData());
        startActivity(intent);
    }

    private void goNewPlantScreen() {
        Intent intent = new Intent(this, NewPlantActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("nombres", nameText);
        intent.putExtra("email", emailText);
        intent.setData(globalIntent.getData());
        startActivity(intent);
    }
}
