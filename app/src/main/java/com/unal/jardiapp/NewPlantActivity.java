package com.unal.jardiapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NewPlantActivity extends AppCompatActivity {

    private String[] item_recordatorio = {"Cada día", "Cada 3 días", "Cada 5 días", "Cada semana"};
    private AutoCompleteTextView autoCompleteTextViewRecordatorio;
    private ArrayAdapter<String> adarpterRecordatorio;

    private String[] item_prioridad = {"Baja", "Media", "Alta"};
    private AutoCompleteTextView autoCompleteTextViewPrioridad;
    private ArrayAdapter<String> adarpterPrioridad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_new_plant);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
}
