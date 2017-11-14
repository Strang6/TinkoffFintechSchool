package com.strang6.tinkofffintechschool;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Strang6 on 14.11.2017.
 */

public class RelationRecyclerViewAdapter extends RecyclerView.Adapter<RelationRecyclerViewAdapter.ViewHolder> {

    private List<Node> nodeList;
    private View.OnLongClickListener clickListener;
    private int type;
    private Node currentNode;

    public RelationRecyclerViewAdapter(int type, List<Node> nodeList, Node node, View.OnLongClickListener clickListener) {
        this.type = type;
        this.currentNode = node;
        this.nodeList = nodeList;
        this.clickListener = clickListener;
    }

    @Override
    public RelationRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RelationRecyclerViewAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.node_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RelationRecyclerViewAdapter.ViewHolder holder, int position) {
        Node node = nodeList.get(position);
        if (type == RelationFragment.TYPE_CHILD && currentNode.getChildren().contains(node) ||
                type == RelationFragment.TYPE_PARENT && node.getChildren().contains(currentNode)) {
                holder.itemView.setBackgroundColor(Color.GREEN);
        }
        holder.idTextView.setText(Long.toString(node.getId()));
        holder.valueTextView.setText(Integer.toString(node.getValue()));
        holder.itemView.setOnLongClickListener(clickListener);
        holder.itemView.setTag(node);
    }

    @Override
    public int getItemCount() {
        return nodeList.size();
    }

    public void setNodes(Node node, List<Node> nodeList) {
        this.currentNode = node;
        this.nodeList = nodeList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView idTextView, valueTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            valueTextView = itemView.findViewById(R.id.valueTextView);
        }
    }
}