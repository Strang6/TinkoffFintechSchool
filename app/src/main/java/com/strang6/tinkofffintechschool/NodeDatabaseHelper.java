package com.strang6.tinkofffintechschool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Strang6 on 13.11.2017.
 */

public class NodeDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "node-database";
    private static final int DB_VERSION = 1;

    public NodeDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE node ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "value INTEGER);");
        db.execSQL("CREATE TABLE relations ("
                + "parent_id INTEGER,"
                + "child_id INTEGER,"
                + "PRIMARY KEY (parent_id, child_id),"
                + "FOREIGN KEY(parent_id) REFERENCES node(id),"
                + "FOREIGN KEY(child_id) REFERENCES node(id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
