package com.example.dhruvik.propdfdownloder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class WebBook extends AppCompatActivity {

    WebView webView;
    Button next;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_book);

        webView = (WebView)this.findViewById(R.id.web);
        next = (Button)this.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(Home.book_url.get(i));
                i++;
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }
}

class ProxyAuthenticator extends Authenticator {
    private String userName, password;

    public ProxyAuthenticator(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password.toCharArray());
    }
}