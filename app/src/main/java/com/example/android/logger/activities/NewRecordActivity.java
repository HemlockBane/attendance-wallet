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

    public void registerAttendance(){
        final Intent viewRecordsIntent = new Intent(NewRecordActivity.this, ViewRecordsActivity.class);

        attendanceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Toast.makeText(NewRecordActivity.this, "You can't register. Wait till tomorrow", Toast.LENGTH_SHORT).show();
                    startActivity(viewRecordsIntent);

                }else{
                    Employee employee = new Employee(ViewRecordsActivity.USER_NAME,
                                    attendanceDate,
                                    attendanceMonth,
                                    attendanceYear,
                                    dateString,
                                    time);

                    mDatabaseReference.push().setValue(employee)
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
