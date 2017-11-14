package com.strang6.tinkofffintechschool;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnLongClickListener, LoaderManager.LoaderCallbacks<List<Node>> {

    private RecyclerView nodeRecyclerView;
    private NodeRecyclerViewAdapter recyclerViewAdapter;
    private static final int NODE_LOADER = 1, ADD_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nodeRecyclerView = findViewById(R.id.nodeRecyclerView);
        nodeRecyclerView.setHasFixedSize(true);
        nodeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        nodeRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerViewAdapter = new NodeRecyclerViewAdapter(new ArrayList<Node>(), this, this);
        nodeRecyclerView.setAdapter(recyclerViewAdapter);

        Loader<List<Node>> loader = getSupportLoaderManager().initLoader(NODE_LOADER, null, this);
        loader.forceLoad();
    }

    @Override
    public boolean onLongClick(View view) {
        Node node = (Node) view.getTag();
        NodeDatabase nodeDatabase = new NodeDatabase(this);
        nodeDatabase.deleteNode(node);
        Loader<List<Node>> loader = getSupportLoaderManager().initLoader(NODE_LOADER, null, this);
        loader.forceLoad();
        return true;
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, RelationActivity.class);
        Node node = (Node) view.getTag();
        intent.putExtra("nodeId", node.getId());
        startActivity(intent);
    }

    public void addNodeOnClick(View view) {
        startActivityForResult(new Intent(this, AddNodeActivity.class), ADD_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CODE) {
            if (resultCode == RESULT_OK) {
                Loader<List<Node>> loader = getSupportLoaderManager().initLoader(NODE_LOADER, null, this);
                loader.forceLoad();
            }
        }
    }

    @Override
    public Loader<List<Node>> onCreateLoader(int i, Bundle bundle) {
        return new NodesLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Node>> loader, List<Node> nodeList) {
        recyclerViewAdapter.addItems(nodeList);
    }

    @Override
    public void onLoaderReset(Loader<List<Node>> loader) {
    }
}
