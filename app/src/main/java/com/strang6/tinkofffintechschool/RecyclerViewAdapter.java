package com.strang6.tinkofffintechschool;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Strang6 on 12.11.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Node> nodeList;
    private View.OnLongClickListener longClickListener;

    public RecyclerViewAdapter(List<Node> nodeList, View.OnLongClickListener longClickListener) {
        this.nodeList = nodeList;
        this.longClickListener = longClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.node_text_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Node node = nodeList.get(position);
        holder.valueTextView.setText(Integer.toString(node.getValue()));
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
        private TextView valueTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            valueTextView = (TextView)itemView.findViewById(R.id.valueTextView);
        }
    }
}
