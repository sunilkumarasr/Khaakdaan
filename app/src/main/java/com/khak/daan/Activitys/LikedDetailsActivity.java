package com.khak.daan.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.khak.daan.Config.MyDatabaseHelper;
import com.khak.daan.ModelAndAdapters.Description;
import com.khak.daan.ModelAndAdapters.DescriptionViewAdapter;
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

public class LikedDetailsActivity extends AppCompatActivity {

    Cursor cursor;
    MyDatabaseHelper myDb;
    String Title_top = "",title = "", category = "", Title_id;
    boolean active;
    ImageView img_back, img_home, share, facebook, starwhite, starfill;

    ImageView leftArrowButton, rightArrowButton;

    int main_content_length, title_value;
    JSONArray jsonArray;


    TextView text_bar, text_title;

    private RecyclerView descriptionView;

    private List<Description> descriptionList;

    private DescriptionViewAdapter adapter;
    ImageView copy_img;
    TextView txt_title;
    ImageView img_logo;

//    //zoom
//    private ZoomLayout zoomLayout;

    WebView webView;
    String sss = "";
    String cat;
    String author;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_details);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Title_top = (String) bundle.get("Title_top");
            category = (String) bundle.get("cat");
            title = (String) bundle.get("title");
            active = (boolean) bundle.get("active");
        }
        webView = findViewById(R.id.webView);
        // Enable JavaScript (optional)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Enable zoom controls
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false); // Hides the zoom controls

        // Optional: Enable pinch-to-zoom gesture
        webSettings.setSupportZoom(true);

//        zoomLayout = findViewById(R.id.zoomLayout);
        img_back = findViewById(R.id.img_back);
        img_home = findViewById(R.id.img_home);
        text_bar = findViewById(R.id.text_bar);
        facebook = findViewById(R.id.facebook);
        starwhite = findViewById(R.id.starwhite);
        starfill = findViewById(R.id.starfill);
        share = findViewById(R.id.share);
        copy_img = findViewById(R.id.copy_img);
        txt_title = findViewById(R.id.txt_title);
        img_logo = findViewById(R.id.img_logo);
        descriptionView = findViewById(R.id.descriptionView);
//        SimpleZoomPanLog log = new SimpleZoomPanLog("asdasd", null);
//        log.setLogger(zoomLayout);

        if (Title_top.equalsIgnoreCase("")){
            txt_title.setVisibility(View.GONE);
            img_logo.setVisibility(View.VISIBLE);
        }

        txt_title.setText(Title_top);

        text_bar.setText(category);

        myDb = new MyDatabaseHelper(this);

        starwhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet anima = Conversions.animation();
                v.startAnimation(anima);

                myDb.insertLiked(Title_top,category, title);
                starwhite.setVisibility(View.GONE);
                starfill.setVisibility(View.VISIBLE);
            }
        });
        starfill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet anima = Conversions.animation();
                v.startAnimation(anima);

                myDb.deleteLikedData(Title_top,category, title);
                starwhite.setVisibility(View.VISIBLE);
                starfill.setVisibility(View.GONE);

                Intent intent4 = new Intent(getApplicationContext(), LikedlistActivity.class);
                ActivityOptions options4 = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
                startActivity(intent4, options4.toBundle());
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet anima = Conversions.animation();
                v.startAnimation(anima);
                // Implement the logic to copy all the text here
                StringBuilder textToshare = new StringBuilder();
                for (Description description : descriptionList) {
                    textToshare.append(description.getDescription()).append("\n");
                }
                if (category.equals("دُعا") || category.equals("نعت شریف") || category.equals("غزلیں") || category.equals("نظمیں")) {
                    textToshare.append(author);
                }
                shareToFacebook(textToshare.toString());
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet anima = Conversions.animation();
                v.startAnimation(anima);

                // Implement the logic to copy all the text here
                StringBuilder textToshare = new StringBuilder();
                for (Description description : descriptionList) {
                    textToshare.append(description.getDescription()).append("\n");
                }
                if (category.equals("دُعا") || category.equals("نعت شریف") || category.equals("غزلیں") || category.equals("نظمیں")) {
                    textToshare.append(author);
                }
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, textToshare.toString());
                intent.setType("text/palane");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });


        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet anima = Conversions.animation();
                v.startAnimation(anima);

                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_out_right, R.anim.left_in_left);
                startActivity(intent, options.toBundle());
            }
        });



        descriptionView.setLayoutManager(new LinearLayoutManager(this));

        title_description();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet anima = Conversions.animation();
                v.startAnimation(anima);

                Intent intent1 = new Intent(getApplicationContext(), LikedlistActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.left_out_right, R.anim.left_in_left);
                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet anima = Conversions.animation();
                v.startAnimation(anima);

                // Implement the logic to copy all the text here
                StringBuilder textToshare = new StringBuilder();
                for (Description description : descriptionList) {
                    textToshare.append(description.getDescription()).append("\n");
                }
                if (category.equals("دُعا") || category.equals("نعت شریف") || category.equals("غزلیں") || category.equals("نظمیں")) {
                    textToshare.append(author);
                }
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, textToshare.toString());
                intent.setType("text/palane");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        copy_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationSet anima = Conversions.animation();
                view.startAnimation(anima);

                // Implement the logic to copy all the text here
                StringBuilder textToCopy = new StringBuilder();

                for (Description description : descriptionList) {
                    textToCopy.append(description.getDescription()).append("\n");
                }
                if (category.equals("دُعا") || category.equals("نعت شریف") || category.equals("غزلیں") || category.equals("نظمیں")) {
                    textToCopy.append(author);
                }
                // Copy the text to the clipboard
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copied Text", textToCopy.toString());
                clipboardManager.setPrimaryClip(clipData);

            }
        });

    }


    public void title_description() {

        try {
            descriptionList = new ArrayList<>();
            InputStream inputStream = getAssets().open("main_content.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String jsonString = new String(buffer, StandardCharsets.UTF_8);
            jsonArray = new JSONArray(jsonString);
            main_content_length = jsonArray.length();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                cat = jsonObject.getString("cat");
                String title_t = jsonObject.getString("title");
                author = jsonObject.getString("author");
                String tit = "";
                JSONArray descArray = jsonObject.getJSONArray("desc");
                if (descArray.length() > 0) {
                    tit = descArray.getString(0);
                }
                if (cat.equals(category) && tit.equals(title)) {

                    cursor = myDb.getLikedOneData(title_t,cat, tit);
                    if (cursor != null && cursor.moveToFirst()) {
                        while (!cursor.isAfterLast()) {

                            Title_id = cursor.getString(0);
                            System.out.println("Title_id::" + Title_id);

                            cursor.moveToNext();
                        }
                    }
                    title_value = i;
                    // Iterate through the "desc" array and print its values
                    for (int j = 0; j < descArray.length(); j++) {
                        String descValue = descArray.getString(j);
                        descriptionList.add(new Description(descValue));
                        sss += "\n" +descValue;
                    }

                    StringBuilder stringBuilder = new StringBuilder();

                    for (int j = 0; j < descriptionList.size(); j++) {
                        Description line = descriptionList.get(j);

                        String descValue = line.getDescription();
                        if (descValue.isEmpty()) {
                            stringBuilder.append("<p></p>");
                        } else {

                            if(j==0){
                                stringBuilder.append("<p>").append(descValue).append("</p><p>");
                            }else if (j==descriptionList.size()-1){
                                stringBuilder.append(descValue).append("</p>");
                            }else {
                                stringBuilder.append(descValue).append("</p><p>");
                            }

                        }

                    }

                    String htmlContent = parseJsonToHtml(descriptionList,author);
                    webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);

                }
            }

            adapter = new DescriptionViewAdapter(getApplicationContext(), descriptionList, (v, description, cNAme, CUUId) -> {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("copy text", description);
                assert clipboardManager != null;
                clipboardManager.setPrimaryClip(clipData);
            });
            descriptionView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private String parseJsonToHtml(List<Description> descriptionList,String author) {
        StringBuilder htmlBuilder = new StringBuilder("<html><head>");
        htmlBuilder.append("<link rel='stylesheet' type='text/css' href='file:///android_asset/styles.css' />");
        htmlBuilder.append("</head><body>");

        for (Description line : descriptionList) {
            String descValue = line.getDescription();
            if (!descValue.isEmpty()) {
                if (descValue.equals("۔ق۔")) {
                    htmlBuilder.append("<p class='stars'>").append(descValue).append("</p>");
                } else {
                    htmlBuilder.append("<p class='center-word'>").append(descValue).append("</p>");
                }
            }else {
                htmlBuilder.append("<p style='padding-bottom: 5px;'></p>");
            }
        }
        if (category.equals("دُعا") || category.equals("نعت شریف") || category.equals("غزلیں") || category.equals("نظمیں")) {
            htmlBuilder.append("<p style='line-height: 1.5;font-size: 20px;text-align: center;'>").append(author).append("</p>");
        }
        htmlBuilder.append("</body></html>");
        return htmlBuilder.toString();
    }




    //share post for facebook
    private void shareToFacebook(String facebook_txt) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, facebook_txt);

        // Find Facebook package
        PackageManager pm = getPackageManager();
        String packageName = "com.facebook.katana";
        Intent facebookIntent = new Intent(Intent.ACTION_SEND);
        facebookIntent.setType("text/plain");
        facebookIntent.putExtra(Intent.EXTRA_TEXT, facebook_txt);

        // Verify if the Facebook app is installed
        ResolveInfo resolvedInfo = pm.resolveActivity(facebookIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolvedInfo != null) {
            shareIntent.setPackage(packageName);
        }

        // Start the activity
        startActivity(shareIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(getApplicationContext(), LikedlistActivity.class);
        startActivity(intent1);
        overridePendingTransition(R.anim.left_out_right, R.anim.left_in_left);
        finish();
    }

}
