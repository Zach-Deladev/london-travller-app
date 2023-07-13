package com.example.loginsignupsystem.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.DbHelper;
import com.example.loginsignupsystem.model.Users;
import com.example.loginsignupsystem.model.UsersDao;
import com.example.loginsignupsystem.model.UsersDaoProvider;



public class LoginActivity extends AppCompatActivity {

    // Defining widget variables
    TextView textView;
    EditText emailAddressLogin, userPassword;
    Button btnLogin, btnRegisterLog;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Assigning widget variables
        emailAddressLogin = findViewById(R.id.emailAddressLogin);
        userPassword =  findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegisterLog = findViewById(R.id.btnRegisterLog);
        TextView successReg = findViewById(R.id.successReg);
        TextView loginErrors = findViewById(R.id.loginErrors);

        dbHelper = new DbHelper(LoginActivity.this);
        UsersDao usersDao = UsersDaoProvider.getInstance(LoginActivity.this);

        Intent intent = getIntent();

        String successMessage = intent.getStringExtra("registrationSuccessMessage");

        // If there was a success message, display it
        if (successMessage != null) {
            successReg.setText(successMessage);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailAddressLogin.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                int userId = usersDao.checkUserCredentials(email, password);
                if (userId == -1) {
                    loginErrors.setText("Invalid email or password.");
                } else {
                    usersDao.logInUser(userId);
                    Users user = usersDao.getUserById(userId);  // Here, we use the new method
                    String fullname = user.getFullname();

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("username", fullname);
                    startActivity(i);
                }
            }
        });

        // Register button onClick function to navigate user from login page to register page
        btnRegisterLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit intent
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
    }
}