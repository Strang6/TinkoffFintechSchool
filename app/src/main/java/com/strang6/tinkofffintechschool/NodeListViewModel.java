package com.strang6.tinkofffintechschool;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Strang6 on 12.11.2017.
 */

public class NodeListViewModel extends AndroidViewModel {
    private LiveData<List<Node>> nodeList;
    private NodeDatabase nodeDatabase;

    public NodeListViewModel(@NonNull Application application) {
        super(application);
        nodeDatabase = NodeDatabase.getDatabase(this.getApplication());
        nodeList = nodeDatabase.getNodeDao().getAllNodes();
    }

    public LiveData<List<Node>> getNodeList() {
        return nodeList;
    }

    public void deleteNode(Node node) {
        new DeleteAsyncTask(nodeDatabase).execute(node);
    }

    public void addNode(Node node) {
        new AddAsyncTask(nodeDatabase).execute(node);
    }

    private static class DeleteAsyncTask extends AsyncTask<Node, Void, Void> {
        private NodeDatabase nodeDatabase;

        public DeleteAsyncTask(NodeDatabase nodeDatabase) {
            this.nodeDatabase = nodeDatabase;
        }

        @Override
        protected Void doInBackground(Node... nodes) {
            nodeDatabase.getNodeDao().deleteNode(nodes[0]);
            return null;
        }
    }

    private static class AddAsyncTask extends AsyncTask<Node, Void, Void> {
        private NodeDatabase nodeDatabase;

        public AddAsyncTask(NodeDatabase nodeDatabase) {
            this.nodeDatabase = nodeDatabase;
        }

        @Override
        protected Void doInBackground(Node... nodes) {
            nodeDatabase.getNodeDao().addNode(nodes[0]);
            return null;
        }
    }
}
