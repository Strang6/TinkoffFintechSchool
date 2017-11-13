package com.strang6.tinkofffintechschool;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Strang6 on 12.11.2017.
 */

@Dao
public interface NodeDao {
    @Insert(onConflict = REPLACE)
    public void addNode(Node node);

    @Delete
    public void deleteNode(Node node);

    @Query("SELECT * FROM Node")
    public LiveData<List<Node>> getAllNodes();

    @Query("SELECT * FROM Node WHERE value = :value")
    public Node getNodeByValue(String value);
}
