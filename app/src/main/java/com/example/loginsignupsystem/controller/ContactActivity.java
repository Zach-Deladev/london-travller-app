package com.example.loginsignupsystem.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginsignupsystem.R;

public class ContactActivity extends AppCompatActivity {

    EditText editTextname, editTextemail, editTextmes;
    Button button;

    Button pht;

    private final int request_call_code=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        pht=(Button) findViewById(R.id.pht);
        button= findViewById(R.id.btnsend);
        editTextname= findViewById(R.id.name);
        editTextemail=findViewById(R.id.email);
        editTextmes=findViewById(R.id.mes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, email, mes;
                name= editTextname.getText().toString();
                email= editTextemail.getText().toString();
                mes= editTextmes.getText().toString();
                if( name.equals("") && email.equals("") && mes.equals("")){
                    Toast.makeText(ContactActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    sendEmail(name, email, mes);
                }

            }
        });
    }
    public void sendEmail(String name, String email, String mes){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, name);
        intent.putExtra(Intent.EXTRA_TEXT, email);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "choose email content:"));

    }

    public void calltonumber(View view) {

        if(ContextCompat.checkSelfPermission(ContactActivity.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
        {
            String phone=pht.getText().toString();
            Intent intent=new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+ "02089379111"));
            startActivity(intent);
        }
        else
            ActivityCompat.requestPermissions(ContactActivity.this,new String [] {Manifest.permission.CALL_PHONE} ,request_call_code);
    }

}