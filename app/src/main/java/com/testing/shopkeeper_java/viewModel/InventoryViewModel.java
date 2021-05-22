package com.testing.shopkeeper_java.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.testing.shopkeeper_java.dao.InventoryDao;
import com.testing.shopkeeper_java.database.InventoryDatabase;
import com.testing.shopkeeper_java.model.InventoryData;
import com.testing.shopkeeper_java.repository.InventoryRepository;

import java.util.List;

public class InventoryViewModel extends AndroidViewModel {

    private InventoryRepository repository;
    private LiveData<List<InventoryDatabase>> allData;
    public InventoryDao inventoryDao;

    public InventoryViewModel(@NonNull Application application) {
        super(application);
        repository = new InventoryRepository(application);
       // allData = repository.getAllData();
        inventoryDao = InventoryDatabase.getInstance(application).inventoryDao();
    }

    public void insert(InventoryData inventoryData) {
        repository.insert(inventoryData);
    }

    public void update(InventoryData inventoryData) {
        repository.update(inventoryData);
    }

    public void getNameIdList(String id, String name) {
        repository.getNameIdList(id,name);
    }

    public void getNameList(String name) {
        repository.getNameList(name);
    }

    public void getList() {
        repository.getList();
    }
//    public void getNameIdList(String id, String name) {
//        repository.getNameIdList(id,name);
//    }
//
    public void deleteAll() {
        repository.deleteAllNotes();
    }
//
//    public LiveData<List<InventoryData>> getAllData() {
//        return allData;
//    }
}


