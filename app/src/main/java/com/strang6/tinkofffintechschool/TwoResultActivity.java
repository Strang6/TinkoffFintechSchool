package com.strang6.tinkofffintechschool;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TwoResultActivity extends AppCompatActivity {

    private TextView authorTextView, dobTextView;
    private final int AUTHOR_MESSAGE = 0, DOB_MESSAGE = 1;
    private final String AUTHOR_KEY = "author", DOB_KEY = "dob";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_result);

        authorTextView = (TextView) findViewById(R.id.authorTextView);
        dobTextView = (TextView) findViewById(R.id.dobTextView);

        if (savedInstanceState != null) {
            authorTextView.setText(savedInstanceState.getString(AUTHOR_KEY));
            dobTextView.setText(savedInstanceState.getString(DOB_KEY));
        } else {
            Thread thread1 = new Thread(loadAuthor);
            thread1.start();
            Thread thread2 = new Thread(loadDOB);
            thread2.start();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(AUTHOR_KEY, authorTextView.getText().toString());
        outState.putString(DOB_KEY, dobTextView.getText().toString());
    }

    Runnable loadAuthor = new Runnable() {
        private String author = "Александр Сергеевич Пушкин";
        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = handler.obtainMessage();
            message.what = AUTHOR_MESSAGE;
            Bundle bundle = new Bundle();
            bundle.putString(AUTHOR_KEY,author);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    Runnable loadDOB = new Runnable() {
        private String dob = "(26 мая [6 июня] 1799, Москва — 29 января [10 февраля] 1837, Санкт-Петербург)";

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = handler.obtainMessage();
            message.what = DOB_MESSAGE;
            Bundle bundle = new Bundle();
            bundle.putString(DOB_KEY, dob);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case AUTHOR_MESSAGE:
                    authorTextView.setText(bundle.getString(AUTHOR_KEY));
                    break;
                case DOB_MESSAGE:
                    dobTextView.setText(bundle.getString(DOB_KEY));
                    break;
            }
        }
    };
}


