package com.example.android.logger.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.logger.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public final String TAG = MainActivity.class.getSimpleName();

    private final String USER_DATABASE_CHILD = "users";
    public static final int RC_SIGN_IN = 1;

    // Declare view elements
    EditText userNameEdit;
    EditText userPasswordEdit;
    Button signInButton;

    // Declare Firebase variables
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get an instance of FirebaseDatabase, and
        // get a reference to the user child node
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(USER_DATABASE_CHILD);
        mFirebaseAuth = FirebaseAuth.getInstance();

//        // Initialise the view variables
//        userNameEdit = findViewById(R.id.et_user_name);
//        userPasswordEdit = findViewById(R.id.et_password);
//        signInButton = findViewById(R.id.btn_sign_in);
//
//        // Check if user has typed any text
//        userPasswordEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
//
//            }
//            // When the user is typing, if text is not zero,
//            // enable button.
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//                if (charSequence.toString().trim().length() > 0) {
//                    signInButton.setEnabled(true);
//                } else {
//                    signInButton.setEnabled(false);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        // Add a click listener to the sign in button
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Display a toast
//                Toast.makeText(MainActivity.this, "Button Clicked!", Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, "Time is " + System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
//
//
//            }
//        });

//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null){
//                    Toast.makeText(MainActivity.this, "Welcome, " + user.getDisplayName() + "!", Toast.LENGTH_SHORT).show();
//                    goToRecords();
//
//
//                }else{
//                    startActivityForResult(
//                            AuthUI.getInstance()
//                                    .createSignInIntentBuilder()
//                                    .setAvailableProviders(Arrays.asList(
//                                            new AuthUI.IdpConfig.EmailBuilder().build()))
//                                    .build(),
//                            RC_SIGN_IN);
//                }
//            }
//        };

    }

    public void goToRecords(){
        Intent recordsIntent = new Intent(MainActivity.this, ViewRecordsActivity.class);
        startActivity(recordsIntent);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN){
//            if (resultCode == RESULT_OK){
//                Toast.makeText(this, "Signed In!", Toast.LENGTH_SHORT).show();
//
//                goToRecords();
//
//            } else if (resultCode == RESULT_CANCELED){
//                Toast.makeText(this, "Sign in cancelled!", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
//    }

    /* HELPER METHODS */

    /** This function checks to see if the the typed password is empty.
     *
     * @param editText The text to be validated
     * @return true or false
     */
    private boolean isValidPassword(EditText editText){
        CharSequence passwordString = editText.getText().toString();
        return (!TextUtils.isEmpty(passwordString));
    }

    /** This function displays an error message when the password is invalid.
     *
     * @param editText The text to be validated
     * @return true or false
     */

    private boolean isValidatedPassword(EditText editText){
        if (!isValidPassword(editText)){
            editText.setError("Please, enter a valid password");
            return false;
        }else{
            return true;
        }
    }
}
