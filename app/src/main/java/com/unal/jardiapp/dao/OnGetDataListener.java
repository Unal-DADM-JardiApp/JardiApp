package com.unal.jardiapp.dao;

import com.unal.jardiapp.model.Plant;

import java.util.ArrayList;
import java.util.List;

public interface OnGetDataListener {
    void onCallback(ArrayList<Plant> plants);
    void onCallback(Plant plant);

}
