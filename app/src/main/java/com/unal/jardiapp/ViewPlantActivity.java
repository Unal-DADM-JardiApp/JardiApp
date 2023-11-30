package com.unal.jardiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import java.util.Objects;

public class ViewPlantActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ImageView photoImageView, plantImage;
    private EditText plantName, plantHumidity, plantBrigtness;
    private Button editButton;
    String idPlant;
    Plant plant;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plant);
        FirebaseApp.initializeApp(this);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        photoImageView = (ImageView) mToolbar.findViewById(R.id.profile_circlex);
        plantImage = (ImageView) findViewById(R.id.plantImg);
        plantName = findViewById(R.id.nombreTxt);
        plantHumidity = findViewById(R.id.humidityTxt);
        plantBrigtness = findViewById(R.id.luminosityTxt);
        idPlant = getIntent().getExtras().getString("ID");
        editButton = findViewById(R.id.editBttn);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goEditScreen();
            }
        });
        //PlantDAO plantDAO = new PlantDAO(ViewPlantActivity.this);
        //Plant plant = plantDAO.showPlant(getIntent().getExtras().getInt("ID"));
        //plantName.setText(plant.getName());
        //plantHumidity.setText(plant.getHumidity());
        //plantBrigtness.setText(plant.getBrightness());
        showPlant();
        //Glide.with(this).load(getIntent().getData()).into(photoImageView);
    }

    private void goEditScreen() {
        Intent intent = new Intent(this, EditPlantActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("name", plant.getName());
        intent.putExtra("species", plant.getSpecies());
        intent.putExtra("priority", plant.getPriority());
        intent.putExtra("reminder", plant.getReminder());
        intent.putExtra("ID", plant.getId());
        startActivity(intent);
    }

    public void showPlant(){

        readData(new OnGetDataListener() {
            @Override
            public void onCallback(ArrayList<Plant> plants) {

            }

            @Override
            public void onCallback(Plant plant) {
                plantName.setText(plant.getName());
                plantHumidity.setText(plant.getHumidity());
                plantBrigtness.setText(plant.getBrightness());
            }

        });
    }


    public void readData(OnGetDataListener myCallback) {
        database.getReference().child("plants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Plant plantQuery = dataSnapshot.getValue(Plant.class);
                    Objects.requireNonNull(plantQuery).setId(dataSnapshot.getKey());
                    if (plantQuery.getId().equals(idPlant)){
                        plant = plantQuery;
                        break;
                    }

                }
                myCallback.onCallback(plant);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error: ", error.getMessage());
            }

        });
    }


}
