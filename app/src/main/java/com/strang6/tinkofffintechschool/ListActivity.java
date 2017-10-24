package com.strang6.tinkofffintechschool;

import android.content.AsyncTaskLoader;
import android.os.AsyncTask;
import android.os.Parcelable;
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
    private final int WORKS_LOADER_ID = 0;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        Loader<String[]> loader = getSupportLoaderManager().initLoader(WORKS_LOADER_ID, null, this);
        loader.forceLoad();

    }

    @Override
    public Loader<String[]> onCreateLoader(int id, Bundle args) {
        Loader loader = null;
        if (id == WORKS_LOADER_ID) {
            loader = new WorksLoader(this);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {
        if (loader.getId() == WORKS_LOADER_ID) {
            MyAdapter myAdapter = new MyAdapter(data);
            recyclerView.setAdapter(myAdapter);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        Loader loader = getSupportLoaderManager().getLoader(WORKS_LOADER_ID);
        if (loader != null) {
            loader.cancelLoad();
        }
    }
}
