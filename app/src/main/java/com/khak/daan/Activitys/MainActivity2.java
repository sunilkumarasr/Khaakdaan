package com.khak.daan.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.khak.daan.ViewController.Conversions;
import com.khak.daan.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    Animation animationUp_bottom,animationbottom_up;
    CardView card_imageview;
    ImageView img_search,img_menu;
    LinearLayout linear_lay;

    CardView btn_word,btn_like,btn_preface,btn_introduction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity2.this, R.color.colorPrimaryDark));

        card_imageview = findViewById(R.id.card_imageview);
        img_search = findViewById(R.id.img_search);
        img_menu = findViewById(R.id.img_menu);
        linear_lay = findViewById(R.id.linear_lay);
        btn_word = findViewById(R.id.btn_word);
        btn_like = findViewById(R.id.btn_like);
        btn_preface = findViewById(R.id.btn_preface);
        btn_introduction = findViewById(R.id.btn_introduction);


        animation_set();


        img_search.setOnClickListener(this);
        img_menu.setOnClickListener(this);
        card_imageview.setOnClickListener(this);
        btn_preface.setOnClickListener(this);
        btn_word.setOnClickListener(this);
        btn_introduction.setOnClickListener(this);
        btn_like.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_search:
                AnimationSet animas = Conversions.animation();
                v.startAnimation(animas);

                Intent intents = new Intent(getApplicationContext(), SearchActivity.class);
                ActivityOptions optionss = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
                startActivity(intents, optionss.toBundle());
                break;
            case R.id.img_menu:
                AnimationSet anima = Conversions.animation();
                v.startAnimation(anima);

                dynamic_menu(v);
                break;
            case R.id.card_imageview:
                AnimationSet animal = Conversions.animation();
                v.startAnimation(animal);

                Intent intent0 = new Intent(getApplicationContext(), ImageZoomActivity.class);
                intent0.putExtra("image_size", "1");
                ActivityOptions options0 = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
                startActivity(intent0, options0.toBundle());
                break;
            case R.id.btn_preface:
                AnimationSet anima1 = Conversions.animation();
                v.startAnimation(anima1);

                Intent intent = new Intent(getApplicationContext(), PrefaceActivity.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
                startActivity(intent, options.toBundle());
                break;
            case R.id.btn_word:
                AnimationSet anima2 = Conversions.animation();
                v.startAnimation(anima2);

                Intent intent2 = new Intent(getApplicationContext(), SpeechActivity.class);
                ActivityOptions options2 = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
                startActivity(intent2, options2.toBundle());
                break;
            case R.id.btn_introduction:
                AnimationSet anima3 = Conversions.animation();
                v.startAnimation(anima3);

                Intent intent3 = new Intent(getApplicationContext(), IntroductionActivity.class);
                ActivityOptions options3 = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
                startActivity(intent3, options3.toBundle());
                break;
            case R.id.btn_like:
                AnimationSet anima4 = Conversions.animation();
                v.startAnimation(anima4);

                Intent intent4 = new Intent(getApplicationContext(), LikedlistActivity.class);
                ActivityOptions options4 = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
                startActivity(intent4, options4.toBundle());
                break;
        }
    }

    private void dynamic_menu(View v) {
        try {
            InputStream inputStream = getAssets().open("diwan_cat.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String jsonString = new String(buffer, StandardCharsets.UTF_8);

            JSONArray jsonArray = new JSONArray(jsonString);

            PopupMenu popupMenu = new PopupMenu(MainActivity2.this, v);

            // Load the custom font
            Typeface customFont = ResourcesCompat.getFont(MainActivity2.this, R.font.mehrnastailqwebregular);

            for (int i = 0; i < jsonArray.length(); i++) {
                String category = jsonArray.getString(i);
//                popupMenu.getMenu().add(0, i, i, category);

                // Set the custom font for the MenuItem
                MenuItem menuItem = popupMenu.getMenu().add(0, i, i, category);
                SpannableString spannableString = new SpannableString(category);
                spannableString.setSpan(new CustomTypefaceSpan("", customFont), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                menuItem.setTitle(spannableString);
            }

            //one more items add static 6th item
//            popupMenu.getMenu().add(0, 5, 5, "متفرق اشعار");
            MenuItem menuItem = popupMenu.getMenu().add(0,5,5,"متفرق اشعار");
            SpannableString spannableString = new SpannableString("متفرق اشعار");
            spannableString.setSpan(new CustomTypefaceSpan("", customFont), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            menuItem.setTitle(spannableString);


            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    if (item.getItemId()==5){
                        Intent intent = new Intent(getApplicationContext(), StaticCardActivity.class);
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
                        startActivity(intent, options.toBundle());
                    }else {
                        String category = String.valueOf(item.getTitle());
                        Intent intent = new Intent(getApplicationContext(), GhazalsActivity.class);
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
                        intent.putExtra("cat",category);
                        startActivity(intent, options.toBundle());
                    }

                    return true;
                }
            });

            popupMenu.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void animation_set() {
        animationUp_bottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_down);
        card_imageview.setAnimation(animationUp_bottom);

        animationbottom_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_bottom_up);
        linear_lay.setAnimation(animationbottom_up);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }



}