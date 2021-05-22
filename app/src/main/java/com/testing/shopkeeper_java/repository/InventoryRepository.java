package com.testing.shopkeeper_java.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import com.testing.shopkeeper_java.dao.InventoryDao;
import com.testing.shopkeeper_java.dao.ShopkeeperDao;
import com.testing.shopkeeper_java.database.InventoryDatabase;
import com.testing.shopkeeper_java.model.InventoryData;

import java.util.List;

public class InventoryRepository {
    private InventoryDao inventoryDao;
    private LiveData<List<InventoryData>> allData;

    public InventoryRepository(Application application) {
        InventoryDatabase inventoryDatabase = InventoryDatabase.getInstance(application);
        inventoryDao = inventoryDatabase.inventoryDao();
        allData = inventoryDao.getAllDetail();
    }

    public void insert(InventoryData inventoryData) {

        new InventoryRepository.InsertNoteAsyncTask(inventoryDao).execute(inventoryData);
    }

    public void update(InventoryData inventoryData) {
        new InventoryRepository.UpdateNoteAsyncTask(inventoryDao).execute(inventoryData);

    }



    public void getNameIdList(String id, String name) {
        new InventoryRepository.GetNameIdDataAsyncTask(inventoryDao).execute(id,name);

    }
//
    public void getNameList(String name) {

        new InventoryRepository.GetNameListAsyncTask(inventoryDao).execute(name);

    }
    public void deleteAllNotes() {
        new InventoryRepository.DeleteAllNoteAsyncTask(inventoryDao).execute();

    }
    public void getList() {

        new InventoryRepository.GetListAsyncTask(inventoryDao).execute();

    }
//    //public LiveData<List<InventoryDatabase>> getAllData() {
//        return allData;
//    }

    private static class InsertNoteAsyncTask extends AsyncTask<InventoryData, Void, Void> {

        private InventoryDao inventoryDao;

        private InsertNoteAsyncTask(InventoryDao inventoryDao) {
            this.inventoryDao = inventoryDao;
        }

        @Override
        protected Void doInBackground(InventoryData... inventoryData) {
            inventoryDao.insert(inventoryData[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<InventoryData, Void, Void> {

        private InventoryDao inventoryDao;

        private UpdateNoteAsyncTask(InventoryDao inventoryDao) {
            this.inventoryDao = inventoryDao;
        }

        @Override
        protected Void doInBackground(InventoryData... inventoryData) {
            inventoryDao.update(inventoryData[0]);
            return null;
        }
    }





    private static class GetNameListAsyncTask extends AsyncTask<String, Void, Void> {

        private InventoryDao inventoryDao;

        private GetNameListAsyncTask(InventoryDao inventoryData) {
            this.inventoryDao = inventoryDao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            inventoryDao.getRoomNameList(strings[0]);
            return null;
        }
    }

    private static class GetNameIdDataAsyncTask extends AsyncTask<String, Void, Void> {

        private InventoryDao inventoryDao;

        private GetNameIdDataAsyncTask(InventoryDao inventoryData) {
            this.inventoryDao = inventoryDao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            inventoryDao.getRoomIdList(strings[0],strings[0]);
            return null;
        }
    }
    private static class GetListAsyncTask extends AsyncTask<String, Void, Void> {

        private InventoryDao inventoryDao;

        private GetListAsyncTask(InventoryDao inventoryDao) {
            this.inventoryDao = inventoryDao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            // shopkeeperDao.getNameList(strings[0]);
            inventoryDao.getList();
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {

        private InventoryDao inventoryDao;

        private DeleteAllNoteAsyncTask(InventoryDao inventoryDao) {
            this.inventoryDao = inventoryDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            inventoryDao.deleteAllData();
            return null;
        }
    }

}
