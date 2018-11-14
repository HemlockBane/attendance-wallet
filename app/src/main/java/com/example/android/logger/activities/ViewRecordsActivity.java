package com.example.android.logger.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.logger.R;

public class ViewRecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_list);
    }
}
