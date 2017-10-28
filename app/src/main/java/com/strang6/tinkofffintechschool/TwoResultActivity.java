package com.strang6.tinkofffintechschool;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.Executor;

public class TwoResultActivity extends AppCompatActivity {

    private static String TAG = "myTag";

    private TextView authorTextView, dobTextView;
    private final int AUTHOR_MESSAGE = 0, DOB_MESSAGE = 1;
    private final String AUTHOR_KEY = "author", DOB_KEY = "dob";
    private String author, dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_result);

        Log.d(TAG, "onCreate");
        authorTextView = (TextView) findViewById(R.id.authorTextView);
        dobTextView = (TextView) findViewById(R.id.dobTextView);

        if (savedInstanceState != null &&
                (author = savedInstanceState.getString(AUTHOR_KEY)) != null &&
                (dob = savedInstanceState.getString(DOB_KEY)) != null) {
            authorTextView.setText(author);
            dobTextView.setText(dob);
        } else {
            loadAuthorInfoThread.start();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putString(AUTHOR_KEY, author);
        outState.putString(DOB_KEY, dob);
    }

    @Override
    protected void onStop() {
        loadAuthorInfoThread.interrupt();
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        if (author == null || dob == null) {
            loadAuthorInfoThread.start();
        }
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    private Thread loadAuthorInfoThread = new Thread() {
        private String [] authorInfo = {"Александр Сергеевич Пушкин",
                "(26 мая [6 июня] 1799, Москва — 29 января [10 февраля] 1837, Санкт-Петербург)"};
        @Override
        public void run() {
            Log.d(TAG, "run");
            if (this.isInterrupted())
                return;
            Message message = handler.obtainMessage();
            message.what = AUTHOR_MESSAGE;
            Bundle bundle = new Bundle();
            bundle.putString(AUTHOR_KEY,authorInfo[0]);
            if (this.isInterrupted())
                return;
            message.setData(bundle);
            if (this.isInterrupted())
                return;
            handler.sendMessage(message);
            if (this.isInterrupted())
                return;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (this.isInterrupted())
                return;
            message = handler.obtainMessage();
            message.what = DOB_MESSAGE;
            bundle = new Bundle();
            bundle.putString(DOB_KEY,authorInfo[1]);
            message.setData(bundle);
            if (this.isInterrupted())
                return;
            handler.sendMessage(message);
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage");
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case AUTHOR_MESSAGE:
                    author = bundle.getString(AUTHOR_KEY);
                    Log.d(TAG, "AUTHOR_MESSAGE " + author);
                    authorTextView.setText(author);
                    break;
                case DOB_MESSAGE:
                    dob = bundle.getString(DOB_KEY);
                    Log.d(TAG, "DOB_MESSAGE " + dob);
                    dobTextView.setText(dob);
                    break;
            }
        }
    };
}


