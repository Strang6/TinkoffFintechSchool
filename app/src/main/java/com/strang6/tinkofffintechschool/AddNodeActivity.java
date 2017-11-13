package com.strang6.tinkofffintechschool;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNodeActivity extends AppCompatActivity {
    private EditText valueEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_node);
        valueEditText = (EditText) findViewById(R.id.valueEditText);
    }

    public void okOnClick(View view) {
        String stringValue = valueEditText.getText().toString();
        if (stringValue.isEmpty())
            Toast.makeText(this, "Введите value", Toast.LENGTH_SHORT).show();
        else {
            NodeListViewModel viewModel = ViewModelProviders.of(this).get(NodeListViewModel.class);
            int value = Integer.parseInt(stringValue);
            viewModel.addNode(new Node(value));
            this.setResult(RESULT_OK);
            finish();
        }
    }
}
