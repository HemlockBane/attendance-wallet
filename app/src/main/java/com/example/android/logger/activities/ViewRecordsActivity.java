package com.example.android.logger.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.logger.R;
import com.example.android.logger.adapters.ViewRecordsRecyclerAdapter;
import com.example.android.logger.models.Employee;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewRecordsActivity extends AppCompatActivity {
    public ArrayList<Employee> recordsList;
    ViewRecordsRecyclerAdapter viewRecordsRecyclerAdapter;

    private FloatingActionButton fabCreateAttendance;
    private ProgressBar progressBarCreateAttendance;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ChildEventListener queryChildEventListener;
    private Query attendanceQuery;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_list);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("attendance");


        progressBarCreateAttendance = findViewById(R.id.pb_create_attendance);

        fabCreateAttendance = findViewById(R.id.fab_create_attendance);
        fabCreateAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long timeInMillisecs = System.currentTimeMillis();

                Date dateObject = new Date(timeInMillisecs);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateObject);

                String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
                String month = Integer.toString(calendar.get(Calendar.MONTH + 1));
                String year = Integer.toString(calendar.get(Calendar.YEAR));

                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                int sunday = Calendar.SUNDAY;
                int saturday = Calendar.SATURDAY;



                if (dayOfWeek == sunday || dayOfWeek == saturday){
                    Toast.makeText(ViewRecordsActivity.this, "It's weekend, you can register!", Toast.LENGTH_SHORT).show();

                    Intent createAttendanceIntent = new Intent(ViewRecordsActivity.this, NewRecordActivity.class);
                    startActivity(createAttendanceIntent);
                }else{

                }
            }
        });

        // Get a reference to the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        // Get a reference to a LinearLayoutManger
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // Set the the LinearLayoutManager as the RecyclerView's layout manager
        recyclerView.setLayoutManager(linearLayoutManager);

        recordsList = new ArrayList<>();

        viewRecordsRecyclerAdapter = new ViewRecordsRecyclerAdapter(this, recordsList);
        recyclerView.setAdapter(viewRecordsRecyclerAdapter);

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    Employee employee = dataSnapshot.getValue(Employee.class);
                    recordsList.add(employee);

                    progressBarCreateAttendance.setVisibility(View.GONE);

                    viewRecordsRecyclerAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);




    }



}
