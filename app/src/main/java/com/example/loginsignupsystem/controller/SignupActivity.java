package com.example.loginsignupsystem.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Users;
import com.example.loginsignupsystem.model.UsersDaoProvider;
import com.example.loginsignupsystem.model.UsersDao;

public class SignupActivity extends AppCompatActivity {

    // Defining widget variables
    TextView registerTitle, regErrors;
    Button signupReg, signupLogin;
    EditText signupEmail, signupPassword, signupName, signupPhone;

    UsersDao usersDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Assigning widget variables
        signupName = findViewById(R.id.signupName);
        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        signupPhone = findViewById(R.id.signupPhone);
        signupReg = findViewById(R.id.signupReg);
        signupLogin = findViewById(R.id.signupLogin);
        regErrors = findViewById(R.id.regErrors);

        // Get instance of UsersDao
        usersDao = UsersDaoProvider.getInstance(SignupActivity.this);

        // Onclick function to take the user from registration page to login page if they click login button
        signupLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        signupReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input from form and convert to string
                String strFullname = signupName.getText().toString();
                String strEmail = signupEmail.getText().toString();
                String strPassword = signupPassword.getText().toString();
                String strPhone = signupPhone.getText().toString();

                // Form validation
                if (strFullname.isEmpty() || strEmail.isEmpty() || strPassword.isEmpty() || strPhone.isEmpty()){
                    regErrors.setText("Please fill in all fields.");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                    // Email validation
                    regErrors.setText("Please enter a valid email address.");
                } else if (strPassword.length() < 8) {
                    // Password strength validation
                    regErrors.setText("Password should be at least 8 characters long.");
                } else {

                    // Now call the methods on usersDao object
                    if (usersDao.emailExists(strEmail)) {
                        regErrors.setText("This email address is already registered.");
                        return;
                    }
                    if (usersDao.phoneExists(strPhone)) {
                        regErrors.setText("This phone number is already registered.");
                        return;
                    }

                    // Create Users object
                    Users newUser = new Users(strFullname, strEmail, strPhone, strPassword);


                    // Call method to add user's information to the database
                    long newRowId = usersDao.register(newUser);

                    if (newRowId == -1) {
                        // Show error message
                        regErrors.setText("Registration failed");
                    } else {
                        // Clear all input fields after successful registration
                        signupName.setText("");
                        signupEmail.setText("");
                        signupPassword.setText("");
                        signupPhone.setText("");

                        // Create Intent to start the login activity
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);

                        // Put success message in the intent
                        intent.putExtra("registrationSuccessMessage", "Registered successfully, please log in!");

                        // Start the login activity
                        startActivity(intent);

                        // Optionally, finish the registration activity so the user can't go back to it with the back button
                        finish();
                    }

                }
            }
        });

    }
}
