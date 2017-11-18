package com.strang6.tinkofffintechschool;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Strang6 on 12.11.2017.
 */

public class NodeRecyclerViewAdapter extends RecyclerView.Adapter<NodeRecyclerViewAdapter.ViewHolder> {

    private List<Node> nodeList;
    private View.OnLongClickListener longClickListener;
    private View.OnClickListener clickListener;

    public NodeRecyclerViewAdapter(List<Node> nodeList, View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
        this.nodeList = nodeList;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.node_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Node node = nodeList.get(position);
        boolean hasChildren = node.hasChildren();
        boolean hasParent = node.hasParent(nodeList);
        if (hasChildren && hasParent) {
            holder.itemView.setBackgroundColor(Color.RED);
        } else if (hasChildren) {
            holder.itemView.setBackgroundColor(Color.YELLOW);
        } else if (hasParent) {
            holder.itemView.setBackgroundColor(Color.BLUE);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.idTextView.setText(Long.toString(node.getId()));
        holder.valueTextView.setText(Integer.toString(node.getValue()));
        holder.itemView.setOnClickListener(clickListener);
        holder.itemView.setOnLongClickListener(longClickListener);
        holder.itemView.setTag(node);
    }

    @Override
    public int getItemCount() {
        return nodeList.size();
    }

    public void addItems(List<Node> nodeList) {
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
