package com.unal.jardiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.unal.jardiapp.user.User;

public class ProfileActivity extends AppCompatActivity {
    private ImageView photoImageView;
    private ImageView photoProfileImageView;
    private TextView nameText;
    private TextView emailText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        photoImageView = (ImageView) mToolbar.findViewById(R.id.profile_circlex);
        photoProfileImageView = (ImageView) findViewById(R.id.profileImage);
        nameText = (TextView) findViewById(R.id.nombreTxVw);
        emailText = (TextView) findViewById(R.id.emailTextView);

        //photoImageView = user.getImage();
        //photoProfileImageView = user.getImage();
        nameText.setText(getIntent().getExtras().getString("nombres"));
        emailText.setText(getIntent().getExtras().getString("email"));

        //Glide.with(this).load(user.getImage().into(photoImageView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem profileItem = (MenuItem) menu.findItem(R.id.profile);
        profileItem.setVisible(false);
        this.invalidateOptionsMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.main_screen) {
            goMainScreen();
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
            return true;
        }
        return false;
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("nombres", nameText.getText().toString());
        intent.putExtra("email", emailText.getText().toString());
        startActivity(intent);
    }

    private void goNewPlantScreen() {
        Intent intent = new Intent(this, NewPlantActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("nombres", nameText.getText().toString());
        intent.putExtra("email", emailText.getText().toString());
        startActivity(intent);
    }
}
