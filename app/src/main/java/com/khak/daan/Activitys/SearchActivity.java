package com.khak.daan.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.khak.daan.Config.CustomItemCityListener;
import com.khak.daan.ModelAndAdapters.GhazalsModel;
import com.khak.daan.ModelAndAdapters.SeaechAdapter;
import com.khak.daan.ModelAndAdapters.SeaechDescriptionAdapter;
import com.khak.daan.R;
import com.khak.daan.ViewController.Conversions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {


    ImageView img_back, img_home, img_menu;
    EditText edit_search;

    private RecyclerView RecyclerView_descriptionView;
    private RecyclerView RecyclerView_total;

    private List<String> descriptionStringList;
    private List<GhazalsModel> descriptionList;
    private List<GhazalsModel> totalList;
    private List<GhazalsModel> filteredList;

    private SeaechDescriptionAdapter description_adapter;
    private SeaechAdapter total_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        img_back = findViewById(R.id.img_back);
        img_home = findViewById(R.id.img_home);
        img_menu = findViewById(R.id.img_menu);
        edit_search = findViewById(R.id.edit_search);
        edit_search.setGravity(Gravity.END);
        edit_search.setSelection(edit_search.getText().length());
        edit_search.setPadding(0, 30, 0, 0);
        RecyclerView_descriptionView = findViewById(R.id.RecyclerView_descriptionView);
        RecyclerView_total = findViewById(R.id.RecyclerView_total);

        RecyclerView_descriptionView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView_total.setLayoutManager(new LinearLayoutManager(this));


        title_description();


        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Do something before text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Do something as text changes
                filterTitles(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (edit_search.getText().toString().equalsIgnoreCase("")){
                    RecyclerView_descriptionView.setVisibility(View.GONE);
                    RecyclerView_total.setVisibility(View.GONE);
                }

            }
        });


        img_back.setOnClickListener(this);
        img_home.setOnClickListener(this);
        img_menu.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                AnimationSet anima = Conversions.animation();
                v.startAnimation(anima);

                overridePendingTransition(R.anim.left_out_right, R.anim.left_in_left);
                finish();
                break;
            case R.id.img_home:
                AnimationSet animae = Conversions.animation();
                v.startAnimation(animae);

                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
                startActivity(intent, options.toBundle());
                break;
            case R.id.img_menu:
                AnimationSet animau = Conversions.animation();
                v.startAnimation(animau);

                showPopupMenu(v);
                break;
        }
    }

    // Set the custom font for each menu item
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(SearchActivity.this, view);
        popupMenu.inflate(R.menu.menu_main);

        // Load the custom font
        Typeface customFont = ResourcesCompat.getFont(SearchActivity.this, R.font.mehrnastailqwebregular);

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
        intent.putExtra("cat", category);
        startActivity(intent, options.toBundle());
    }

    private void ghail_Activity() {
        Intent intent2 = new Intent(getApplicationContext(), StaticCardActivity.class);
        ActivityOptions options2 = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
        startActivity(intent2, options2.toBundle());
    }


    public void title_description() {
        filteredList = new ArrayList<>();
        filteredList.clear();

        try {
            InputStream inputStream = getAssets().open("main_content.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String jsonString = new String(buffer, StandardCharsets.UTF_8);

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title_top = jsonObject.getString("title");
                String cat = jsonObject.getString("cat");

                // Get the "desc" key array
                JSONArray descArray = jsonObject.getJSONArray("desc");
                if (descArray.length() > 0) {
                    String desc = descArray.getString(0);

                    ArrayList<String> players = new ArrayList<>();
                    for (int j = 0; j < descArray.length(); j++) {
                        players.add(descArray.getString(j));
                    }

                    filteredList.add(new GhazalsModel(title_top, desc, cat, players, false));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void filterTitles(String query) {
        descriptionList = new ArrayList<>();
        descriptionList.clear();

        descriptionStringList = new ArrayList<>();
        descriptionStringList.clear();

        totalList = new ArrayList<>();
        totalList.clear();

        RecyclerView_descriptionView.setVisibility(View.GONE);

        String type_search = "";

        ArrayList<String> players = new ArrayList<>();

        if (query.isEmpty()) {
            totalList.addAll(filteredList);
            descriptionList.addAll(filteredList);
        } else {
            query = query.toLowerCase();

            for (GhazalsModel person : filteredList) {

                for (int j = 0; j < person.getDes().size(); j++) {

                    if (person.getDes().get(j).toLowerCase().contains(query)) {
                        type_search = query;
                        //setText
                        descriptionStringList.add(person.getDes().get(j));

                        RecyclerView_descriptionView.setVisibility(View.VISIBLE);

                        //all list
                        descriptionList.add(person);

                        //description
                        description_adapter = new SeaechDescriptionAdapter(descriptionList,descriptionStringList, query, getApplicationContext(), new CustomItemCityListener() {
                            @Override
                            public void onItemClick(View v, String description, String Vendortype, String CategoryID) {
//                                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                                ClipData clipData = ClipData.newPlainText("copy text", description);
//                                assert clipboardManager != null;
//                                clipboardManager.setPrimaryClip(clipData);
                            }
                        });
                        RecyclerView_descriptionView.setAdapter(description_adapter);
                        description_adapter.notifyDataSetChanged();
                    }
                }

//                if (person.getTitle_top().toLowerCase().contains(query) || person.getTitle().toLowerCase().contains(query) || person.getCat().toLowerCase().contains(query)) {
//
//                    totalList.add(person);
//
//                    RecyclerView_descriptionView.setVisibility(View.GONE);
//                    RecyclerView_total.setVisibility(View.VISIBLE);
//
//                    //total list
//                    total_adapter = new SeaechAdapter(totalList, type_search, getApplicationContext(), new CustomItemCityListener() {
//                        @Override
//                        public void onItemClick(View v, String description, String Vendortype, String CategoryID) {
//                            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                            ClipData clipData = ClipData.newPlainText("copy text", description);
//                            assert clipboardManager != null;
//                            clipboardManager.setPrimaryClip(clipData);
//                        }
//                    });
//                    RecyclerView_total.setAdapter(total_adapter);
//                    total_adapter.notifyDataSetChanged();
//                }


            }



        }

    }
}