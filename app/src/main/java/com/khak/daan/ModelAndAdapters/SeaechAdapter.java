package com.khak.daan.ModelAndAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khak.daan.Config.CustomItemCityListener;
import com.khak.daan.R;
import com.khak.daan.ViewController.TextAdjuster;

import java.util.ArrayList;
import java.util.List;

public class SeaechAdapter  extends RecyclerView.Adapter<SeaechAdapter.ViewHolder> {

    private CustomItemCityListener customItemCityListener;
    Context context;

    String type_search = "";

    public static List<GhazalsModel> titleList;
    public static List<String> S_List;

    public SeaechAdapter(List<GhazalsModel> titleList,String type_search, Context context, CustomItemCityListener customItemCityListener) {
        this.titleList = titleList;
        this.type_search = type_search;
        this.context = context;
        this.customItemCityListener = customItemCityListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GhazalsModel title = titleList.get(position);


        S_List = new ArrayList<>();
        for (int j = 0; j < title.getDes().size(); j++) {
            S_List.add(titleList.get(position).getDes().get(j));
        }

        // Implement the logic to copy all the text here
        StringBuilder textToCopy = new StringBuilder();

        for (String description : S_List) {
            textToCopy.append(description).append("\n");
        }

        TextAdjuster.adjustTextWithSpaces(holder.text1, textToCopy.toString(), 300);


        if (title.getTitle_top().equalsIgnoreCase("")){
            holder.img_logo.setVisibility(View.VISIBLE);
            holder.txt_title.setVisibility(View.GONE);
            holder.txt_title.setText(title.getTitle());
        }else {
            holder.img_logo.setVisibility(View.GONE);
            holder.txt_title.setVisibility(View.VISIBLE);
            holder.txt_title.setText(title.getTitle_top());
        }

        holder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customItemCityListener.onItemClick(v, textToCopy.toString(), "","");
            }
        });



    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linear;
        ImageView img_logo;
        TextView txt_title;
        TextView text1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            linear = itemView.findViewById(R.id.linear);
            img_logo = itemView.findViewById(R.id.img_logo);
            txt_title = itemView.findViewById(R.id.txt_title);
            text1 = itemView.findViewById(R.id.text1);

        }

    }

}