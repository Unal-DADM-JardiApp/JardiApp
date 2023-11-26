package com.unal.jardiapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.unal.jardiapp.model.Plant;

public class NewPlantActivity extends AppCompatActivity {

    EditText plantNameTxt, speciesTxt;
    Button addPlantButtn, addImageBttn;
    ImageView image;
    private final int GALLERY_REQ_CODE = 1;
    private AutoCompleteTextView autoCompleteTextViewRecordatorio, autoCompleteTextViewPrioridad;
    private ArrayAdapter<String> adarpterRecordatorio, adarpterPrioridad;

    private String[] item_recordatorio = {"Cada día", "Cada 3 días", "Cada 5 días", "Cada semana"};
    private String[] item_prioridad = {"Baja", "Media", "Alta"};

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

        plantNameTxt = findViewById(R.id.nombreEditTxt);
        speciesTxt = findViewById(R.id.especieEditTxt);
        addPlantButtn = findViewById(R.id.createPlantButton);
        image = findViewById(R.id.plantImg);
        addImageBttn = findViewById(R.id.addImgBttn);

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
                ImagePicker.with(NewPlantActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        addPlantButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savePlant())
                    Toast.makeText(NewPlantActivity.this, "Planta creada y almacenada", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(NewPlantActivity.this, "Se presentó un error, no se pudo crear el registro de la planta", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            //if (requestCode == GALLERY_REQ_CODE){
            image.setImageURI(data.getData());
            //}
        }
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

    /**
     * Method for save plant in Database
     * @return true if an autocremental id was created and the registry was saved,
     * otherwise, return false.
     */
    private boolean savePlant(){
        Plant plant = new Plant();
        plant.setName(plantNameTxt.getText().toString());
        plant.setSpecies(speciesTxt.getText().toString());
        plant.setPriority(autoCompleteTextViewPrioridad.getText().toString());
        plant.setReminder(autoCompleteTextViewRecordatorio.getText().toString());
        plant.setSensor(""); // TODO: expecting to manage the sensor information
        // TODO: call the DAO for insert and retrieve the autoincrementable id
        return false; // FIXME: if id != null then true, else false.

    }
}
