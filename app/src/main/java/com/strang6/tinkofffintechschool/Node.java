package com.strang6.tinkofffintechschool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Strang6 on 12.11.2017.
 */
public class Node {
    private long id;
    private int value;
    private List<Node> children;

    public Node(long id, int value) {
        this.value = value;
        this.id = id;
        children = new ArrayList<>();
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public boolean hasParent(List<Node> nodes) {
        for (Node parent: nodes) {
            if (parent.getChildren().contains(this))
                return true;
        }
        return false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public static Node getNodeById(List<Node> nodes, long nodeId) {
        for (Node node: nodes){
            if (node.getId() == nodeId)
                return node;
        }
        return null;
    }
}