package com.testing.shopkeeper_java.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopkeeper_detail")

public class Shopkeeper {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "unique_id")
    private String uniqueId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "price")
    private Float price;
    @ColumnInfo(name = "quantity")
    private int quantity;
    @ColumnInfo(name = "total_Cost")
    private Float totalCost;
    @ColumnInfo(name = "transaction_Type")
    private String transactionType;

    public Shopkeeper(String uniqueId,String name, String date, Float price,int quantity, Float totalCost,String transactionType) {

        this.uniqueId = uniqueId;
        this.name = name;
        this.date = date;
        this.price = price;
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.transactionType = transactionType;
    }


    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Float getTotalCost() {
        return totalCost;
    }


}
