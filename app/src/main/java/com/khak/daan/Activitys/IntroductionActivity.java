package com.khak.daan.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.khak.daan.ViewController.Conversions;
import com.khak.daan.R;

public class IntroductionActivity extends AppCompatActivity implements View.OnClickListener{

    Animation animationUp_bottom,animationbottom_up;
    CardView card_imageview;

    ImageView img_back, img_home,img_search,img_menu;

    LinearLayout linaer_img;
    LinearLayout linear_txt_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        card_imageview = findViewById(R.id.card_imageview);
        img_back = findViewById(R.id.img_back);
        img_home = findViewById(R.id.img_home);
        img_search = findViewById(R.id.img_search);
        img_menu = findViewById(R.id.img_menu);
        linaer_img = findViewById(R.id.linaer_img);
        linear_txt_intro = findViewById(R.id.linear_txt_intro);

        animation_set();

        img_back.setOnClickListener(this);
        img_home.setOnClickListener(this);
        img_menu.setOnClickListener(this);
        img_search.setOnClickListener(this);
        card_imageview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        // common animation
        AnimationSet animation = Conversions.animation();
        v.startAnimation(animation);

        if (id == R.id.img_back) {

            overridePendingTransition(R.anim.left_out_right, R.anim.left_in_left);
            finish();

        } else if (id == R.id.img_home) {

            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(
                    getApplicationContext(),
                    R.anim.left_out_right,
                    R.anim.left_in_left
            );
            startActivity(intent, options.toBundle());

        } else if (id == R.id.img_menu) {

            showPopupMenu(v);

        } else if (id == R.id.img_search) {

            Intent intents = new Intent(getApplicationContext(), SearchActivity.class);
            ActivityOptions optionss = ActivityOptions.makeCustomAnimation(
                    getApplicationContext(),
                    R.anim.left_out_right,
                    R.anim.left_in_left
            );
            startActivity(intents, optionss.toBundle());

        } else if (id == R.id.card_imageview) {

            Intent intent4 = new Intent(getApplicationContext(), ImageZoomActivity.class);
            intent4.putExtra("image_size", "2");
            ActivityOptions options4 = ActivityOptions.makeCustomAnimation(
                    getApplicationContext(),
                    R.anim.left_out_right,
                    R.anim.left_in_left
            );
            startActivity(intent4, options4.toBundle());
        }
    }


    private void animation_set() {
        animationUp_bottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_down);
        linaer_img.setAnimation(animationUp_bottom);

        animationbottom_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_bottom_up);
        linear_txt_intro.setAnimation(animationbottom_up);
    }

    // Set the custom font for each menu item
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(IntroductionActivity.this, view);
        popupMenu.inflate(R.menu.menu_main);

        // Load the custom font
        Typeface customFont = ResourcesCompat.getFont(IntroductionActivity.this, R.font.mehrnastailqwebregular);

        // Set the custom font for each menu item
        Menu menu = popupMenu.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            applyCustomFontToMenuItem(menuItem, customFont);
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.menu_item_1 || id == R.id.menu_item_2 ||
                        id == R.id.menu_item_3 || id == R.id.menu_item_4 ||
                        id == R.id.menu_item_5) {

                    list_Activity(menuItem);
                    return true;

                } else if (id == R.id.menu_item_6) {

                    ghail_Activity();
                    return true;

                } else {
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
        overridePendingTransition(R.anim.left_out_right, R.anim.left_in_left);
        finish();
    }

}