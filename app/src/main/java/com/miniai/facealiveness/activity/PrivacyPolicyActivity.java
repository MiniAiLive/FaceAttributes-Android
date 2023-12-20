package com.miniai.facealiveness.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.miniai.facealiveness.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressDialog progressDialog; // Declare the progress dialog

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        // Find the WebView in the layout
        webView = findViewById(R.id.webView);

        // Enable JavaScript (if needed)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Initialize the progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Set the message for the progress dialog
        progressDialog.setCanceledOnTouchOutside(false); // Prevent dialog dismissal on touch outside

        // Load the privacy policy URL
        String privacyPolicyUrl = "https://www.miniai.live/privacy-police/"; // Replace with your URL
        webView.loadUrl(privacyPolicyUrl);
        progressDialog.show();
        // Set a WebView client to handle URL loading within the WebView
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                // Show the progress dialog when a page starts loading

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Hide the progress dialog when the page is fully loaded
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Go back in WebView history or finish the activity
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
