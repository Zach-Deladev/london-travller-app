package com.example.loginsignupsystem.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.loginsignupsystem.R;

import java.util.List;

public class TourListAdapter extends ArrayAdapter<Tour> {

    // This is the context of the current state of the application (can be used to get access to resources, etc.)
    private final Context context;

    // This is the constructor of our custom adapter that takes in the context and the list of tours
    public TourListAdapter(Context context, List<Tour> tours) {
        super(context, R.layout.list_item, tours);
        this.context = context;
        // This is the list of tours that we will be displaying
    }

    // This method returns a view for each item within the adapter view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // If convertView is null, it means this view is not yet created.
        // Inflate our list item layout and create a new ViewHolder instance.
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.titleView = (TextView) convertView.findViewById(R.id.title);
            viewHolder.subtitleView = (TextView) convertView.findViewById(R.id.subtitle);
            viewHolder.selectButton = (Button) convertView.findViewById(R.id.select_button);

            // We attach the created ViewHolder to the convertView. This way, we don't have to find views by id again when getView() is called again.
            convertView.setTag(viewHolder);
        } else {
            // If convertView is not null, it means this view was previously created.
            // We retrieve the ViewHolder that was previously attached to take advantage of its references to our views.
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the specific Tour object based on its position within our list of tours
        Tour tour = getItem(position);

        // Set the image, title, and description of the tour on the view
        // Assuming the getImage() function in your Tour model returns the resource ID of your image
        viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(context, tour.getImageResourceId(context)));

        viewHolder.titleView.setText(tour.getTitle());
        viewHolder.subtitleView.setText(tour.getSubTitle());

        // We set an OnClickListener on our button to handle the click event
        viewHolder.selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click event here
            }
        });

        // We return the view that now contains all the necessary information for this list item
        return convertView;
    }

    // A ViewHolder holds the references to the views in our layout. This way, once these views are found
    // and inflated, they can be reused without needing to call findViewById() again, which is expensive.
    static class ViewHolder {
        public TextView subtitleView;
        ImageView imageView;
        TextView titleView;

        Button selectButton;
    }
}