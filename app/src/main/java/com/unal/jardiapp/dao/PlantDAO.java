package com.unal.jardiapp.dao;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unal.jardiapp.model.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlantDAO {

    Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Plant> plantList;
    public PlantDAO(@Nullable Context context) {
        //super(context);
        this.context = context;
        FirebaseApp.initializeApp(context);
    }

    public void insert(Plant plant){
        database.getReference().child("plants").push().setValue(plant).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void update(Plant plant){
        database.getReference().child("plants").child(plant.getId()).setValue(plant).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void delete(Plant plant){
        database.getReference().child("plants").child(plant.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public synchronized ArrayList<Plant> showPlants(){

        plantList = new ArrayList<>();

        readData(new OnGetDataListener() {
            @Override
            public void onCallback(ArrayList<Plant> plants) {
            }

            @Override
            public void onCallback(Plant plant) {

            }

        });
        return plantList;
    }


    public void readData(OnGetDataListener myCallback) {
        database.getReference().child("plants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Plant plant = dataSnapshot.getValue(Plant.class);
                    Objects.requireNonNull(plant).setId(dataSnapshot.getKey());
                    plantList.add(plant);
                }
                myCallback.onCallback(plantList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error: ", error.getMessage());
            }

        });
    }

            /**
     * Method for testing
     * @return list of plants
     */
    /*
    public ArrayList<Plant> showPlants(){
        ArrayList<Plant> plants = new ArrayList<>();
        Plant plant1 = new Plant();
        Plant plant2 = new Plant();

        plant1.setId(1);
        plant1.setName("Planti");
        plant1.setSpecies("Ceropegia woodii");
        plant1.setHumidity("40%");
        plant1.setBrightness("150 lm");

        plant2.setId(2);
        plant2.setSpecies("Ficus Lyrata");
        plant2.setName("Mimosa");
        plant2.setHumidity("630%");
        plant2.setBrightness("89 lm");

        plants.add(plant1);
        plants.add(plant2);
        return plants;
    }*/

    public Plant showPlant(int id){
        Plant plant = new Plant();
        plant.setId("1");
        plant.setName("Planti");
        plant.setSpecies("Ceropegia woodii");
        plant.setHumidity("40%");
        plant.setBrightness("150 lm");

        return plant;
    }
}
