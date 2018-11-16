package com.example.android.logger.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.logger.R;
import com.example.android.logger.models.Employee;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewRecordActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mSubDatabaseReference;


    private TextView attendanceNameText;
    private TextView attendanceDateText;
    private TextView attendanceTimeText;

    final String LOG_TAG = NewRecordActivity.class.getSimpleName();
    public String TIME_TEMPLATE = "HH:mm aa";
    public String DAY_TEMPLATE = "EEE dd MMM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        attendanceNameText = findViewById(R.id.tv_new_attendance_name);
        attendanceDateText = findViewById(R.id.tv_new_attendance_date);
        attendanceTimeText = findViewById(R.id.tv_new_attendance_time);

        long time = System.currentTimeMillis();
        Date dateObject = new Date(time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateObject);

        String attendanceDate = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String attendanceMonth = Integer.toString(calendar.get(Calendar.MONTH));
        String attendanceYear = Integer.toString(calendar.get(Calendar.YEAR));

        String dayString = " " + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.DAY_OF_MONTH);


        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_TEMPLATE);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DAY_TEMPLATE);


        attendanceNameText.setText(dayString);
        attendanceDateText.setText(dateFormat.format(dateObject));
        attendanceTimeText.setText(timeFormat.format(dateObject));

        Employee employee = new Employee("Karen Jane",
                attendanceDate,
                attendanceMonth,
                attendanceYear,
                time);

        mDatabaseReference.child("attendance").push().setValue(employee)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(NewRecordActivity.this, "Write successful!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(NewRecordActivity.this, "Write unsuccessful!", Toast.LENGTH_SHORT).show();

                    }
                });


    }
}
