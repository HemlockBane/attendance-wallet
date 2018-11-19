package com.example.android.logger.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

public class ViewRecordsActivity extends AppCompatActivity implements PopScreen.PopScreenListener {
    public ArrayList<Employee> recordsList;
    ViewRecordsRecyclerAdapter viewRecordsRecyclerAdapter;

    private FloatingActionButton fabCreateAttendance;
    private ProgressBar progressBarCreateAttendance;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ChildEventListener queryChildEventListener;
    private Query attendanceQuery;

    private String dateString;


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

                dateString = "";

                Date dateObject = new Date(timeInMillisecs);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateObject);

                String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
                String month = Integer.toString(calendar.get(Calendar.MONTH + 1));
                String year = Integer.toString(calendar.get(Calendar.YEAR));

                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                int sunday = Calendar.SUNDAY;
                int saturday = Calendar.SATURDAY;


                if (dayOfWeek == sunday || dayOfWeek == saturday) {
                    Toast.makeText(ViewRecordsActivity.this, "It's weekend, you can't register!", Toast.LENGTH_SHORT).show();

                } else {
                    Intent createAttendanceIntent = new Intent(ViewRecordsActivity.this, NewRecordActivity.class);
                    startActivity(createAttendanceIntent);
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

        recordsList.clear();
        loadAllFirebaseEntries();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_records, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_all_sort:
                Toast.makeText(this, "All", Toast.LENGTH_SHORT).show();
                recordsList.clear();

                progressBarCreateAttendance.setVisibility(View.VISIBLE);

                viewRecordsRecyclerAdapter.notifyDataSetChanged();

                loadAllFirebaseEntries();


                break;

            case R.id.action_date_sort:
                recordsList.clear();

                progressBarCreateAttendance.setVisibility(View.VISIBLE);

                viewRecordsRecyclerAdapter.notifyDataSetChanged();

                openPopUp();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /** Helper Methods**/

    public void openPopUp(){
        PopScreen popScreen = new PopScreen();
        popScreen.show(getSupportFragmentManager(), "Test Dialog");




    }

    @Override
    public void applyText(String date, String month, String year) {
        dateString = year + "/" + month + "/" + date;

        Toast.makeText(this, dateString, Toast.LENGTH_SHORT).show();

        sortFirebaseEntries(dateString);
    }

    public void loadAllFirebaseEntries() {

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
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

    public void sortFirebaseEntries(String equalToQuery) {
        progressBarCreateAttendance.setVisibility(View.VISIBLE);
        //Toast.makeText(this, dateString, Toast.LENGTH_SHORT).show();
        attendanceQuery = mDatabaseReference.orderByChild("dateString").equalTo(equalToQuery);

        queryChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(ViewRecordsActivity.this, "Data exists", Toast.LENGTH_SHORT).show();

                    progressBarCreateAttendance.setVisibility(View.GONE);

                    Employee employee = dataSnapshot.getValue(Employee.class);

                    recordsList.add(employee);

                    progressBarCreateAttendance.setVisibility(View.GONE);

                    viewRecordsRecyclerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ViewRecordsActivity.this, "No data found", Toast.LENGTH_SHORT).show();
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

        attendanceQuery.addChildEventListener(queryChildEventListener);
    }


}
