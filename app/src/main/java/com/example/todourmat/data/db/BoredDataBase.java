package com.example.todourmat.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todourmat.data.local.BoredDao;
import com.example.todourmat.model.BoredAction;

@Database(
        entities = { BoredAction.class },
        version =  BoredDataBase.VERSION,
        exportSchema = false
)

public abstract class BoredDataBase extends RoomDatabase {
    public final static int VERSION = 1;

    public abstract BoredDao boredDao();
}
