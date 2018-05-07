package com.example.ankita.intellichef;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


/**
 * Created by panny on 06-05-2018.
 */

public class WebViewPage extends Activity {
    Button b1;
    EditText ed1;

    private WebView wv1;
    public float ratingValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewpage);
        Toast.makeText(getApplicationContext(),"hello", Toast.LENGTH_LONG).show();
        wv1 = (WebView) findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());


        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl("https://www.vegrecipesofindia.com/idli-recipe-how-to-make-soft-idlis/");
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder mBuild = new AlertDialog.Builder(WebViewPage.this);
        View mView = getLayoutInflater().inflate(R.layout.ratingbar,null);

        final RatingBar ratebar = (RatingBar)mView.findViewById(R.id.ratingBar);
        ratebar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingValue = rating;
                Toast.makeText(WebViewPage.this, ""+rating, Toast.LENGTH_SHORT).show();
            }
        });
        Button btnSubmit=(Button)mView.findViewById(R.id.btnSubRating);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WebViewPage.this, ""+ratingValue, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), recipeDisplay.class);
                startActivity(i);
            }


        });

        mBuild.setView(mView);
        AlertDialog dialog=mBuild.create();
        dialog.show();

    }
}

class MyBrowser extends WebViewClient {
   @Override
   public boolean shouldOverrideUrlLoading(WebView view, String url) {
       view.loadUrl(url);
       return true;
   }
}

