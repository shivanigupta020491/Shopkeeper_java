package com.testing.shopkeeper_java.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.testing.shopkeeper_java.dao.ShopkeeperDao;
import com.testing.shopkeeper_java.model.Shopkeeper;

@Database(entities = {Shopkeeper.class}, version = 2, exportSchema = false)
public abstract class ShopkeeperDatabase extends RoomDatabase {

    private static ShopkeeperDatabase instance;

    public abstract ShopkeeperDao shopkeeperDao();

    private static final String DB_NAME = "shopkeeper_db";

    public static synchronized ShopkeeperDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ShopkeeperDatabase.class, DB_NAME).fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ShopkeeperDao shopkeeperDao;

        private PopulateDbAsyncTask(ShopkeeperDatabase db) {
            shopkeeperDao = db.shopkeeperDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            conferenceDao.insert(new Conference("hello","today","03:09"));
//            conferenceDao.insert(new Conference("meeting1","yesterday","01:00"));

            return null;
        }
    }
}
