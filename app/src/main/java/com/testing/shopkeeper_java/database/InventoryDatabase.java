package com.testing.shopkeeper_java.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.testing.shopkeeper_java.dao.InventoryDao;
import com.testing.shopkeeper_java.model.InventoryData;

@Database(entities = {InventoryData.class}, version = 2, exportSchema = false)
//@TypeConverters(dataConvertor.class)
public abstract class InventoryDatabase extends RoomDatabase {

    private static InventoryDatabase instance;

    public abstract InventoryDao inventoryDao();

    private static final String DB_NAME = "calendarPopup_db";

    public static synchronized InventoryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    InventoryDatabase.class, DB_NAME).fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InventoryDatabase.PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private InventoryDao inventoryDao;

        private PopulateDbAsyncTask(InventoryDatabase db) {
            inventoryDao = db.inventoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            conferenceDao.insert(new Conference("hello","today","03:09"));
//            conferenceDao.insert(new Conference("meeting1","yesterday","01:00"));

            return null;
        }
    }
}
