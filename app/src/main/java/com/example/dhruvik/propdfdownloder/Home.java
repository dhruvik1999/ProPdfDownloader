package com.example.dhruvik.propdfdownloder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Home extends AppCompatActivity {

    EditText name;
    Button find;
    Thread thread;
    Handler handler;

    static ArrayList<String> book_url;
    static ArrayList<String> book_img;
    static ArrayList<String> boog_get_url;
    String result="";
    Document document = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initiallization();

        Runnable r = new Runnable() {
            @Override
            public void run() {

                book_url.clear();
                book_img.clear();
                boog_get_url.clear();
                result = "";
                try {
                    document = Jsoup.connect("http://gen.lib.rus.ec/search.php?req="+name.getText().toString()+"&lg_topic=libgen&open=0&view=simple&res=25&phrase=0&column=def").userAgent("Chrome").timeout(5000).get();
                    Elements link = document.select("a[title='Gen.lib.rus.ec']");

                    for(Element l : link){
                        String t="";
                        t = l.attr("href");
                        book_url.add(t);
                        get_image_url(t);
                        Log.i("LINK",t);

                        Thread.sleep(1000);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendEmptyMessage(0);
                }
            }
        };

        thread = new Thread(r);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        };

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),name.getText().toString(), Toast.LENGTH_SHORT ).show();
                thread.start();
            }
        });
    }

    private void initiallization(){
        name = (EditText)this.findViewById(R.id.name);
        find = (Button)this.findViewById(R.id.find);
        book_url = new ArrayList<String>();
        boog_get_url = new ArrayList<String>();
        book_img = new ArrayList<String>();
    }

    public void get_image_url(String url){

        try {
            Connection con = Jsoup.connect(url).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").timeout(10000);
            Connection.Response resp = con.execute();
            Document doc = null;
            if (resp.statusCode() == 200) {
                doc = con.get();
            }

            Element link = doc.select("a").first();
            String h =link.attr("href");
                    boog_get_url.add(h);

            Element image = doc.select("img").first();
            String s = image.absUrl("src");
            book_img.add(s);
            s ="";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
