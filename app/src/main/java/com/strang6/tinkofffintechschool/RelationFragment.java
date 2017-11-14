package com.strang6.tinkofffintechschool;


import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RelationFragment extends Fragment implements View.OnLongClickListener, LoaderManager.LoaderCallbacks<List<Node>> {
    public static final int TYPE_PARENT = 0, TYPE_CHILD = 1;
    private int type;
    private long currentNodeId;
    private RelationRecyclerViewAdapter recyclerViewAdapter;
    private static final int NODE_LOADER = 1;

    public static RelationFragment newInstance(int type, long nodeId) {
        RelationFragment relationFragment = new RelationFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putLong("nodeId", nodeId);
        relationFragment.setArguments(args);
        return relationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
        currentNodeId = getArguments().getLong("nodeId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_relation, null);
        RecyclerView relationView = view.findViewById(R.id.relationView);
        relationView.setHasFixedSize(true);
        relationView.setLayoutManager(new LinearLayoutManager(getContext()));
        relationView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewAdapter = new RelationRecyclerViewAdapter(type, new ArrayList<Node>(), null, this);
        relationView.setAdapter(recyclerViewAdapter);
        Loader<List<Node>> loader = getLoaderManager().initLoader(NODE_LOADER, null, this);
        loader.forceLoad();
        return view;
    }


    @Override
    public boolean onLongClick(View view) {
        long selectedNodeId = ((Node)view.getTag()).getId();
        long childId = type == TYPE_PARENT ? currentNodeId : selectedNodeId;
        long parentId = type == TYPE_PARENT ? selectedNodeId : currentNodeId;
        DialogFragment dialog;
        int type;
        ColorDrawable colorDrawable = (ColorDrawable)view.getBackground();
        if (colorDrawable!=null && colorDrawable.getColor() == Color.GREEN) {
            type = AlertDialogFragment.TYPE_DELETE;
        } else {
            type = AlertDialogFragment.TYPE_ADD;
        }
        dialog = AlertDialogFragment.newInstance(type, parentId, childId);
        dialog.show(getFragmentManager(), "dialog");
        return true;
    }

    @Override
    public Loader<List<Node>> onCreateLoader(int i, Bundle bundle) {
        return new NodesLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Node>> loader, List<Node> nodes) {
        Node node = Node.getNodeById(nodes, currentNodeId);
        nodes.remove(node);
        nodes.remove(node);
        recyclerViewAdapter.setNodes(node, nodes);
    }

    @Override
    public void onLoaderReset(Loader<List<Node>> loader) {

    }
}
