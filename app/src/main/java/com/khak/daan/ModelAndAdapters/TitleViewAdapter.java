package com.khak.daan.ModelAndAdapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.khak.daan.Config.CommonData;
import com.khak.daan.ViewController.Conversions;
import com.khak.daan.Config.CustomItemCityListener;
import com.khak.daan.Activitys.LastActivity;
import com.khak.daan.Config.MyDatabaseHelper;
import com.khak.daan.R;
import com.khak.daan.ViewController.TextAdjuster;

import java.util.List;

public class TitleViewAdapter extends RecyclerView.Adapter<TitleViewAdapter.ViewHolder> {

    private CustomItemCityListener customItemCityListener;
    Context context;

    public static List<GhazalsModel> titleList;
    public TitleViewAdapter(List<GhazalsModel> titleList, Context context, CustomItemCityListener customItemCityListener) {
        this.titleList = titleList;
        this.context = context;
        this.customItemCityListener = customItemCityListener;
    }

    @NonNull
    @Override
    public TitleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.titleview_item, parent, false);
        return new TitleViewAdapter.ViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull TitleViewAdapter.ViewHolder holder, int position) {
//        GhazalsModel title = titleList.get(position);
//        if (title.isActive()) {
//            holder.like_remove.setVisibility(View.VISIBLE);
//        }
//
//        if (titleList.get(position).getTitle_top().equalsIgnoreCase("")){
////            holder.textViewTitle.setText(title.getTitle());
//            TextAdjuster.adjustTextWithSpaces(holder.textViewTitle, title.getTitle(), 250);
//        }else {
//            holder.textViewTitle.setText(title.getTitle_top());
//        }
//
//        //TextAdjuster.adjustTextWithSpaces(holder.textViewTitle, title.getTitle_top(), 250);
////        holder.textViewTitle.setText(title.getTitle());
//
//    }

//    numbering set to titles
@Override
public void onBindViewHolder(@NonNull TitleViewAdapter.ViewHolder holder, int position) {
    GhazalsModel title = titleList.get(position);
    if (title.isActive()) {
        holder.like_remove.setVisibility(View.VISIBLE);
    }

    String numberedTitle = (position + 1) + ". "; // Position starts from 0, so add 1
    if (titleList.get(position).getTitle_top().equalsIgnoreCase("")) {
        numberedTitle += title.getTitle();
        TextAdjuster.adjustTextWithSpaces(holder.textViewTitle, numberedTitle, 250);
    } else {
        numberedTitle += title.getTitle_top();
        holder.textViewTitle.setText(numberedTitle);
    }
}


    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        ImageView like_remove;
        CardView card_1;
        MyDatabaseHelper myDb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text1);
            like_remove = itemView.findViewById(R.id.like_remove);
            card_1 = itemView.findViewById(R.id.card_1);



            card_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimationSet anima = Conversions.animation();
                    v.startAnimation(anima);

                    Context context = v.getContext();
                    String title_top = titleList.get(getAdapterPosition()).getTitle_top();
                    String cat = titleList.get(getAdapterPosition()).getCat();
                    String title = titleList.get(getAdapterPosition()).getTitle();
                    boolean active = titleList.get(getAdapterPosition()).isActive();

                    CommonData.getInstance().setTitleList(titleList);
                    Intent intent = new Intent(context, LastActivity.class);
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(context, R.anim.left_out_right, R.anim.left_in_left);
                    intent.putExtra("title_top", title_top);
                    intent.putExtra("title", title);
                    intent.putExtra("cat", cat);
                    intent.putExtra("active", active);
                    intent.putExtra(context.getString(R.string.total_count), titleList.size());
                    intent.putExtra(context.getString(R.string.position),getAdapterPosition());
                    context.startActivity(intent, options.toBundle());

                }
            });

            like_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimationSet anima = Conversions.animation();
                    v.startAnimation(anima);

                    Context context = v.getContext();
                    myDb = new MyDatabaseHelper(context);
                    String Title_top = titleList.get(getAdapterPosition()).getTitle_top();
                    String category = titleList.get(getAdapterPosition()).getCat();
                    String title = titleList.get(getAdapterPosition()).getTitle();
                    myDb.deleteLikedData(Title_top,category,title);
                    customItemCityListener.onItemClick(v,"","","");

//                    Intent intent = new Intent(context, LikedlistActivity.class);
//                    ActivityOptions optionsfosp = ActivityOptions.makeCustomAnimation(context, R.anim.right_to_left, R.anim.right_to_left);
//
//                    context.startActivity(intent, optionsfosp.toBundle());
                }
            });

        }

    }

}