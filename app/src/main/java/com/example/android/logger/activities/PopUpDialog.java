package com.example.android.logger.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.logger.R;

public class PopUpDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_dialog);

        PopScreen popScreen = new PopScreen();
        popScreen.show(getSupportFragmentManager(), "Test Dialog");
    }
}
