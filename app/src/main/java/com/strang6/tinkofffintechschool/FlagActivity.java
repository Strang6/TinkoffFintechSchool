package com.strang6.tinkofffintechschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Strang6 on 12.10.2017.
 */

public class FlagActivity extends AppCompatActivity {
    public void onClick(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int id = radioGroup.getCheckedRadioButtonId();
        if (id != -1) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.redButton:
                    intent = new Intent(this, RedActivity.class);
                    break;
                case R.id.greenButton:
                    intent = new Intent(this, GreenActivity.class);
                    break;
                case R.id.blueButton:
                    intent = new Intent(this, BlueActivity.class);
                    break;
            }

            switch (id) {
                case R.id.noFlag:
                    break;
                case R.id.newTask:
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;
                case R.id.singleTop:
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    break;
                case R.id.clearTop:
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    break;
                case R.id.noHistory:
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    break;
                case R.id.reorderToFront:
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    break;
                case R.id.clearTask:
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    break;
                case R.id.taskOnHome:
                    intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                    break;
            }
            startActivity(intent);
        } else
            Toast.makeText(this, "Выберите флаг", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("FlagActivity", this.getClass().toString() + " " + this.hashCode());
        super.onNewIntent(intent);
    }
}
