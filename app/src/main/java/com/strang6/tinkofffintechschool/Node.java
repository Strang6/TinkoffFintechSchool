package com.strang6.tinkofffintechschool;

import java.util.List;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Strang6 on 12.11.2017.
 */
@Entity
public class Node {
    @PrimaryKey
    private int value;
    //private List<Node> children;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

//    public List<Node> getChildren() {
//        return children;
//    }

    public void setValue(int value) {
        this.value = value;
    }

    //public void setChildren(List<Node> children) {
   //     this.children = children;
   // }
}