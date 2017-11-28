package com.strang6.tinkofffintechschool;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private MainPresenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new Adapter(new ArrayList<News>());
        recyclerView.setAdapter(adapter);

        presenter = new MainPresenter();
        presenter.bindView(this);
        presenter.loadNews();

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadNews();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unbindView();
    }

    public void setNewsList(List<News> newsList) {
        swipeRefreshLayout.setRefreshing(false);
        adapter.setNewsList(newsList);
    }

    public void loadingError() {
        Toast.makeText(this, "Loading error", Toast.LENGTH_SHORT).show();
    }
}
