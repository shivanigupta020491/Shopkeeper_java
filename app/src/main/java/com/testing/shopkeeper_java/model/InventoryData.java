package com.testing.shopkeeper_java.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "inventory_detail")
public class InventoryData {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "unique_id")
    public String uniqueId;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "quantity")
    public int quantity;



    public InventoryData(String uniqueId, String name, int quantity) {

        this.uniqueId = uniqueId;
        this.name = name;
        this.quantity = quantity;

    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //    public List<String> getItemList() {
//        return itemList;
//    }
//
//    public void setItemList(List<String> itemList) {
//        this.itemList = itemList;
//    }
}
