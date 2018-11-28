package com.example.android.logger.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.logger.R;
import com.example.android.logger.models.Employee;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewRecordActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mSubDatabaseReference;
    private Query attendanceQuery;
    private ChildEventListener queryChildEventListener;
    private ValueEventListener queryValueEventListener;


    private TextView attendanceNameText;
    private TextView attendanceDateText;
    private TextView attendanceTimeText;
    private FloatingActionButton fabPostAttenance;

    final String LOG_TAG = NewRecordActivity.class.getSimpleName();
    public String TIME_TEMPLATE = "HH:mm aa";
    public String DAY_TEMPLATE = "EEE dd MMM";

    private Intent viewRecordsIntent;


    String attendanceDate;
    String attendanceMonth;
    String attendanceYear;
    String dateString;
    long time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("attendance");

        attendanceNameText = findViewById(R.id.tv_new_attendance_name);
        attendanceDateText = findViewById(R.id.tv_new_attendance_date);
        attendanceTimeText = findViewById(R.id.tv_new_attendance_time);
        fabPostAttenance = findViewById(R.id.fab_post_new_attendance);

        viewRecordsIntent = new Intent(NewRecordActivity.this, ViewRecordsActivity.class);

        time = System.currentTimeMillis();
        Date dateObject = new Date(time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateObject);

        attendanceDate = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        attendanceMonth = Integer.toString(calendar.get(Calendar.MONTH) + 1); // Month is zero-indexed
        attendanceYear = Integer.toString(calendar.get(Calendar.YEAR));
        dateString = "" + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);


        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_TEMPLATE);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DAY_TEMPLATE);


        attendanceNameText.setText(ViewRecordsActivity.USER_NAME);
        attendanceDateText.setText(dateFormat.format(dateObject));
        attendanceTimeText.setText(timeFormat.format(dateObject));


        fabPostAttenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewRecordActivity.this, dateString, Toast.LENGTH_SHORT).show();

                attendanceQuery = mDatabaseReference.orderByChild("dateString").equalTo(dateString);

                registerAttendance();
            }
        });
    }

    public void registerAttendance() {

        attendanceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // If people have already registered for the day
                if (dataSnapshot.exists()) {
                    //Toast.makeText(NewRecordActivity.this, "Data", Toast.LENGTH_SHORT).show();

                    ArrayList<Employee> employeeQueryList = new ArrayList<>();
                    // Make a copy of the attendance register
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Employee testEmployee = snapshot.getValue(Employee.class);
                        employeeQueryList.add(testEmployee);

                    }

                    boolean disqualified = true;

                    // Check all the names in the register and see if the
                    // user has registered for the day
                    for (Employee employee : employeeQueryList) {
                        //Toast.makeText(NewRecordActivity.this, "Data Loop: " + employee.getEmployeeName(), Toast.LENGTH_SHORT).show();

                        // If user has registered for the day,
                        if ((employee.getEmployeeName()).equals(ViewRecordsActivity.USER_NAME)) {
                            // User is disqualified
                            disqualified = true;
                            break;
                        } else {
                            // User is qualified
                            disqualified = false;
                        }
                    }

                    // If the user hasn't registered for the day
                    if (!disqualified) {
                        // Register
                        sendDataToServer();
                    } else {
                        // Don't register
                        Toast.makeText(NewRecordActivity.this, "You can't register again for today. Wait till tomorrow", Toast.LENGTH_SHORT).show();
                        startActivity(viewRecordsIntent);
                    }

                } else {
                    // If no one has registered for the day
                    Toast.makeText(NewRecordActivity.this, "Well done, early bird!", Toast.LENGTH_SHORT).show();
                    // Register
                    sendDataToServer();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void sendDataToServer() {
        Employee newEmployee = new Employee(ViewRecordsActivity.USER_NAME,
                attendanceDate,
                attendanceMonth,
                attendanceYear,
                dateString,
                time);

        mDatabaseReference.push().setValue(newEmployee)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(NewRecordActivity.this, "Write successful!", Toast.LENGTH_SHORT)
                                .show();
                        startActivity(viewRecordsIntent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(NewRecordActivity.this, "Write unsuccessful!", Toast.LENGTH_SHORT)
                                .show();
                        startActivity(viewRecordsIntent);

                    }
                });
    }
}
