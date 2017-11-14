package com.strang6.tinkofffintechschool;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Strang6 on 14.11.2017.
 */

public class NodesLoader extends AsyncTaskLoader<List<Node>> {

    public NodesLoader(Context context) {
        super(context);
    }

    @Override
    public List<Node> loadInBackground() {
        NodeDatabase nodeDatabase = new NodeDatabase(this.getContext());
        return nodeDatabase.getAllNodes();
    }
}