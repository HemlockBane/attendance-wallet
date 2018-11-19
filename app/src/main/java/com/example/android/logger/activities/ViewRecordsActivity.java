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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.logger.R;
import com.example.android.logger.adapters.ViewRecordsRecyclerAdapter;
import com.example.android.logger.models.Employee;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class ViewRecordsActivity extends AppCompatActivity implements PopScreen.PopScreenListener {

    public static final int RC_SIGN_IN = 1;
    private static final String LOG_TAG = ViewRecordsActivity.class.getSimpleName();

    public ArrayList<Employee> recordsList;
    ViewRecordsRecyclerAdapter viewRecordsRecyclerAdapter;

    private FloatingActionButton fabCreateAttendance;
    private ProgressBar progressBarCreateAttendance;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private ChildEventListener mChildEventListener;
    private ChildEventListener queryChildEventListener;

    private ValueEventListener queryValueListener;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private Query attendanceQuery;

    private String dateString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_list);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("attendance");
        mFirebaseAuth = FirebaseAuth.getInstance();

        checkLoginStatus();


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed In!", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in cancelled!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //When user interacts the UI
    @Override
    protected void onResume() {
        super.onResume();
        // Listen for sign in status
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    // When user stops interacting with the UI
    @Override
    protected void onPause() {
        super.onPause();
        //
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource
        getMenuInflater().inflate(R.menu.menu_view_records, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Determine which menu item is clicked
        switch (id) {
            // All is clicked
            case R.id.action_all_sort:
                Toast.makeText(this, "All", Toast.LENGTH_SHORT).show();
                // Clear the ArrayList
                recordsList.clear();

                // Display the loading indicator
                progressBarCreateAttendance.setVisibility(View.VISIBLE);

                // Update the RecyclerAdapter
                viewRecordsRecyclerAdapter.notifyDataSetChanged();

                // Update the UI
                loadAllFirebaseEntries();
                return true;

            // Sort by date is clicked
            case R.id.action_date_sort:
                recordsList.clear();

                progressBarCreateAttendance.setVisibility(View.VISIBLE);

                viewRecordsRecyclerAdapter.notifyDataSetChanged();

                openPopUp();
                return true;

            case R.id.action_sign_out:
                AuthUI.getInstance().signOut(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Helper Methods

    public void checkLoginStatus() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                // If user is signed in
                if (user != null) {
                    //Display toast
                    Toast.makeText(ViewRecordsActivity.this, "Welcome, " + user.getDisplayName() + "!", Toast.LENGTH_SHORT).show();
                } else {
                    // Start login flow
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    //Start the pop up screen
    public void openPopUp() {
        PopScreen popScreen = new PopScreen();
        popScreen.show(getSupportFragmentManager(), "Test Dialog");


    }

    // Get details from the pop up screen
    @Override
    public void applyText(String date, String month, String year) {
        dateString = year + "/" + month + "/" + date;

        Toast.makeText(this, dateString, Toast.LENGTH_SHORT).show();

        sortFirebaseEntries(dateString);
    }

    // Load all attendance entries
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

        attendanceQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Toast.makeText(ViewRecordsActivity.this, "Exists", Toast.LENGTH_SHORT).show();
                    progressBarCreateAttendance.setVisibility(View.GONE);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Employee employee = dataSnapshot.getValue(Employee.class);

                    recordsList.add(employee);

                    progressBarCreateAttendance.setVisibility(View.GONE);

                    viewRecordsRecyclerAdapter.notifyDataSetChanged();
                    }

                }else{
                    Toast.makeText(ViewRecordsActivity.this, "Doesn't exist", Toast.LENGTH_SHORT).show();
                    progressBarCreateAttendance.setVisibility(View.GONE);
                    Toast.makeText(ViewRecordsActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
