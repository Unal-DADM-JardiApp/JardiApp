package com.unal.jardiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unal.jardiapp.adapters.PlantAdapterList;
import com.unal.jardiapp.dao.PlantDAO;
import com.unal.jardiapp.model.Plant;

import java.util.ArrayList;

public class PlantListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

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
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        globalIntent = getIntent();
        photoImageView = (ImageView) mToolbar.findViewById(R.id.profile_circlex);
        Glide.with(this).load(globalIntent.getData()).into(photoImageView);

        plantList = findViewById(R.id.plantList);
        searchText = findViewById(R.id.searchText);

        plantList.setLayoutManager(new LinearLayoutManager(this));
        PlantDAO plantDAO = new PlantDAO(PlantListActivity.this);
        plantArrayList  = new ArrayList<>();
        plantAdapter = new PlantAdapterList(plantDAO.showPlants());
        plantList.setAdapter(plantAdapter);

        searchText.setOnQueryTextListener(this);

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
