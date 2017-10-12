package com.strang6.tinkofffintechschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.BatchUpdateException;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE = 0;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);

        Button flagsButton = (Button) findViewById(R.id.flagsButton);
        flagsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GreenActivity.class);
                startActivity(intent);
            }
        });

    }


    public void onClickSharing(View view) {
        String text = editText.getText().toString();
        if (!text.isEmpty()) {
            Intent intent;
            switch (view.getId()) {
                case R.id.messengerButton:
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, text);
                    intent.setType("text/plain");
                    startActivity(intent);
                    break;
                case R.id.activityButton:
                    intent = new Intent(this, DisplayActivity.class);
                    intent.putExtra("text", text);
                    startActivityForResult(intent, REQ_CODE);
                    break;
            }
        } else
            Toast.makeText(this, "Введите текст", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE) {
            String result = "result";
            if (resultCode == RESULT_OK)
                result = "Ok";
            else if (resultCode == RESULT_CANCELED)
                result = "Canceled";
            editText.setText(result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
