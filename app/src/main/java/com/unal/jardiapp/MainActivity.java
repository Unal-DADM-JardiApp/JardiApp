package com.unal.jardiapp;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unal.jardiapp.user.User;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseUser fireBaseUser;
    private Button profileBttn, plantsBttn, newPlantBttn;
    private ImageView photoImageView;
    private String nameText;
    private String emailText;
    //private TextView idTextView;

    private GoogleApiClient googleApiClient;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        photoImageView = (ImageView) mToolbar.findViewById(R.id.profile_circlex);

        //nameTextView = (TextView) findViewById(R.id.namesTextView);
        //emailTextView = (TextView) findViewById(R.id.emailTextView);

        profileBttn = findViewById(R.id.profileButton);
        profileBttn.setOnClickListener(new ProfileButtonClickListener());

        newPlantBttn = findViewById(R.id.createPlantButton);
        newPlantBttn.setOnClickListener(new NewPlantButtonClickListener());

        plantsBttn = findViewById(R.id.plantsButton);
        plantsBttn.setOnClickListener(new PlantsButtonClickListener());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    fireBaseUser = user;
                    setUserData(fireBaseUser);
                } else {
                    goLogInScreen();
                }
            }
        };
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
            goPlantsScreen();
            return true;
        } else if (itemId == R.id.products) {
            return true;
        } else if (itemId == R.id.care_tips) {
            return true;
        } else if (itemId == R.id.support) {
            return true;
        } else if (itemId == R.id.quit) {
            logOut();
            revoke();
            goLogInScreen();
            return true;
        }
        return false;
    }

    private void setUserData(FirebaseUser user) {
        nameText =user.getDisplayName().toString();
        emailText = user.getEmail().toString();
        //idTextView.setText(user.getUid());
        Glide.with(this).load(user.getPhotoUrl()).into(photoImageView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut(/*View view*/) {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.not_close_session, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void revoke(/*View view*/) {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.not_revoke, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    private void goProfileScreen() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("nombres", nameText);
        intent.putExtra("email", emailText);
        intent.setData(fireBaseUser.getPhotoUrl());
        startActivity(intent);
    }

    private void goPlantsScreen() {
        Intent intent = new Intent(this, PlantListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("nombres", nameText);
        intent.putExtra("email", emailText);
        intent.setData(fireBaseUser.getPhotoUrl());
        startActivity(intent);
    }

    private void goNewPlantScreen() {
        Intent intent = new Intent(this, NewPlantActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("nombres", nameText);
        intent.putExtra("email", emailText);
        intent.setData(fireBaseUser.getPhotoUrl());
        startActivity(intent);
    }

    private class ProfileButtonClickListener implements View.OnClickListener {
        public ProfileButtonClickListener(){}
        public void onClick(View view) {
            goProfileScreen();
        }
    }

    private class NewPlantButtonClickListener implements View.OnClickListener {
        public NewPlantButtonClickListener(){}
        public void onClick(View view) {
            goNewPlantScreen();
        }
    }

    private class PlantsButtonClickListener implements View.OnClickListener {
        public PlantsButtonClickListener(){}
        public void onClick(View view) {
            goPlantsScreen();
        }
    }

    public User getUserData(){
        User user = new User();
        user.setNames(nameText);
        user.setEmail(emailText);
        user.setImage(photoImageView);
        return user;
    }

}