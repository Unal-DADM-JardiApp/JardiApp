package com.unal.jardiapp.user;

import android.widget.ImageView;

public class User {
    private String names;
    private String email;
    private ImageView image;

    public String getNames() {
        return names;
    }

    public String getEmail() {
        return email;
    }

    public ImageView getImage() {
        return image;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
