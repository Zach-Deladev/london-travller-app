package com.example.loginsignupsystem.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.loginsignupsystem.R;

public class ContactFragment extends Fragment {

    EditText editTextname, editTextemail, editTextmes;
    Button button;
    Button pht;

    private final int request_call_code = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        pht = view.findViewById(R.id.pht);
        button = view.findViewById(R.id.btnsend);
        editTextname = view.findViewById(R.id.name);
        editTextemail = view.findViewById(R.id.email);
        editTextmes = view.findViewById(R.id.mes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, email, mes;
                name = editTextname.getText().toString();
                email = editTextemail.getText().toString();
                mes = editTextmes.getText().toString();
                if (name.equals("") && email.equals("") && mes.equals("")) {
                    Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    sendEmail(name, email, mes);
                }
            }
        });

        return view;
    }

    public void sendEmail(String name, String email, String mes) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, name);
        intent.putExtra(Intent.EXTRA_TEXT, email);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "choose email content:"));
    }

    public void calltonumber(View view) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            String phone = pht.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + "02089379111"));
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, request_call_code);
        }
    }
}
