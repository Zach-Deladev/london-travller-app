package com.example.loginsignupsystem.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.model.Users;
import com.example.loginsignupsystem.model.UsersDao;
import com.example.loginsignupsystem.model.UsersDaoProvider;

import java.util.Random;

public class ConfirmationPageFragment extends Fragment {
    private TextView welcomeTxt, luckyNumberTxt;
    private Button shareBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirmation_page, container, false);

        welcomeTxt = view.findViewById(R.id.textView2);
        luckyNumberTxt = view.findViewById(R.id.lucky_number_txt);
        shareBtn = view.findViewById(R.id.share_number_btn);

        // Retrieve the currently logged-in user's ID
        int userId = getLoggedInUserId();

        // If there is a logged-in user, you can use the userId as needed
        if (userId != -1) {
            // Now you have the user's ID, and you can use it as needed
            // For example, you can pass it to other methods or use it to perform specific actions
            // based on the currently logged-in user.
            // ...
        } else {
            // No user is currently logged in, handle this case if needed
            // ...
        }

        // Random Number Generated
        int randomNumber = generateRandomNumber();
        luckyNumberTxt.setText(String.valueOf(randomNumber));

        // Share button code
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareData(randomNumber);
            }
        });

        return view;
    }

    private int generateRandomNumber() {
        Random random = new Random();
        int upperLimit = 1000000;
        return random.nextInt(upperLimit);
    }

    private void shareData(int randomNumber) {
        // Implicit intents
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        String number = String.valueOf(randomNumber);

        intent.putExtra(Intent.EXTRA_TEXT, "Your reference number is: " + number);

        startActivity(Intent.createChooser(intent, "Choose a platform"));
    }

    // Get the ID of the currently logged-in user
    private int getLoggedInUserId() {
        UsersDao usersDao = UsersDaoProvider.getInstance(getActivity());
        Users loggedInUser = usersDao.getLoggedInUser();
        if (loggedInUser != null) {
            return loggedInUser.getId();
        } else {
            return -1;
        }
    }
}
