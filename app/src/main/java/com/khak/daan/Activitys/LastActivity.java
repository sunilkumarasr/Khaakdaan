package com.khak.daan.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khak.daan.Config.CommonData;
import com.khak.daan.ModelAndAdapters.GhazalsModel;
import com.khak.daan.ViewController.Conversions;
import com.khak.daan.Config.CustomItemCityListener;
import com.khak.daan.Config.MyDatabaseHelper;
import com.khak.daan.ModelAndAdapters.DescriptionViewAdapter;
import com.khak.daan.ModelAndAdapters.Description;
import com.khak.daan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import arsatech.co.justifiedtextview.JustifiedTextView;

public class
LastActivity extends AppCompatActivity {
    Cursor cursor;
    MyDatabaseHelper myDb;
    String title = "", title_top = "", category = "", Title_id;
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

    TextView txt_test;

    private int totalCount = 0;
    private int selectedPosition = 0;
    private List<GhazalsModel> listOfTitles;
    String sss = "";

    private JustifiedTextView adjusttext;

//    //zoom
//    private ZoomLayout zoomLayout;

    LinearLayout llContainer;

    WebView webView;
    String title_t;

    String author;

    private boolean isAuthorIncluded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.containsKey(getString(R.string.total_count))) {
                totalCount = bundle.getInt(getString(R.string.total_count));
            }
            if (bundle.containsKey(getString(R.string.position))) {
                selectedPosition = bundle.getInt(getString(R.string.position));
            }
            category = (String) bundle.get("cat");
            title_top = (String) bundle.get("title_top");
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
        leftArrowButton = findViewById(R.id.left_arrow);
        rightArrowButton = findViewById(R.id.right_arrow);
        txt_test = findViewById(R.id.txt_test);
        llContainer = findViewById(R.id.llContainer);
//        SimpleZoomPanLog log = new SimpleZoomPanLog("asdasd", null);
//        log.setLogger(zoomLayout);

        myDb = new MyDatabaseHelper(this);
        listOfTitles = CommonData.getInstance().getTitleList();

        text_bar.setText(category);
        txt_title.setText(title_top);

        if (title_top.equalsIgnoreCase("")) {
            txt_title.setVisibility(View.GONE);
            img_logo.setVisibility(View.VISIBLE);
        }

        if (selectedPosition == 0) {
            leftArrowButton.setVisibility(View.GONE);
        }

        if (selectedPosition + 1 == listOfTitles.size()) {
            rightArrowButton.setVisibility(View.GONE);
        }


        starwhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet anima = Conversions.animation();
                v.startAnimation(anima);

                myDb.insertLiked(title_top, category, title);
                starwhite.setVisibility(View.GONE);
                starfill.setVisibility(View.VISIBLE);
            }
        });
        starfill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet anima = Conversions.animation();
                v.startAnimation(anima);

                myDb.deleteLikedData(title_top, category, title);
                starwhite.setVisibility(View.VISIBLE);
                starfill.setVisibility(View.GONE);
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

        descriptionView = findViewById(R.id.descriptionView);

        descriptionView.setLayoutManager(new LinearLayoutManager(this));

        title_description();

        text_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet anima = Conversions.animation();
                v.startAnimation(anima);

                overridePendingTransition(R.anim.left_in_left, R.anim.left_out_right);
                finish();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet anima = Conversions.animation();
                v.startAnimation(anima);

                overridePendingTransition(R.anim.left_out_right, R.anim.left_in_left);
                finish();
            }
        });

        leftArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition != 0) {
                    selectedPosition = selectedPosition - 1;
                    if (selectedPosition == 0) {
                        leftArrowButton.setVisibility(View.GONE);
                        rightArrowButton.setVisibility(View.VISIBLE);
                    } else {
                        rightArrowButton.setVisibility(View.VISIBLE);
                    }
                    GhazalsModel selectedTitle = listOfTitles.get(selectedPosition);
                    title = selectedTitle.getTitle();
                    //title set
                    txt_title.setText(selectedTitle.getTitle_top());
                    title_description();
                }
            }
        });
        rightArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = selectedPosition + 1;
                if (selectedPosition == listOfTitles.size() - 1) {
                    rightArrowButton.setVisibility(View.GONE);
                    leftArrowButton.setVisibility(View.VISIBLE);
                } else {
                    leftArrowButton.setVisibility(View.VISIBLE);
                }
                if (selectedPosition <= listOfTitles.size() - 1) {
                    GhazalsModel selectedTitle = listOfTitles.get(selectedPosition);
                    title = selectedTitle.getTitle();
                    //title set
                    txt_title.setText(selectedTitle.getTitle_top());
                    title_description();
                }
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

                toast();

                // Implement the logic to copy all the text here
                StringBuilder textToshare = new StringBuilder();

                for (Description description : descriptionList) {
                    textToshare.append(description.getDescription()).append("\n");
                }
                if (category.equals("دُعا") || category.equals("نعت شریف") || category.equals("غزلیں") || category.equals("نظمیں")) {
                    textToshare.append(author);
                }
                // Copy the text to the clipboard
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copied Text", textToshare.toString());
                clipboardManager.setPrimaryClip(clipData);

            }
        });


    }


    public void copyTextToClipboard(TextView txtView) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(txtView.getText().toString());
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("text label", txtView.getText().toString());
            clipboard.setPrimaryClip(clip);
        }
    }

    private void toast() {
        Toast toast = new Toast(LastActivity.this);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custome_toast, null);
        TextView textView = (TextView) view.findViewById(R.id.custom_toast_text);
        textView.setText("یہ کلام کاپی کیا جاچکا ہے۔ آپ اسے کہیں بھی پیسٹ کر سکتے ہیں۔");
        toast.setView(view);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 4000);
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

            // Iterate through the JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String cat = jsonObject.getString("cat");
                title_t = jsonObject.getString("title");
                author = jsonObject.getString("author");
                String tit = "";
                JSONArray descArray = jsonObject.getJSONArray("desc");
                if (descArray.length() > 0) {
                    tit = descArray.getString(0);
                }
                if (cat.equals(category) && tit.equals(title)) {
                    starfill.setVisibility(View.GONE);
                    starwhite.setVisibility(View.VISIBLE);
                    cursor = myDb.getLikedData();
                    if (cursor != null && cursor.moveToFirst()) {
                        // Iterate over the cursor until it reaches the end
                        while (!cursor.isAfterLast()) {
                            // Retrieve data from the cursor for each row

                            Title_id = cursor.getString(1);
                            String ca = cursor.getString(2);
                            String ti = cursor.getString(3);

                            if (cat.equalsIgnoreCase(ca) && tit.equalsIgnoreCase(ti)) {
                                System.out.println("Title_id::" + Title_id);
                                starfill.setVisibility(View.VISIBLE);
                                starwhite.setVisibility(View.GONE);
                            }

                            cursor.moveToNext();
                        }
                    }


                    title_value = i;


                    // Get the "desc" key array
                    // Iterate through the "desc" array and print its values
                    for (int k = 0; k < descArray.length(); k++) {
                        String descValue = descArray.getString(k);
                        descriptionList.add(new Description(descValue));
                        sss += "<\n>" + descValue;

                    }

                    StringBuilder stringBuilder = new StringBuilder();

//
//                    for (Description line : descriptionList) {
//                        stringBuilder.append(line.getDescription()).append("\n");
//                    }

                    for (int j = 0; j < descriptionList.size(); j++) {
                        Description line = descriptionList.get(j);

                        String descValue = line.getDescription();
                        if (descValue.isEmpty()) {
                            stringBuilder.append("<p></p>");
                        } else {

                            if (j == 0) {
                                stringBuilder.append("<p>").append(descValue).append("</p><p>");
                            } else if (j == descriptionList.size() - 1) {
                                stringBuilder.append(descValue);
                            } else {
                                stringBuilder.append(descValue).append("</p><p>");
                            }

                        }

                    }
                    WebSettings webSettings = webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    String htmlContent = parseJsonToHtml(descriptionList, author);
                    webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);

                }
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private String parseJsonToHtml(List<Description> descriptionList, String author) {
        StringBuilder htmlBuilder = new StringBuilder("<html><head>");
        htmlBuilder.append("<link rel='stylesheet' type='text/css' href='file:///android_asset/styles.css' />");
        htmlBuilder.append("</head><body>");

        if (title_t.equals("حرفِ گم") || title_t.equals("وہیں تو عشق رہتا ہے")) {
            for (Description line : descriptionList) {
                String descValue = line.getDescription();
                if (!descValue.isEmpty()) {
                    if (descValue.equals("۔ق۔") || descValue.equals("٭٭٭")) {
                        htmlBuilder.append("<p class='stars'>").append(descValue).append("</p>");
                    } else {
                        htmlBuilder.append("<p class='right-align'>").append(descValue).append("</p>");
                    }
                } else {
                    htmlBuilder.append("<p style='padding-bottom: 3%;padding-top: 2%;'></p>");
                }
            }
        } else {

            for (Description line : descriptionList) {
                String descValue = line.getDescription();
                if (!descValue.isEmpty()) {
                    if (descValue.equals("۔ق۔") || descValue.equals("٭٭٭")) {
                        htmlBuilder.append("<p class='stars'>").append(descValue).append("</p>");
                    } else {
                        htmlBuilder.append("<p class='center-word'>").append(descValue).append("</p>");
                    }
                } else {
                    htmlBuilder.append("<p style='padding-bottom: 3%;padding-top: 2%;'></p>");
                }
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


        try {
            // Start the activity
            startActivity(shareIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "The facebook app is not installed", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_out_right, R.anim.left_in_left);
        finish();
    }


}
