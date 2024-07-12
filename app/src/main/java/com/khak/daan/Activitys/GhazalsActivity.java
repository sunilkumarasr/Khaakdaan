package com.khak.daan.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.khak.daan.Config.CommonClass;
import com.khak.daan.ModelAndAdapters.GhazalsModel;
import com.khak.daan.ViewController.Conversions;
import com.khak.daan.Config.CustomItemCityListener;
import com.khak.daan.ModelAndAdapters.TitleViewAdapter;
import com.khak.daan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GhazalsActivity extends AppCompatActivity implements View.OnClickListener{


    private CommonClass cc;

    String category,title = "";

    CardView card_1;
    ImageView img_back, img_home,img_search,img_menu;
    TextView text_bar, text_cat;

    SearchView searchView;

    private RecyclerView titleView;

    private List<GhazalsModel> titleList;

    private List<GhazalsModel> filteredList;
    private TitleViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghazals);

        cc = new CommonClass(GhazalsActivity.this);

        img_back = findViewById(R.id.img_back);
        img_home = findViewById(R.id.img_home);
        img_search = findViewById(R.id.img_search);
        img_menu = findViewById(R.id.img_menu);

        text_bar = findViewById(R.id.text_bar);
        text_cat = findViewById(R.id.text_cat);
        searchView = findViewById(R.id.searchview);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            category = (String) b.get("cat");
            title = (String) b.get("title");
        }

        text_bar.setText(category);
        text_cat.setText(category);


        titleView = findViewById(R.id.titleView);

        titleView.setLayoutManager(new LinearLayoutManager(this));

        titleList = new ArrayList<>();
        titleList = new ArrayList<>(titleList);

        filteredList = new ArrayList<>();
        filteredList = new ArrayList<>(filteredList);


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
                if (cat.equals(category)) {
                    // Get the "desc" key array
                    JSONArray descArray = jsonObject.getJSONArray("desc");
                        if(descArray.length()>0){
                        String desc = descArray.getString(0);

                            ArrayList<String> players = new ArrayList<>();
                            for (int j = 0; j < descArray.length(); j++) {
                                players.add(descArray.getString(j));
                            }


                        titleList.add(new GhazalsModel(title_top,desc, cat,players,false));
                        filteredList.add(new GhazalsModel(title_top,desc, cat,players, false));
                    }
                    // Iterate through the "desc" array and print its values
                }
            }

            adapter=new TitleViewAdapter(titleList, getApplicationContext(),new CustomItemCityListener() {
                @Override
                public void onItemClick(View v, String VendorUUID,String Vendortype,String CategoryID) {

                }
            });
            titleView.setAdapter(adapter);
            cc.AnimationLeft(titleView);
            adapter.notifyDataSetChanged();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
            case R.id.img_search:
                AnimationSet animas = Conversions.animation();
                v.startAnimation(animas);

                Intent intents = new Intent(getApplicationContext(), SearchActivity.class);
                ActivityOptions optionss = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
                startActivity(intents, optionss.toBundle());
                break;
        }
    }

    // Set the custom font for each menu item
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(GhazalsActivity.this, view);
        popupMenu.inflate(R.menu.menu_main);

        // Load the custom font
        Typeface customFont = ResourcesCompat.getFont(GhazalsActivity.this, R.font.mehrnastailqwebregular);

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



    private void filterTitles(String query) {
        titleList.clear();

        if (query.isEmpty()) {
            titleList.addAll(filteredList);
        } else {
            query = query.toLowerCase();

            for (GhazalsModel person : filteredList) {
                // Add your custom logic for filtering based on two fields
                if (person.getTitle_top().toLowerCase().contains(query)
                        || String.valueOf(person.getDes()).contains(query)) {
                    titleList.add(person);
                }

            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_out_right, R.anim.left_in_left);
        finish();
    }


}
