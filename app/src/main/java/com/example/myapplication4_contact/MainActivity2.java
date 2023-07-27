package com.example.myapplication4_contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    EditText editTextname, editTextemail, editTextmes;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
                    Toast.makeText(MainActivity2.this, "All fields are required", Toast.LENGTH_SHORT).show();
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


}