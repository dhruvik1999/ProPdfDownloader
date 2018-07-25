package com.example.dhruvik.propdfdownloder;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.loading.book.BookLoading;

public class ProgressBarView extends AppCompatActivity {

     TextView per;

     public void persent(int per1){
     }
    BookLoading bookLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar_view);
        per = (TextView)this.findViewById(R.id.fg);


        bookLoading = (BookLoading)this.findViewById(R.id.bookLoad);
        bookLoading.start();

        new CountDownTimer(100000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                per.setText(String.valueOf(Home.downloaded_book)+" Books...");
            }

            @Override
            public void onFinish() {

            }
        }.start();



    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
