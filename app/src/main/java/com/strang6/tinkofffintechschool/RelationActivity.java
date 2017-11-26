package com.strang6.tinkofffintechschool;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class RelationActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Node>> {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private long nodeId;
    private List<Node> nodes;
    private static final int NODE_LOADER = 1;
    private List<OnNodesLoadListener> listeners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);
        nodeId = getIntent().getExtras().getLong("nodeId");
        setTitle("node id = " + nodeId);
        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new RelationFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        listeners = new ArrayList<>();
        Loader<List<Node>> loader = getSupportLoaderManager().initLoader(NODE_LOADER, null, this);
        loader.forceLoad();
    }

    public void addOnNodesLoadListener(OnNodesLoadListener listener) {
        listeners.add(listener);
        if (nodes != null)
            listener.onLoadNodesFinished(nodes);
    }

    @Override
    public Loader<List<Node>> onCreateLoader(int id, Bundle args) {
        return new NodesLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Node>> loader, List<Node> data) {
        nodes = data;
        for (OnNodesLoadListener listener : listeners) {
            listener.onLoadNodesFinished(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Node>> loader) {
    }

    private class RelationFragmentPagerAdapter extends FragmentPagerAdapter {

        public RelationFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            if (position == 0)
                fragment = RelationFragment.newInstance(RelationFragment.TYPE_PARENT, nodeId);
            else
                fragment = RelationFragment.newInstance(RelationFragment.TYPE_CHILD, nodeId);
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "Родители";
            else
                return "Дети";
        }
    }
}
