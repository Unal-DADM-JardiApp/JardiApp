package com.unal.jardiapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.unal.jardiapp.dao.PlantDAO;
import com.unal.jardiapp.model.Plant;

public class EditPlantActivity extends AppCompatActivity {

    static final int DIALOG_UPDATE_ID = 0;
    static final int DIALOG_DELETE_ID = 1;
    EditText plantNameTxt, speciesTxt;
    Button addPlantButtn, addImageBttn, editplantBttn;
    ImageView plantImage, profileImage;
    ImageButton deleteButton;
    String plantID;
    private AutoCompleteTextView autoCompleteTextViewRecordatorio, autoCompleteTextViewPrioridad;
    private ArrayAdapter<String> adarpterRecordatorio, adarpterPrioridad;

    private String[] item_recordatorio = {"Cada día", "Cada 3 días", "Cada 5 días", "Cada semana"};
    private String[] item_prioridad = {"Baja", "Media", "Alta"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_new_plant);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent = getIntent();

        plantNameTxt = findViewById(R.id.nombreEditTxt);
        speciesTxt = findViewById(R.id.especieEditTxt);
        addPlantButtn = findViewById(R.id.createPlantButton);
        plantImage = findViewById(R.id.plantImg);
        addImageBttn = findViewById(R.id.addImgBttn);
        addPlantButtn.setVisibility(View.INVISIBLE);
        editplantBttn = findViewById(R.id.editPlantButton);
        deleteButton = findViewById(R.id.deleteButton);

        plantNameTxt.setText(getIntent().getExtras().getString("name"));
        speciesTxt.setText(getIntent().getExtras().getString("species"));
        plantID = getIntent().getExtras().getString("ID");

        autoCompleteTextViewRecordatorio = findViewById(R.id.auto_complete_recordatorio);
        adarpterRecordatorio = new ArrayAdapter<>(this, R.layout.list_item, item_recordatorio);
        autoCompleteTextViewRecordatorio.setAdapter(adarpterRecordatorio);

        autoCompleteTextViewPrioridad = findViewById(R.id.auto_complete_prioridad);
        adarpterPrioridad = new ArrayAdapter<>(this, R.layout.list_item, item_prioridad);
        autoCompleteTextViewPrioridad.setAdapter(adarpterPrioridad);

        autoCompleteTextViewRecordatorio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });

        autoCompleteTextViewPrioridad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });

        addImageBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(EditPlantActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        editplantBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (plantNameTxt.getText().toString().equals("") || speciesTxt.getText().toString().equals("") || autoCompleteTextViewPrioridad.getText().toString().equals("") || autoCompleteTextViewRecordatorio.getText().toString().equals(""))
                    Toast.makeText(EditPlantActivity.this, "Debe llenar todos los cambios antes de guardar la planta", Toast.LENGTH_LONG).show();
                else{
                    if (savePlant()){
                        Toast.makeText(EditPlantActivity.this, "Planta actualizada", Toast.LENGTH_LONG).show();
                        goPlantViewScreen();
                    }
                    else
                        Toast.makeText(EditPlantActivity.this, "Se presentó un error, no se pudo actualizar el registro de la planta", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void onClick(View view) {
        int id = view.getId();
        if (R.id.deleteButton == id){
            showDialog(DIALOG_DELETE_ID);
        }
    }

    private void goPlantViewScreen() {
        Intent intent = new Intent(this, ViewPlantActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ID", plantID);
        startActivity(intent);
    }

    private void goPlantListScreen() {
        Intent intent = new Intent(this, PlantListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private boolean savePlant(){
        PlantDAO dao = new PlantDAO(this);
        Plant plant = new Plant();
        plant.setId(plantID);
        plant.setName(plantNameTxt.getText().toString());
        plant.setSpecies(speciesTxt.getText().toString());
        plant.setPriority(autoCompleteTextViewPrioridad.getText().toString());
        plant.setReminder(autoCompleteTextViewRecordatorio.getText().toString());
        plant.setSensor(""); // TODO: expecting to manage the sensor information
        // TODO: call the DAO for insert and retrieve the autoincrementable id
        dao.update(plant);
        return true; // FIXME: if id != null then true, else false.

    }

    private void deletePlant(){
        PlantDAO dao = new PlantDAO(this);
        Plant plant = new Plant();
        plant.setId(plantID);
        // TODO: call the DAO for insert and retrieve the autoincrementable id
        dao.delete(plant);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(id) {
            case DIALOG_DELETE_ID:
                builder.setMessage(R.string.delete_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deletePlant();
                                goPlantListScreen();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
        }
        dialog = builder.create();
        return dialog;
    }

}
