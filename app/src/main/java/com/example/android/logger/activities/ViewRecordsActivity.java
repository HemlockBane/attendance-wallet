package com.example.android.logger.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.logger.R;
import com.example.android.logger.adapters.ViewRecordsRecyclerAdapter;
import com.example.android.logger.models.Employee;

import java.util.ArrayList;

public class ViewRecordsActivity extends AppCompatActivity {
    public ArrayList<Employee> recordsList;
    private FloatingActionButton fabCreateAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_list);

        fabCreateAttendance = findViewById(R.id.fab_create_attendance);
        fabCreateAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createAttendanceIntent = new Intent(ViewRecordsActivity.this, NewRecordActivity.class);
                startActivity(createAttendanceIntent);
            }
        });

        // Get a reference to the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        // Get a reference to a LinearLayoutManger
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // Set the the LinearLayoutManager as the RecyclerView's layout manager
        recyclerView.setLayoutManager(linearLayoutManager);

        recordsList = new ArrayList<>();
        recordsList.add(new Employee("John Oke", "25", "May", "2018",System.currentTimeMillis()));
//        recordsList.add(new Employee("John Oke", "5th May, 2018", "7:45 am"));
//        recordsList.add(new Employee("Yvonne Faith", "5th May, 2018", "7:56 am"));
//        recordsList.add(new Employee("John Doe", "5th May, 2018", "8:45 am"));

        ViewRecordsRecyclerAdapter viewRecordsRecyclerAdapter = new ViewRecordsRecyclerAdapter(this, recordsList);
        recyclerView.setAdapter(viewRecordsRecyclerAdapter);
    }



}
