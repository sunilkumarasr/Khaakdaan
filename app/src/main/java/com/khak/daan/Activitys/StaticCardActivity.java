package com.khak.daan.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.khak.daan.Config.CustomItemCityListener;
import com.khak.daan.ModelAndAdapters.Description;
import com.khak.daan.ModelAndAdapters.DescriptionViewAdapter;
import com.khak.daan.ViewController.Conversions;
import com.khak.daan.Config.MyDatabaseHelper;
import com.khak.daan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StaticCardActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView img_back, img_home, img_search, img_menu;

    MyDatabaseHelper myDb;

    private RecyclerView descriptionView;
    private List<Description> descriptionList;
    private DescriptionViewAdapter adapter;


    WebView webView;
    String sss = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_card);

        myDb = new MyDatabaseHelper(this);

        webView = findViewById(R.id.webView);
        // Enable JavaScript (optional)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Enable zoom controls
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false); // Hides the zoom controls

        // Optional: Enable pinch-to-zoom gesture
        webSettings.setSupportZoom(true);


        img_back = findViewById(R.id.img_back);
        img_home = findViewById(R.id.img_home);
        img_search = findViewById(R.id.img_search);
        img_menu = findViewById(R.id.img_menu);

        descriptionView = findViewById(R.id.descriptionView);
        descriptionView.setLayoutManager(new LinearLayoutManager(this));
        details_list();


        img_back.setOnClickListener(this);
        img_home.setOnClickListener(this);
        img_menu.setOnClickListener(this);
        img_search.setOnClickListener(this);
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
        PopupMenu popupMenu = new PopupMenu(StaticCardActivity.this, view);
        popupMenu.inflate(R.menu.menu_main);

        // Load the custom font
        Typeface customFont = ResourcesCompat.getFont(StaticCardActivity.this, R.font.mehrnastailqwebregular);

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


    private void details_list() {
        descriptionList = new ArrayList<>();
        String jsonString = null;
        try {
            InputStream is = getAssets().open("last_content.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            JSONArray jsonArray = new JSONArray(jsonString); // Assuming the top level of the JSON is an array

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray descArray = jsonObject.getJSONArray("desc");
                for (int j = 0; j < descArray.length(); j++) {
                    String descValue = descArray.getString(j);
                    descriptionList.add(new Description(descValue));
                    sss += "\n" + descValue;

                }

                StringBuilder stringBuilder = new StringBuilder();

                for (int j = 0; j < descriptionList.size(); j++) {
                    Description line = descriptionList.get(j);

                    String descValue = line.getDescription();
                    if (descValue.isEmpty()) {
                        stringBuilder.append("<p></p>");
                    } else {
                        if (j == 0) {
                            stringBuilder.append("<p>").append(descValue).append("</p><p>");
                        } else if (j == descriptionList.size() - 1) {
                            stringBuilder.append(descValue).append("</p>");
                        } else {
                            stringBuilder.append(descValue).append("</p><p>");
                        }

                    }

                }
                webView.getSettings().setJavaScriptEnabled(true);

                String htmlContent = parseJsonToHtml(descriptionList);
                webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);

            }

            adapter = new DescriptionViewAdapter(getApplicationContext(), descriptionList, new CustomItemCityListener() {
                @Override
                public void onItemClick(View v, String description, String cNAme, String CUUId) {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("copy text", description);
                    assert clipboardManager != null;
                    clipboardManager.setPrimaryClip(clipData);
                }
            });
            descriptionView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String parseJsonToHtml(List<Description> descriptionList) {
        StringBuilder htmlBuilder = new StringBuilder("<html><head>");
        htmlBuilder.append("<link rel='stylesheet' type='text/css' href='file:///android_asset/styles.css' />");
        htmlBuilder.append("</head><body>");
        for (int j = 0; j < descriptionList.size(); j++) {
            Description line = descriptionList.get(j);
            String descValue = line.getDescription();
            if (!descValue.isEmpty()) {

                if (descValue.equals("٭٭٭") || descValue.equals("۔ق۔")) {
                    htmlBuilder.append("<p class='stars'>").append(descValue).append("</p>");
                } else {
                    htmlBuilder.append("<p class='center-word'>").append(descValue).append("</p>");
                }

            }

        }

        htmlBuilder.append("</body></html>");
        return htmlBuilder.toString();
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_out_right, R.anim.left_in_left);
        finish();
    }
}
