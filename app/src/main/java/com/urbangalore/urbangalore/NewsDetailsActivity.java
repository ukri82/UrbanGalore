package com.urbangalore.urbangalore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.urbangalore.logging.L;


public class NewsDetailsActivity extends ActionBarActivity
{

    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
            /*Log.v("MyWebViewClient", url);
            Log.v("MyWebViewClient", Uri.parse(url).getHost());
            if (Uri.parse(url).getHost().equals("m.thehindu.com") || Uri.parse(url).getHost().equals("m.timesofindia.com")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;*/
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            myDetailsView.setVisibility(View.VISIBLE);
            myProgressDlg.dismiss();
            super.onPageFinished(view, url);
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
        {
            myDetailsView.setVisibility(View.VISIBLE);
            myProgressDlg.dismiss();

            showError();
        }

        void showError()
        {
            final String mimeType = "text/html";
            final String encoding = "UTF-8";
            String html = "<br /><br /> Oops ! There seems to be a problem in getting the news details :( <br/> Better try another one :)";

            myDetailsView.loadDataWithBaseURL("", html, mimeType, encoding, "");
        }
    }

    private WebView myDetailsView;
    private Toolbar myToolbar;
    ProgressDialog myProgressDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        setupToolbar();


        myDetailsView = (WebView) findViewById(R.id.news_details_view);
        WebSettings webSettings = myDetailsView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myDetailsView.setWebViewClient(new MyWebViewClient());

        myDetailsView.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        String aURL = intent.getStringExtra("NEWS_ITEM_URL");
        myDetailsView.loadUrl(aURL);

        myProgressDlg = new ProgressDialog(this);
        myProgressDlg.setTitle("Loading");
        myProgressDlg.setMessage("Wait while loading...");
        myProgressDlg.show();
    }

    private void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setLogo(R.drawable.uglogotinyw);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        if (android.R.id.home == id) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
