package com.unal.jardiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NewPlantActivity extends AppCompatActivity {

    private String[] item_recordatorio = {"Cada día", "Cada 3 días", "Cada 5 días", "Cada semana"};
    private AutoCompleteTextView autoCompleteTextViewRecordatorio;
    private ArrayAdapter<String> adarpterRecordatorio;

    private String[] item_prioridad = {"Baja", "Media", "Alta"};
    private AutoCompleteTextView autoCompleteTextViewPrioridad;
    private ArrayAdapter<String> adarpterPrioridad;

    private String nameText;
    private String emailText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_new_plant);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent = getIntent();
        nameText = getIntent().getExtras().getString("nombres");
        emailText = getIntent().getExtras().getString("email");
        //photoImageView = (ImageView) mToolbar.findViewById(R.id.profile_circlex);
        autoCompleteTextViewRecordatorio = findViewById(R.id.auto_complete_recordatorio);
        adarpterRecordatorio = new ArrayAdapter<>(this, R.layout.list_item, item_recordatorio);
        autoCompleteTextViewRecordatorio.setAdapter(adarpterRecordatorio);
        autoCompleteTextViewRecordatorio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });

        autoCompleteTextViewPrioridad = findViewById(R.id.auto_complete_prioridad);
        adarpterPrioridad = new ArrayAdapter<>(this, R.layout.list_item, item_prioridad);
        autoCompleteTextViewPrioridad.setAdapter(adarpterPrioridad);
        autoCompleteTextViewPrioridad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem newPlantScreenItem = (MenuItem) menu.findItem(R.id.new_plant);
        newPlantScreenItem.setVisible(false);
        this.invalidateOptionsMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.profile) {
            goProfileScreen();
            return true;
        } else if (itemId == R.id.main_screen) {
            goMainScreen();
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
            return true;
        }
        return false;
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goProfileScreen() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("nombres", nameText);
        intent.putExtra("email", emailText);
        startActivity(intent);
    }

}
