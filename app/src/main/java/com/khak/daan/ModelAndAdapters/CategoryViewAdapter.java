package com.khak.daan.ModelAndAdapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.khak.daan.Activitys.GhazalsActivity;
import com.khak.daan.R;

import java.util.List;

public class CategoryViewAdapter extends RecyclerView.Adapter<CategoryViewAdapter.ViewHolder> {

    public static List<PoetryCategory> categoryList;

    public CategoryViewAdapter(List<PoetryCategory> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PoetryCategory category = categoryList.get(position);
        holder.textViewCategory.setText(category.getCategory());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCategory;
        CardView card_1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            String cat ="";
            textViewCategory = itemView.findViewById(R.id.text1);
            card_1 = itemView.findViewById(R.id.card_1);
            card_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    String category = categoryList.get(getAdapterPosition()).getCategory();
                    Intent intent = new Intent(context, GhazalsActivity.class);
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(context, R.anim.left_out_right, R.anim.left_in_left);
                    intent.putExtra("cat",category);
                    context.startActivity(intent, options.toBundle());
                }
            });
        }

    }

}