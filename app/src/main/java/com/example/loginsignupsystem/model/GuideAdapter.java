package com.example.loginsignupsystem.model;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignupsystem.R;
import com.example.loginsignupsystem.controller.ConfirmationFragment;

import java.util.List;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.GuideViewHolder> {

    private Context context;
    private List<Guides> guides;
    private String chosenDate;
    private int chosenTicketNumber;
    private double totalPrice;
    private Tour selectedTour;

    public GuideAdapter(Context context, List<Guides> guides, String chosenDate, int chosenTicketNumber, double totalPrice, Tour selectedTour) {
        this.context = context;
        this.guides = guides;
        this.chosenDate = chosenDate;
        this.chosenTicketNumber = chosenTicketNumber;
        this.totalPrice = totalPrice;
        this.selectedTour = selectedTour;
    }

    @NonNull
    @Override
    public GuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guide_item, parent, false);
        return new GuideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideViewHolder holder, int position) {
        Guides guide = guides.get(position);

        holder.image.setImageResource(guide.getImage());
        holder.name.setText(guide.getGuidName());
        holder.language.setText(guide.getLanguage());

        holder.selectButton.setOnClickListener(v -> {
            ConfirmationFragment confirmationFragment = new ConfirmationFragment();
            Bundle args = new Bundle();

            args.putString("chosenDate", chosenDate);
            args.putInt("chosenTicketNumber", chosenTicketNumber);
            args.putDouble("totalPrice", totalPrice);
            args.putSerializable("selectedGuide", guide);
            args.putSerializable("selectedTour", selectedTour);

            confirmationFragment.setArguments(args);

            FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, confirmationFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return guides.size();
    }

    public static class GuideViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView language;
        Button selectButton;

        public GuideViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            language = itemView.findViewById(R.id.language);
            selectButton = itemView.findViewById(R.id.select_button);
        }
    }
}
