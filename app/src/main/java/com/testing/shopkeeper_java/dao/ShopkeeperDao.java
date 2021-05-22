package com.testing.shopkeeper_java.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.testing.shopkeeper_java.model.Shopkeeper;

import java.util.List;

@Dao
public interface ShopkeeperDao {

    @Insert
    void insert(Shopkeeper shopkeeper);


    @Update
    void update(Shopkeeper shopkeeper);

    @Delete
    void delete(Shopkeeper shopkeeper);

    @Query("DELETE FROM shopkeeper_detail")
    void deleteAllData();

    @Query("SELECT * FROM shopkeeper_detail")
    Shopkeeper getNameList();
//
//    @Query("SELECT * FROM shopkeeper_detail")
//    Shopkeeper getNameList(String name);
//
//
    @Query("SELECT *  FROM shopkeeper_detail")
    LiveData<List<Shopkeeper>> getAllDetail();

    @Query("SELECT *  FROM shopkeeper_detail")
    List<Shopkeeper> getList();


}
