package com.khak.daan.ModelAndAdapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khak.daan.Activitys.SearchDetailsActivity;
import com.khak.daan.Config.CustomItemCityListener;
import com.khak.daan.R;
import com.khak.daan.ViewController.Conversions;
import com.khak.daan.ViewController.TextAdjuster;
import java.util.List;

public class SeaechDescriptionAdapter  extends RecyclerView.Adapter<SeaechDescriptionAdapter.ViewHolder> {

    private CustomItemCityListener customItemCityListener;
    Context context;
    private List<GhazalsModel> descriptionList;
    public static List<String> titlesearchitem;
    String type_search = "";
    public SeaechDescriptionAdapter(List<GhazalsModel> descriptionList,List<String> titlesearchitem,String type_search, Context context, CustomItemCityListener customItemCityListener) {
        this.descriptionList = descriptionList;
        this.titlesearchitem = titlesearchitem;
        this.type_search = type_search;
        this.context = context;
        this.customItemCityListener = customItemCityListener;
    }


    @NonNull
    @Override
    public SeaechDescriptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchview_descrption_item, parent, false);
        return new SeaechDescriptionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeaechDescriptionAdapter.ViewHolder holder, int position) {
        String title = titlesearchitem.get(position);


        // Highlight the search string in the text
        if (type_search != null && !type_search.isEmpty()) {
            int startPos = title.toLowerCase().indexOf(type_search.toLowerCase());
            int endPos = startPos + type_search.length();

            if (startPos != -1) {
                Spannable spannable = new SpannableString(title);
                spannable.setSpan(new BackgroundColorSpan(Color.YELLOW), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.text1.setText(spannable);
            } else {
                holder.text1.setText(title);
            }
        } else {


            TextAdjuster.adjustTextWithSpaces(holder.text1, title, 350);
        }


    }

    @Override
    public int getItemCount() {
        return titlesearchitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linear;
        TextView text1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linear = itemView.findViewById(R.id.linear);
            text1 = itemView.findViewById(R.id.text1);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimationSet anima = Conversions.animation();
                    v.startAnimation(anima);

                    Context context = v.getContext();
                    String title_top = descriptionList.get(getAdapterPosition()).getTitle_top();
                    String title = descriptionList.get(getAdapterPosition()).getTitle();
                    String cat = descriptionList.get(getAdapterPosition()).getCat();

                    Intent intent = new Intent(context, SearchDetailsActivity.class);
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(context, R.anim.left_out_right, R.anim.left_in_left);
                    intent.putExtra("title_top", title_top);
                    intent.putExtra("title", title);
                    intent.putExtra("cat", cat);
                    context.startActivity(intent, options.toBundle());
                }
            });

            text1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimationSet anima = Conversions.animation();
                    v.startAnimation(anima);

                    Context context = v.getContext();
                    String title_top = descriptionList.get(getAdapterPosition()).getTitle_top();
                    String title = descriptionList.get(getAdapterPosition()).getTitle();
                    String cat = descriptionList.get(getAdapterPosition()).getCat();

                    Intent intent = new Intent(context, SearchDetailsActivity.class);
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(context, R.anim.left_out_right, R.anim.left_in_left);
                    intent.putExtra("title_top", title_top);
                    intent.putExtra("title", title);
                    intent.putExtra("cat", cat);
                    context.startActivity(intent, options.toBundle());
                }
            });

        }

    }

}