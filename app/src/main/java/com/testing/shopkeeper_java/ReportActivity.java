package com.testing.shopkeeper_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.testing.shopkeeper_java.adapter.OrderAdapter;
import com.testing.shopkeeper_java.model.InventoryData;
import com.testing.shopkeeper_java.model.Shopkeeper;
import com.testing.shopkeeper_java.pojo.selectedItem;
import com.testing.shopkeeper_java.viewModel.InventoryViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<selectedItem> selectedItemList;
    private List<InventoryData> inventoryItemList;
    private InventoryViewModel inventoryViewModel;
    private OrderAdapter listDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initView();
        initInstance();

    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerItemList);
    }

    private void initInstance() {
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);

        selectedItemList = new ArrayList<>();
        inventoryItemList = new ArrayList<>();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //LiveData<List<Shopkeeper>> event = shpokeeperViewModel.shopkeeperDao.getAllDetail();
                // shpokeeperViewModel.shopkeeperDao.deleteAllData();

                inventoryItemList = inventoryViewModel.inventoryDao.getList();
                System.out.println("insert data print " + inventoryItemList.size() );

                for (int i = 0; i < inventoryItemList.size(); i++){
                    String name = inventoryItemList.get(i).name;
                    int quantity = inventoryItemList.get(i).quantity;

                    selectedItem item = new selectedItem(name,quantity);
                    selectedItemList.add(item);

                    setDataListInAdapter(selectedItemList);
                }

            }
        });
    }

    private void setDataListInAdapter(final ArrayList<selectedItem> itemsList1) {

        if (itemsList1.size() > 0) {
            listDataAdapter = new OrderAdapter(this, itemsList1);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(listDataAdapter);
            recyclerView.setHasFixedSize(false);
            listDataAdapter.notifyDataSetChanged();

        }
    }
}