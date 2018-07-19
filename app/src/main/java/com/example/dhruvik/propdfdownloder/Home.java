package com.example.dhruvik.propdfdownloder;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    EditText name;
    Button find;
    TextView data;
    Thread thread;
    Handler handler;
    boolean lock = false;

    static ArrayList<String> book_url;
    static ArrayList<String> book_img;
    static ArrayList<String> boog_get_url;
    String result="";
    int counter = 0;
    Document document = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initiallization();

        Runnable r = new Runnable() {
            @Override
            public void run() {

                try {
                    document = Jsoup.connect("http://gen.lib.rus.ec/search.php?req="+name.getText()+"&lg_topic=libgen&open=0&view=simple&res=25&phrase=0&column=def").get();
                    Elements link = document.select("a[title='Gen.lib.rus.ec']");

                    for(Element l : link){
                        book_url.add(l.attr("href"));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    for(int i=0;i<book_url.size();i++){
                        if(i==0){
                            lock = false;
                        }
                        if (i==book_url.size()-1){
                            lock = true;
                        }
                        get_image_url(book_url.get(i));
                    }

                }
            }
        };

        thread = new Thread(r);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                data.setText(result);
                Log.i("J",result);
                //startActivity(new Intent(getApplicationContext(),Books.class));
            }
        };

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),name.getText().toString(),Toast.LENGTH_SHORT ).show();
                thread.start();
            }
        });
    }

    private void initiallization(){
        name = (EditText)this.findViewById(R.id.name);
        find = (Button)this.findViewById(R.id.find);
        data = (TextView)this.findViewById(R.id.data);
        book_url = new ArrayList<String>();
        boog_get_url = new ArrayList<String>();
        book_img = new ArrayList<String>();
    }

    public void get_image_url(String url){

        try {
            Document document = Jsoup.connect(url).get();
            Element link = document.select("a").first();

            String h =link.attr("href");
                    boog_get_url.add(h);
                    result = result + "\n\n\n" + h;

            Element image = document.select("img").first();
            String s = image.absUrl("src");
            result = result + "\n" + s ;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(lock) {
                handler.sendEmptyMessage(0);
            }
        }
    }

}
