package com.khak.daan.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.khak.daan.Config.CommonClass;
import com.khak.daan.ViewController.Conversions;
import com.khak.daan.Config.CustomItemCityListener;
import com.khak.daan.Config.MyDatabaseHelper;
import com.khak.daan.ModelAndAdapters.LikesAdapter.LikedChildArrayModel;
import com.khak.daan.ModelAndAdapters.LikesAdapter.LikedChildModel;
import com.khak.daan.ModelAndAdapters.LikesAdapter.LikedlistAdapter;
import com.khak.daan.ModelAndAdapters.Title;
import com.khak.daan.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class LikedlistActivity extends AppCompatActivity implements View.OnClickListener{

    private CommonClass cc;

    ImageView img_back, img_home,img_search,img_menu;
    Cursor cursor;
    MyDatabaseHelper myDb;
    SearchView searchView;
    private RecyclerView titleView;

    TextView txt_no_data;

    private List<Title> titleList;
    private LinkedHashMap<String, LinkedList<Title>> mapOfCategoryNamesAndTitles;
    private List<LikedChildModel> childList;
    private List<LikedChildArrayModel> childarrayList;
    private List<LikedChildArrayModel> childarrayList2;

    private List<Title> filteredList;


    private LikedlistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likedlist);

        cc = new CommonClass(LikedlistActivity.this);


        img_back = findViewById(R.id.img_back);
        img_home = findViewById(R.id.img_home);
        img_search = findViewById(R.id.img_search);
        img_menu = findViewById(R.id.img_menu);

        titleView = findViewById(R.id.titleView);
        searchView = findViewById(R.id.searchview);
        searchView.setGravity(Gravity.END);
        searchView.setPadding(0, 30, 0, 0);
        txt_no_data = findViewById(R.id.txt_no_data);

        myDb = new MyDatabaseHelper(this);

        myDb = new MyDatabaseHelper(this);

        filteredList = new ArrayList<>();
        filteredList = new ArrayList<>(filteredList);

        titleView.setLayoutManager(new LinearLayoutManager(this));
        titleList = new ArrayList<>();
        childList = new ArrayList<>();
        childarrayList = new ArrayList<>();
        childarrayList2 = new ArrayList<>();
        titleList = new ArrayList<>(titleList);
        mapOfCategoryNamesAndTitles = new LinkedHashMap<>();
        cursor = myDb.getLikedData();
        if (cursor != null && cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {
                String title_top = cursor.getString(1);
                String cat = cursor.getString(2);
                String title = cursor.getString(3);
                titleList.add(new Title(title_top,title, cat, true));
                filteredList.add(new Title(title_top,title, cat, false));

                if (mapOfCategoryNamesAndTitles.containsKey(cat)) {
                    LinkedList<Title> listOfTitles = mapOfCategoryNamesAndTitles.get(cat);
                    if (listOfTitles == null) {
                        listOfTitles = new LinkedList<>();
                    }
                    listOfTitles.add(new Title(title_top,title, cat, true));
                    mapOfCategoryNamesAndTitles.put(cat, listOfTitles);
                } else {
                    LinkedList<Title> listOfTitles = new LinkedList<>();
                    listOfTitles.add(new Title(title_top,title, cat, true));
                    mapOfCategoryNamesAndTitles.put(cat, listOfTitles);
                }
                // Do something with the retrieved data
                System.out.println("Category: " + cat + ", Title: " + title);
                // Move the cursor to the next row
                cursor.moveToNext();


                for (int i = 0; i < titleList.size(); i++) {
                    childarrayList.clear();
                    childList.clear();
                    if (cat.equalsIgnoreCase(titleList.get(i).getCat())) {
                        childarrayList.add(new LikedChildArrayModel(title, cat, true));
                    } else {

                    }
                }

                childarrayList2.addAll(childarrayList);

                childList.add(new LikedChildModel(title, cat, true, childarrayList2));


            }
        }

        if (mapOfCategoryNamesAndTitles.size()>0){

        }else {
            txt_no_data.setVisibility(View.VISIBLE);
        }

        adapter = new LikedlistAdapter(titleList, mapOfCategoryNamesAndTitles, getApplicationContext(), new CustomItemCityListener() {
            @Override
            public void onItemClick(View v, String d_Title_top, String d_category, String d_title) {

                delete_item(d_Title_top,d_category,d_title);

            }
        });
        titleView.setAdapter(adapter);
        cc.AnimationLeft(titleView);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                filterTitles(query);
                return true;
            }
        });


        img_back.setOnClickListener(this);
        img_home.setOnClickListener(this);
        img_menu.setOnClickListener(this);
        img_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                AnimationSet anima = Conversions.animation();
                v.startAnimation(anima);

                Intent intent1 = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.left_out_right, R.anim.left_in_left);
                finish();
                break;
            case R.id.img_home:
                AnimationSet animas = Conversions.animation();
                v.startAnimation(animas);

                Intent inten = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(inten);
                overridePendingTransition(R.anim.left_in_left, R.anim.left_out_right);
                finish();
                break;
            case R.id.img_menu:
                AnimationSet animau = Conversions.animation();
                v.startAnimation(animau);

                showPopupMenu(v);
                break;
            case R.id.img_search:
                AnimationSet anim = Conversions.animation();
                v.startAnimation(anim);

                Intent intents = new Intent(getApplicationContext(), SearchActivity.class);
                ActivityOptions optionss = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
                startActivity(intents, optionss.toBundle());
                break;
        }
    }


    // Set the custom font for each menu item
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(LikedlistActivity.this, view);
        popupMenu.inflate(R.menu.menu_main);

        // Load the custom font
        Typeface customFont = ResourcesCompat.getFont(LikedlistActivity.this, R.font.mehrnastailqwebregular);

        // Set the custom font for each menu item
        Menu menu = popupMenu.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            applyCustomFontToMenuItem(menuItem, customFont);
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_item_1:
                    case R.id.menu_item_2:
                    case R.id.menu_item_3:
                    case R.id.menu_item_4:
                    case R.id.menu_item_5:
                        list_Activity(menuItem);
                        return true;
                    case R.id.menu_item_6:
                        ghail_Activity();
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    private void applyCustomFontToMenuItem(MenuItem menuItem, Typeface customFont) {
        SpannableString spannableString = new SpannableString(menuItem.getTitle());
        spannableString.setSpan(new CustomTypefaceSpan("", customFont), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        menuItem.setTitle(spannableString);
    }

    private void list_Activity(MenuItem menu) {
        String category = String.valueOf(menu.getTitle());
        Intent intent = new Intent(getApplicationContext(), GhazalsActivity.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
        intent.putExtra("cat",category);
        startActivity(intent, options.toBundle());
    }

    private void ghail_Activity() {
        Intent intent2 = new Intent(getApplicationContext(), StaticCardActivity.class);
        ActivityOptions options2 = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
        startActivity(intent2, options2.toBundle());
    }


    private void like_item_Delete(String d_Title_top,String d_category,String d_title) {

        //delete item
        myDb.deleteLikedData(d_Title_top,d_category, d_title);

        titleList = new ArrayList<>();
        titleList.clear();
        titleList = new ArrayList<>(titleList);
        mapOfCategoryNamesAndTitles = new LinkedHashMap<>();
        cursor = myDb.getLikedData();
        if (cursor != null && cursor.moveToFirst()) {
            // Iterate over the cursor until it reaches the end
            while (!cursor.isAfterLast()) {
                // Retrieve data from the cursor for each row
                String title_top = cursor.getString(1);
                String cat = cursor.getString(2);
                String title = cursor.getString(3);
                titleList.add(new Title(title_top,title, cat, true));
                filteredList.add(new Title(title_top,title, cat, false));
                // Do something with the retrieved data
                if (mapOfCategoryNamesAndTitles.containsKey(cat)) {
                    LinkedList<Title> listOfTitles = mapOfCategoryNamesAndTitles.get(cat);
                    if (listOfTitles == null) {
                        listOfTitles = new LinkedList<>();
                    }
                    listOfTitles.add(new Title(title_top,title, cat, true));
                    mapOfCategoryNamesAndTitles.put(cat, listOfTitles);
                } else {
                    LinkedList<Title> listOfTitles = new LinkedList<>();
                    listOfTitles.add(new Title(title_top,title, cat, true));
                    mapOfCategoryNamesAndTitles.put(cat, listOfTitles);
                }

                System.out.println("Category: " + cat + ", Title: " + title);
                // Move the cursor to the next row
                cursor.moveToNext();
            }
        }
        Intent intent = new Intent(getApplicationContext(), LikedlistActivity.class);
        startActivity(intent);

    }

    private void filterTitles(String query) {
        titleList.clear();
        LinkedHashMap<String, LinkedList<Title>> mapOfTempCategoryNamesAndTitles = new LinkedHashMap<>();
        if (query.isEmpty()) {
            titleList.addAll(filteredList);
            mapOfTempCategoryNamesAndTitles = mapOfCategoryNamesAndTitles;
        } else {
            query = query.toLowerCase();
            for (Title title : filteredList) {
                if (title.getTitle().toLowerCase().contains(query)) {
                    titleList.add(title);
                    String cat = title.getCat();
                    if (mapOfTempCategoryNamesAndTitles.containsKey(cat)) {
                        LinkedList<Title> listOfTitles = mapOfTempCategoryNamesAndTitles.get(cat);
                        if (listOfTitles == null) {
                            listOfTitles = new LinkedList<>();
                        }
                        listOfTitles.add(title);
                        mapOfTempCategoryNamesAndTitles.put(cat, listOfTitles);
                    } else {
                        LinkedList<Title> listOfTitles = new LinkedList<>();
                        listOfTitles.add(title);
                        mapOfTempCategoryNamesAndTitles.put(cat, listOfTitles);
                    }
                }
            }
        }
        adapter.updateList(mapOfTempCategoryNamesAndTitles);
        //adapter.notifyDataSetChanged();
    }


    private void delete_item(String d_Title_top, String d_category, String d_title) {
        final Dialog dialog = new Dialog(LikedlistActivity.this);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.deletecustom_popup);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        TextView txt_yes = dialog.findViewById(R.id.txt_yes);
        TextView txt_no = dialog.findViewById(R.id.txt_no);


        txt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet animationp = Conversions.animation();
                v.startAnimation(animationp);

                like_item_Delete(d_Title_top,d_category,d_title);
                dialog.dismiss();
            }
        });
        txt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet animationp = Conversions.animation();
                v.startAnimation(animationp);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(intent1);
        overridePendingTransition(R.anim.left_out_right, R.anim.left_in_left);
        finish();
    }

}