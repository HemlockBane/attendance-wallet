package com.example.android.logger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public final String TAG = MainActivity.class.getSimpleName();

    private final String USER_DATABASE_CHILD = "users";
    // Declare view elements
    EditText userNameEdit;
    EditText userPasswordEdit;
    Button signInButton;
    // Declare Firebase variables
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get an instance of FirebaseDatabase, and
        // get a reference to the user child node
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(USER_DATABASE_CHILD);

        // Initialise the view variables
        userNameEdit = findViewById(R.id.et_user_name);
        userPasswordEdit = findViewById(R.id.et_password);
        signInButton = findViewById(R.id.btn_sign_in);

        // Check if user has typed any text
        userPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }
            // When the user is typing, if text is not zero,
            // enable button.

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString().trim().length() > 0) {
                    signInButton.setEnabled(true);
                } else {
                    signInButton.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Add a click listener to the sign in button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display a toast
                Toast.makeText(MainActivity.this, "Button Clicked!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
