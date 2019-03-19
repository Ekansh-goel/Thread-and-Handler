package com.example.threadandhandler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    long endtime;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ((TextView) findViewById(R.id.textView)).setText("button is clicked");
            Bundle bundle = msg.getData();
            String data = bundle.getString("data_key");
            ((TextView) findViewById(R.id.textView)).setText("data added");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void click(View view) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                endtime = System.currentTimeMillis() + 10000;
                while (System.currentTimeMillis() < endtime) {
                    synchronized (this) {
                        try {
                            wait(endtime - System.currentTimeMillis());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                handler.sendEmptyMessage(0);

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("data_key", "message from data");
        message.setData(bundle);
        handler.sendMessage(message);
    /*public void click(View view) {
        endtime = System.currentTimeMillis() + 10000;
        while (System.currentTimeMillis() < endtime) {
            synchronized (this) {
                try {
                    wait(endtime - System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        ((TextView) findViewById(R.id.textView)).setText("button is clicked");
    }*/
    }
}