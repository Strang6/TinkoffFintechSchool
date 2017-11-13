package com.strang6.tinkofffintechschool;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener{

    private RecyclerView nodeRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private NodeListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nodeRecyclerView = findViewById(R.id.nodeRecyclerView);
        nodeRecyclerView.setHasFixedSize(true);
        nodeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        nodeRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<Node>(), this);
        nodeRecyclerView.setAdapter(recyclerViewAdapter);

        viewModel = ViewModelProviders.of(this).get(NodeListViewModel.class);
        viewModel.getNodeList().observe(this, new Observer<List<Node>>() {
            @Override
            public void onChanged(@Nullable List<Node> nodeList) {
                recyclerViewAdapter.addItems(nodeList);
            }
        });
    }

    @Override
    public boolean onLongClick(View view) {
        Node node = (Node) view.getTag();
        viewModel.deleteNode(node);
        return true;
    }


    public void addNodeOnClick(View view) {
        startActivity(new Intent(this, AddNodeActivity.class));
    }
}
