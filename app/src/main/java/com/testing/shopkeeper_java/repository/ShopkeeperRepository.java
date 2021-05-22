package com.testing.shopkeeper_java.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import com.testing.shopkeeper_java.dao.ShopkeeperDao;
import com.testing.shopkeeper_java.database.ShopkeeperDatabase;
import com.testing.shopkeeper_java.model.Shopkeeper;

import java.util.List;

public class ShopkeeperRepository {

    private ShopkeeperDao shopkeeperDao;
    private LiveData<List<Shopkeeper>> allData;

    public ShopkeeperRepository(Application application) {
        ShopkeeperDatabase shopkeeperDatabase = ShopkeeperDatabase.getInstance(application);
        shopkeeperDao = shopkeeperDatabase.shopkeeperDao();
        allData = shopkeeperDao.getAllDetail();
    }

    public void insert(Shopkeeper shopkeeper) {

        new InsertNoteAsyncTask(shopkeeperDao).execute(shopkeeper);
    }



    public void getNameList(String name) {

        new GetNameListAsyncTask(shopkeeperDao).execute(name);

    }

    public void delete(Shopkeeper shopkeeper) {

        new ShopkeeperRepository.DeleteAsyncTask(shopkeeperDao).execute(shopkeeper);

    }

    public void deleteAllNotes() {
        new ShopkeeperRepository.DeleteAllNoteAsyncTask(shopkeeperDao).execute();

    }

    public void getList() {

        new GetListAsyncTask(shopkeeperDao).execute();

    }


    private static class InsertNoteAsyncTask extends AsyncTask<Shopkeeper, Void, Void> {

        private ShopkeeperDao shopkeeperDao;

        private InsertNoteAsyncTask(ShopkeeperDao shopkeeperDao) {
            this.shopkeeperDao = shopkeeperDao;
        }

        @Override
        protected Void doInBackground(Shopkeeper... conferences) {
            shopkeeperDao.insert(conferences[0]);
            return null;
        }
    }


    private static class GetNameListAsyncTask extends AsyncTask<String, Void, Void> {

        private ShopkeeperDao shopkeeperDao;

        private GetNameListAsyncTask(ShopkeeperDao shopkeeperDao) {
            this.shopkeeperDao = shopkeeperDao;
        }


        @Override
        protected Void doInBackground(String... strings) {
           // shopkeeperDao.getNameList(strings[0]);
            shopkeeperDao.getNameList();
            return null;
        }
    }

    private static class GetListAsyncTask extends AsyncTask<String, Void, Void> {

        private ShopkeeperDao shopkeeperDao;

        private GetListAsyncTask(ShopkeeperDao shopkeeperDao) {
            this.shopkeeperDao = shopkeeperDao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            // shopkeeperDao.getNameList(strings[0]);
            shopkeeperDao.getList();
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<Shopkeeper, Void, Void> {

        private ShopkeeperDao shopkeeperDao;

        private DeleteAsyncTask(ShopkeeperDao shopkeeperDao) {
            this.shopkeeperDao = shopkeeperDao;
        }

        @Override
        protected Void doInBackground(Shopkeeper... shopkeeperDatas) {
            shopkeeperDao.delete(shopkeeperDatas[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {

        private ShopkeeperDao shopkeeperDao;

        private DeleteAllNoteAsyncTask(ShopkeeperDao shopkeeperDao) {
            this.shopkeeperDao = shopkeeperDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            shopkeeperDao.deleteAllData();
            return null;
        }
    }
}
