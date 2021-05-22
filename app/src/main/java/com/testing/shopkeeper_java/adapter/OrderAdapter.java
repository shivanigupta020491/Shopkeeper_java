package com.testing.shopkeeper_java.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.testing.shopkeeper_java.R;
import com.testing.shopkeeper_java.pojo.selectedItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ItemViewHolder>  {
    private ArrayList<selectedItem> listdata;
    private Context context;
    private AlertDialog.Builder builder;

    public OrderAdapter(Context context, ArrayList<selectedItem> listdata) {
        this.listdata = listdata;
        this.context = context;

    }


    @NonNull
    @Override
    public OrderAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);
        return new OrderAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ItemViewHolder holder, final int position) {


        holder.itemTV.setText("Item Name - ");
        holder.quantityTV.setText("Item Quantity - ");
        holder.itemName.setText(listdata.get(position).getItemName());
        holder.quantity.setText(Integer.toString(listdata.get(position).getItemQuantity()));


        System.out.println(">>>>> additionalList.get(position).getItemName()" + listdata.get(position).getItemName());

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView quantity;
        TextView itemName;
        TextView itemTV;
        TextView quantityTV;


        LinearLayout orderLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
             this.quantity = itemView.findViewById(R.id.itemQuant);
             this.itemName = itemView.findViewById(R.id.topicName);
             this.itemTV = itemView.findViewById(R.id.itemNameTV);
             this.quantityTV = itemView.findViewById(R.id.itemQuanTV);

        }

    }



}
