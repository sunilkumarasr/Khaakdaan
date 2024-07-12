package com.khak.daan.ModelAndAdapters.LikesAdapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.khak.daan.Activitys.LikedDetailsActivity;
import com.khak.daan.Config.CustomItemCityListener;
import com.khak.daan.ModelAndAdapters.Title;
import com.khak.daan.R;
import com.khak.daan.ViewController.Conversions;
import com.khak.daan.ViewController.TextAdjuster;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class LikedlistAdapter extends RecyclerView.Adapter<LikedlistAdapter.ViewHolder> {

    private CustomItemCityListener customItemCityListener;

    Context context;

    public static List<Title> titleList;
    private LinkedHashMap<String, LinkedList<Title>> mapOfCategoryNamesAndTitles;

    public LikedlistAdapter(List<Title> titleList, LinkedHashMap<String, LinkedList<Title>> mapOfCategoryNamesAndTitles, Context context, CustomItemCityListener customItemCityListener) {
        this.titleList = titleList;
        this.context = context;
        this.mapOfCategoryNamesAndTitles = mapOfCategoryNamesAndTitles;
        this.customItemCityListener = customItemCityListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liked_title_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Title title = titleList.get(position);
        Set<String> listOfKeys = mapOfCategoryNamesAndTitles.keySet();
        LinkedList<String> listOfCategories = new LinkedList<>(listOfKeys);
        String key = listOfCategories.get(position);
        LinkedList<Title> listOfTitles = mapOfCategoryNamesAndTitles.get(key);
        holder.tvCategoryName.setText(key);
        prepareItemDetails(holder, listOfTitles, position);

    }

    private void prepareItemDetails(ViewHolder holder, LinkedList<Title> listOfTitles, int position) {
        for (int index = 0; index < listOfTitles.size(); index++) {
            Title title = listOfTitles.get(index);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            View view = inflater.inflate(R.layout.liked_child_items, null);
            CardView card_1 = view.findViewById(R.id.card_1);
            TextView txt_dec = view.findViewById(R.id.text1);
            ImageView like_remove = view.findViewById(R.id.like_remove);

            if (title.getTitle_top().equalsIgnoreCase("")){
                TextAdjuster.adjustTextWithSpaces(txt_dec, title.getTitle(), 250);
//                txt_dec.setText(title.getTitle());
            }else {
                txt_dec.setText(title.getTitle_top());
            }

            card_1.setTag(position + "&&##" + index);
            like_remove.setTag(position + "&&##" + index);
            card_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pos = (String) v.getTag();
                    String[] positionIndex = pos.split("&&##");

                    int position = 0;
                    int index = 0;
                    if (positionIndex.length == 2) {
                        position = Integer.parseInt(positionIndex[0]);
                        index = Integer.parseInt(positionIndex[1]);
                    }

                    Set<String> listOfKeys = mapOfCategoryNamesAndTitles.keySet();
                    LinkedList<String> listOfCategories = new LinkedList<>(listOfKeys);
                    String key = listOfCategories.get(position);
                    LinkedList<Title> listOfTitles = mapOfCategoryNamesAndTitles.get(key);

                    AnimationSet anima = Conversions.animation();
                    v.startAnimation(anima);

                    String Title_top = listOfTitles.get(index).getTitle_top();
                    String cat = listOfTitles.get(index).getCat();
                    String title = listOfTitles.get(index).getTitle();
                    boolean active = listOfTitles.get(index).isActive();
                    Intent intent = new Intent(context, LikedDetailsActivity.class);
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(context, R.anim.left_out_right, R.anim.left_in_left);
                    intent.putExtra("Title_top", Title_top);
                    intent.putExtra("title", title);
                    intent.putExtra("cat", cat);
                    intent.putExtra("active", active);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent, options.toBundle());

                }
            });

            like_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pos = (String) v.getTag();
                    String[] positionIndex = pos.split("&&##");

                    int position = 0;
                    int index = 0;
                    if (positionIndex.length == 2) {
                        position = Integer.parseInt(positionIndex[0]);
                        index = Integer.parseInt(positionIndex[1]);
                    }

                    Set<String> listOfKeys = mapOfCategoryNamesAndTitles.keySet();
                    LinkedList<String> listOfCategories = new LinkedList<>(listOfKeys);
                    String key = listOfCategories.get(position);
                    LinkedList<Title> listOfTitles = mapOfCategoryNamesAndTitles.get(key);

                    //delete item
                    AnimationSet anima = Conversions.animation();
                    v.startAnimation(anima);
                    String Title_top = listOfTitles.get(index).getTitle_top();
                    String category = listOfTitles.get(index).getCat();
                    String title = listOfTitles.get(index).getTitle();
                    customItemCityListener.onItemClick(v, Title_top, category, title);

                }
            });
            holder.llContainer.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return mapOfCategoryNamesAndTitles.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCategoryName;
        private LinearLayout llContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            llContainer = itemView.findViewById(R.id.llContainer);

        }

    }

    public void updateList(LinkedHashMap<String, LinkedList<Title>> mapOfTempCategoryNamesAndTitles) {
        mapOfCategoryNamesAndTitles = mapOfTempCategoryNamesAndTitles;
        notifyDataSetChanged();
    }


}
