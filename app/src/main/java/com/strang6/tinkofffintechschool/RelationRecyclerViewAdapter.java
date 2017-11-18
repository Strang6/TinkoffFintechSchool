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

    private List<Node> nodesWithRelation, nodesWithoutRelation;
    private View.OnClickListener clickListener;

    public RelationRecyclerViewAdapter(List<Node> nodesWithRelation, List<Node> nodesWithoutRelation, View.OnClickListener clickListener) {
        this.clickListener = clickListener;
        this.nodesWithoutRelation = nodesWithoutRelation;
        this.nodesWithRelation = nodesWithRelation;
    }

    @Override
    public RelationRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RelationRecyclerViewAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.node_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RelationRecyclerViewAdapter.ViewHolder holder, int position) {
        Node node;
        if (position < nodesWithRelation.size()) {
            node = nodesWithRelation.get(position);
            holder.itemView.setBackgroundColor(Color.GREEN);
        } else {
            node = nodesWithoutRelation.get(position - nodesWithRelation.size());
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.idTextView.setText(Long.toString(node.getId()));
        holder.valueTextView.setText(Integer.toString(node.getValue()));
        holder.itemView.setOnClickListener(clickListener);
        holder.itemView.setTag(node);
    }

    @Override
    public int getItemCount() {
        return nodesWithRelation.size() + nodesWithoutRelation.size();
    }

    public void setNodes(List<Node> nodesWithRelation, List<Node> nodesWithoutRelation) {
        this.nodesWithRelation = nodesWithRelation;
        this.nodesWithoutRelation = nodesWithoutRelation;
        notifyDataSetChanged();
    }

    public void addRelation(long nodeId) {
        Node node = Node.getNodeById(nodesWithoutRelation, nodeId);
        nodesWithoutRelation.remove(node);
        nodesWithRelation.add(node);
        notifyDataSetChanged();
    }

    public void deleteRelation(long nodeId) {
        Node node = Node.getNodeById(nodesWithRelation, nodeId);
        nodesWithRelation.remove(node);
        nodesWithoutRelation.add(node);
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