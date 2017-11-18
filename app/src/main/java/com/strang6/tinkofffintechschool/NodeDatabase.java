package com.strang6.tinkofffintechschool;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Strang6 on 12.11.2017.
 */

public class NodeDatabase {
    private static final String NODE_TABLE = "node", COLUMN_ID = "id", COLUMN_VALUE = "value",
            RELATIONS_TABLE = "relations", COLUMN_PARENT_ID = "parent_id", COLUMN_CHILD_ID = "child_id";

    private NodeDatabaseHelper databaseHelper;

    public NodeDatabase(Context context) {
        databaseHelper = new NodeDatabaseHelper(context);
    }

    public void addNode(int value) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_VALUE, value);
        db.insert(NODE_TABLE, null, contentValues);
        databaseHelper.close();
    }

    public void deleteNode(Node node) {
        long id = node.getId();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(RELATIONS_TABLE,
                COLUMN_PARENT_ID + "=" + id + " OR "
                        + COLUMN_CHILD_ID + "=" + id, null);
        db.delete(NODE_TABLE, COLUMN_ID + "=" + node.getId(), null);
        databaseHelper.close();
    }

    public void deleteRelation(long parentId, long childId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(RELATIONS_TABLE,
                COLUMN_PARENT_ID + "=" + parentId +
                        " AND " + COLUMN_CHILD_ID + "=" + childId
                ,null);
        databaseHelper.close();
    }

    public void addRelation(long parentId, long childId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PARENT_ID, parentId);
        contentValues.put(COLUMN_CHILD_ID, childId);
        db.insert(RELATIONS_TABLE, null, contentValues);
        databaseHelper.close();
    }

    public List<Node> getAllNodes() {
        List<Node> nodes = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor nodeCursor = db.query(NODE_TABLE, null, null, null,
                null, null, null);
        if (nodeCursor.moveToFirst()) {
            int idIndex = nodeCursor.getColumnIndex(COLUMN_ID);
            int valueIndex = nodeCursor.getColumnIndex(COLUMN_VALUE);
            do {
                long id = nodeCursor.getLong(idIndex);
                int value = nodeCursor.getInt(valueIndex);
                nodes.add(new Node(id, value));
            } while (nodeCursor.moveToNext());

            Cursor relationsCursor = db.query(RELATIONS_TABLE, null, null,
                    null, null, null, null);
            if (relationsCursor.moveToFirst()) {
                int parentIdIndex = relationsCursor.getColumnIndex(COLUMN_PARENT_ID);
                int childIdIndex = relationsCursor.getColumnIndex(COLUMN_CHILD_ID);
                do {
                    long parentId = relationsCursor.getLong(parentIdIndex);
                    long childId = relationsCursor.getLong(childIdIndex);
                    Node.getNodeById(nodes, parentId).addChild(Node.getNodeById(nodes, childId));
                } while (relationsCursor.moveToNext());
            }
            relationsCursor.close();
        }
        nodeCursor.close();
        databaseHelper.close();

        return nodes;
    }
}
