package com.unal.jardiapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.unal.jardiapp.dao.PlantDAO;
import com.unal.jardiapp.model.Plant;

public class ViewPlantActivity extends AppCompatActivity {

    private ImageView photoImageView, plantImage;
    private EditText plantName, plantHumidity, plantBrigtness;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plant);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        photoImageView = (ImageView) mToolbar.findViewById(R.id.profile_circlex);
        plantImage = (ImageView) findViewById(R.id.plantImg);
        plantName = findViewById(R.id.nombreTxt);
        plantHumidity = findViewById(R.id.humidityTxt);
        plantBrigtness = findViewById(R.id.luminosityTxt);

        PlantDAO plantDAO = new PlantDAO(ViewPlantActivity.this);
        Plant plant = plantDAO.showPlant(getIntent().getExtras().getInt("ID"));
        plantName.setText(plant.getName());
        plantHumidity.setText(plant.getHumidity());
        plantBrigtness.setText(plant.getBrightness());
        
        Glide.with(this).load(getIntent().getData()).into(photoImageView);
    }
}
