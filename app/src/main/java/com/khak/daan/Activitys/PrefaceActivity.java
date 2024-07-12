package com.khak.daan.Activitys;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.khak.daan.R;
import com.khak.daan.ViewController.Conversions;

public class PrefaceActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView img_back, img_home,img_search,img_menu;
    TextView copy_text;


    Animation animationside_to_left;
    LinearLayout linear_text;



    WebView webView;
    String sss = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preface);


        img_back = findViewById(R.id.img_back);
        img_home = findViewById(R.id.img_home);
        img_menu = findViewById(R.id.img_menu);
        img_search = findViewById(R.id.img_search);
        linear_text = findViewById(R.id.linear_text);


        copy_text = findViewById(R.id.copy_text);

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);

        String ss = getResources().getString(R.string.preface_list0)+"<br>"+getResources().getString(R.string.name_for_line1)+"<br>"+getResources().getString(R.string.date_for_line3)+"<br>"+getResources().getString(R.string.last_line3);

        String htmlContent = parseJsonToHtml(ss);
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);

        animation_set();

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
                overridePendingTransition(R.anim.left_out_right, R.anim.left_in_left);
                startActivity(intent1);
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

    private void animation_set() {
        animationside_to_left = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_down);
        copy_text.setAnimation(animationside_to_left);
    }

    private String parseJsonToHtml(String descriptionList) {
        StringBuilder htmlBuilder = new StringBuilder("<html><head><style type='text/css'>@font-face {font-family: 'mehrnastailqwebregular'; src: url('file:///android_res/font/mehrnastailqwebregular.ttf')} body {font-family: 'mehrnastailqwebregular';}</style></head><body>");

        htmlBuilder.append("<p style='font-size:20px; word-spacing: 13px; text-align: justify; text-align-last: right; direction: rtl;'>").append(descriptionList).append("</p>");

        htmlBuilder.append("</body></html>");
        return htmlBuilder.toString();
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(PrefaceActivity.this, view);
        popupMenu.inflate(R.menu.menu_main);

        // Load the custom font
        Typeface customFont = ResourcesCompat.getFont(PrefaceActivity.this, R.font.mehrnastailqwebregular);

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(getApplicationContext(), MainActivity2.class);
        overridePendingTransition(R.anim.left_out_right, R.anim.left_in_left);
        startActivity(intent1);
        finish();
    }



}