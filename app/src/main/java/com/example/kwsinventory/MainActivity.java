package com.example.kwsinventory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private WebView myWebView;
    private Button mButtonBack;
    private Button mButtonForward;
    private ProgressBar progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSavePassword(true);
        webSettings.setLightTouchEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAppCacheEnabled(true);

        myWebView.setWebViewClient(new WebViewClient());
        myWebView.setSoundEffectsEnabled(true);
        myWebView.requestFocus();


        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#6b8e23"))
        );

        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
            }
        });


        WebSettings settings = myWebView.getSettings();

        myWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);

        final ProgressDialog progressBar = new ProgressDialog(MainActivity.this);
        progressBar.setMessage("Please Wait While We Process Your Request");

        myWebView.loadUrl("https://murmuring-woodland-75772.herokuapp.com");

        myWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!progressBar.isShowing()) {
                    progressBar.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }
}