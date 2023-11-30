package com.unal.jardiapp.dao;

import android.content.Context;

import androidx.annotation.Nullable;

import com.unal.jardiapp.model.Plant;

import java.util.ArrayList;

public class PlantDAO {

    Context context;

    public PlantDAO(@Nullable Context context) {
        //super(context);
        this.context = context;
    }

    /**
     * Method for testing
     * @return list of plants
     */
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
    }

    public Plant showPlant(int id){
        Plant plant = new Plant();
        plant.setId(1);
        plant.setName("Planti");
        plant.setSpecies("Ceropegia woodii");
        plant.setHumidity("40%");
        plant.setBrightness("150 lm");

        return plant;
    }
}
