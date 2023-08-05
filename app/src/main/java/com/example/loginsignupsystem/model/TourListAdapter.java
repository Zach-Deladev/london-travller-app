package com.example.loginsignupsystem.model;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.controller.TourInfoFragment;

import java.util.List;

// This is a custom ArrayAdapter for the Tour objects. ArrayAdapter is a type of Adapter that works with a List of data.
public class TourListAdapter extends ArrayAdapter<Tour> {

    static class view{
        ImageView imageView;
        TextView textView;
        TextView subtitle;
        Button btn;
    }

    // Context of the application.
    private final Context context;
    // Constructor of our TourListAdapter that takes the current context and a list of tours.
    public TourListAdapter(Context context, List<Tour> tours) {
        super(context, R.layout.list_item, tours);
        this.context = context;
    }

    // Method for generating each item in the list.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view viewhold;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            viewhold = new view();
            viewhold.imageView = convertView.findViewById(R.id.image);
            viewhold.textView = convertView.findViewById(androidx.core.R.id.title);
            viewhold.subtitle = convertView.findViewById(R.id.subtitle);
            viewhold.btn = convertView.findViewById(R.id.select_button);

        } else {
            viewhold = (view) convertView.getTag();
        }
        Tour tour = getItem(position);
        viewhold.imageView.setImageDrawable(ContextCompat.getDrawable(context, tour.getImageResourceId(context)));
        viewhold.textView.setText(tour.getTitle());
        viewhold.subtitle.setText(tour.getSubTitle());

        viewhold.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TourInfoFragment tourInfoFragment = new TourInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedTour", tour);
                tourInfoFragment.setArguments(bundle);
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.commit();
            }

        });
        return convertView;
    }
}