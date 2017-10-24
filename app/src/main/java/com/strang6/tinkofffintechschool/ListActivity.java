package com.strang6.tinkofffintechschool;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String[]>{
    private static String TAG = "myTag";
    private static String WORKS_KEY = "works";

    private final int WORKS_LOADER_ID = 0;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private  String [] works;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Log.d(TAG, "onCreate");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        if (savedInstanceState != null && (works = savedInstanceState.getStringArray(WORKS_KEY)) != null) {
            MyAdapter myAdapter = new MyAdapter(works);
            recyclerView.setAdapter(myAdapter);
        } else {
            Loader<String[]> loader = getSupportLoaderManager().initLoader(WORKS_LOADER_ID, null, this);
            loader.forceLoad();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
        if (works == null) {
            Loader<String[]> loader = getSupportLoaderManager().initLoader(WORKS_LOADER_ID, null, this);
            loader.forceLoad();
        }
    }

    @Override
    public Loader<String[]> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");
        Loader loader = null;
        if (id == WORKS_LOADER_ID) {
            loader = new WorksLoader(this);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {
        Log.d(TAG, "onLoadFinished");
        if (loader.getId() == WORKS_LOADER_ID) {
            works = data;
            MyAdapter myAdapter = new MyAdapter(data);
            recyclerView.setAdapter(myAdapter);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {
        Log.d(TAG, "onLoaderReset");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putStringArray(WORKS_KEY, works);
    }
}
