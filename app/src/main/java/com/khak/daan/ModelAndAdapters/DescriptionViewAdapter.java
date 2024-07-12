package com.khak.daan.ModelAndAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khak.daan.Config.CustomItemCityListener;
import com.khak.daan.R;
import com.khak.daan.ViewController.TextAdjuster;

import java.util.List;

public class DescriptionViewAdapter extends RecyclerView.Adapter<DescriptionViewAdapter.ViewHolder> {

    Context context;
    public static List<Description> descriptionList;
    private CustomItemCityListener customItemClick;

    //
    public DescriptionViewAdapter(Context context, List<Description> descriptionList, CustomItemCityListener customItemClick) {
        this.context = context;
        this.descriptionList = descriptionList;
        this.customItemClick = customItemClick;
    }


    @NonNull
    @Override
    public DescriptionViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.descriptionview_item, parent, false);
        return new DescriptionViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Description description = descriptionList.get(position);


//        TextAdjuster.adjustTextWithSpaces(holder.textViewDescription, description.getDescription(), 300);
        Log.d("TextAdjuster", "Original Text: " + description.getDescription());
        System.out.println("Original Text:"+description.getDescription());

        TextAdjuster.adjustTextWithSpaces(holder.textViewDescription, description.getDescription(), 300);

        Log.d("TextAdjuster", "Adjusted Text: " + holder.textViewDescription.getText().toString());
        System.out.println("Adjusted Text:"+ holder.textViewDescription.getText().toString());

        holder.textViewDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customItemClick.onItemClick(v, description.getDescription(), "","");
            }
        });

    }

    @Override
    public int getItemCount() {
        return descriptionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDescription = itemView.findViewById(R.id.text1);


        }

    }

}
