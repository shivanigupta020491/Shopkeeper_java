package com.testing.shopkeeper_java.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.testing.shopkeeper_java.model.InventoryData;
import com.testing.shopkeeper_java.model.Shopkeeper;

import java.util.List;

@Dao
public interface InventoryDao {

    @Insert
    void insert(InventoryData inventoryData);

    @Update
    void update(InventoryData inventoryData);

    @Query("SELECT * FROM inventory_detail ")
    InventoryData getAllList();

    @Query("DELETE FROM inventory_detail")
    void deleteAllData();

    @Query("SELECT *  FROM inventory_detail")
    List<InventoryData> getList();

    @Query("SELECT *  FROM inventory_detail ")
    LiveData<List<InventoryData>> getAllDetail();

    @Query("SELECT * FROM inventory_detail Where name = :name")
    InventoryData getRoomNameList(String name);

    @Query("SELECT * FROM inventory_detail Where unique_id = :id and name = :name")
    InventoryData getRoomIdList(String id,String name);
}
