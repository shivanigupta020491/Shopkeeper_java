package com.testing.shopkeeper_java.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class selectedItem {

    private String itemName;
    private int itemQuantity;


    public selectedItem(String itemName, int itemQuantity) {

        this.itemName = itemName;
        this.itemQuantity = itemQuantity;

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
