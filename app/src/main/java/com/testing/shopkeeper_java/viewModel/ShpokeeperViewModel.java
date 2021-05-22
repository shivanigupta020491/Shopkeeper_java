package com.testing.shopkeeper_java.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.testing.shopkeeper_java.dao.ShopkeeperDao;
import com.testing.shopkeeper_java.database.ShopkeeperDatabase;
import com.testing.shopkeeper_java.model.Shopkeeper;
import com.testing.shopkeeper_java.repository.ShopkeeperRepository;

import java.util.List;

public class ShpokeeperViewModel extends AndroidViewModel {

    private ShopkeeperRepository repository;
    private LiveData<List<Shopkeeper>> allData;
    public ShopkeeperDao shopkeeperDao;

    public ShpokeeperViewModel(@NonNull Application application) {
        super(application);
        repository = new ShopkeeperRepository(application);
     //   allData = repository.getAllData();
        shopkeeperDao = ShopkeeperDatabase.getInstance(application).shopkeeperDao();
    }

    public void insert(Shopkeeper shopkeeper) {

        repository.insert(shopkeeper);
    }

//    public void update(Shopkeeper shopkeeper) {
//        repository.update(conference);
//    }
//
//    public void delete(Shopkeeper conference) {
//        repository.delete(conference);
//    }
//
    public void getNameList(String name) {
        repository.getNameList(name);
    }

    public void getList() {
        repository.getList();
    }
//
    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Shopkeeper>> getAllData() {
        return allData;
    }
}
