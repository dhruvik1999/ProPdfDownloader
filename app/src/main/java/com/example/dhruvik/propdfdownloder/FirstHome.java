package com.example.dhruvik.propdfdownloder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class FirstHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_home);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                startActivity(new Intent(getApplicationContext(),Home.class));
            }
        };

        if(check_internet_connection()){
            //code for wait for 3 sec and start home.


            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                      handler.sendEmptyMessage(0);
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }else{
            //code for inter net connection is lost.
        }
    }

    public boolean check_internet_connection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
