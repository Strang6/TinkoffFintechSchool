package com.strang6.tinkofffintechschool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RelationFragment extends Fragment implements View.OnClickListener, OnNodesLoadListener {
    public static final int TYPE_PARENT = 0, TYPE_CHILD = 1;
    private int type;
    private long currentNodeId, selectedNodeId;
    private RelationRecyclerViewAdapter recyclerViewAdapter;
    private static final int REQ_ADD = 5, REQ_DELETE = 6;
    private static final String TAG = "MyTag";

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
        Log.d(TAG, "onCreate type " + type + " currentNodeId " + currentNodeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_relation, null);
        RecyclerView relationView = view.findViewById(R.id.relationView);
        relationView.setHasFixedSize(true);
        relationView.setLayoutManager(new LinearLayoutManager(getContext()));
        relationView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewAdapter = new RelationRecyclerViewAdapter(new ArrayList<Node>(), new ArrayList<Node>(), this);
        relationView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((RelationActivity) getActivity()).addOnNodesLoadListener(this);
    }

    @Override
    public void onClick(View view) {
        selectedNodeId = ((Node) view.getTag()).getId();
        long childId, parentId;
        if (type == TYPE_PARENT) {
            childId = currentNodeId;
            parentId = selectedNodeId;
        } else {
            childId = selectedNodeId;
            parentId = currentNodeId;
        }
        DialogFragment dialog;
        int type;
        int req;
        ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();
        if (colorDrawable != null && colorDrawable.getColor() == Color.GREEN) {
            type = AlertDialogFragment.TYPE_DELETE;
            req = REQ_DELETE;
        } else {
            type = AlertDialogFragment.TYPE_ADD;
            req = REQ_ADD;
        }
        dialog = AlertDialogFragment.newInstance(type, parentId, childId);
        dialog.setTargetFragment(this, req);
        dialog.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            getActivity().setResult(Activity.RESULT_OK);
            switch (requestCode) {
                case REQ_ADD:
                    recyclerViewAdapter.addRelation(selectedNodeId);
                    break;
                case REQ_DELETE:
                    recyclerViewAdapter.deleteRelation(selectedNodeId);
                    break;
            }
        }
    }

    @Override
    public void onLoadNodesFinished(List<Node> nodes) {
        Log.d(TAG, "onLoadFinished type " + type + " currentNodeId " + currentNodeId);
        Log.d(TAG, "nodes.size " + nodes.size());
        Node currentNode = Node.getNodeById(nodes, currentNodeId);
        List<Node> nodesWithRelation, nodesWithoutRelation;
        nodesWithoutRelation = new ArrayList<>(nodes);
        nodesWithoutRelation.remove(currentNode);
        if (type == TYPE_CHILD) {
            nodesWithRelation = currentNode.getChildren();
        } else {
            nodesWithRelation = currentNode.getParents(nodesWithoutRelation);
        }
        nodesWithoutRelation.removeAll(nodesWithRelation);
        recyclerViewAdapter.setNodes(nodesWithRelation, nodesWithoutRelation);
    }

}
