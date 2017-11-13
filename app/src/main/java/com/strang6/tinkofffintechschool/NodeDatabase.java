package com.strang6.tinkofffintechschool;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Strang6 on 12.11.2017.
 */
@Database(entities = {Node.class}, version = 1)
public abstract class NodeDatabase extends RoomDatabase {

    private static NodeDatabase INSTANCE;

    public static NodeDatabase getDatabase(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, NodeDatabase.class, "node-database").build();
        }
        return INSTANCE;
    }

    public abstract NodeDao getNodeDao();
}
