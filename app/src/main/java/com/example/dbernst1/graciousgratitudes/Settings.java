package com.example.dbernst1.graciousgratitudes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Settings extends AppCompatActivity {

    public static final String KEY_PREF_ORIENTATION_SWITCH = "default_orientation";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content,new SettingsFragment()).commit();
    }
}
