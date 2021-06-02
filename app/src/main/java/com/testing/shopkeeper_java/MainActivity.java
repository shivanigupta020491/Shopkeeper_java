package com.testing.shopkeeper_java;


import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.testing.shopkeeper_java.database.ShopkeeperDatabase;
import com.testing.shopkeeper_java.model.InventoryData;
import com.testing.shopkeeper_java.model.Shopkeeper;
import com.testing.shopkeeper_java.viewModel.InventoryViewModel;
import com.testing.shopkeeper_java.viewModel.ShpokeeperViewModel;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText itemNameEditText;
    private EditText priceText;
    private EditText quantityText;
    private TextView dateText;
    private TextView totalAmntText;
    private Spinner typeSpinner;
    private Spinner uniqueIdTypeSpinner;
    private ImageView imgaeViewCalendar;
    private Button goBtn,inventoryreport;
    private LinearLayout dateLayout;
    private String spinnerTypePopupValue = "";
    private String spinnerTypePopupValueId;
    private Integer spinnerTypePopupPosition;
    private String uniqueIdPopupValue,uniqueId;
    private String uniqueIdTypePopupValueId;
    private Integer uniqueIdTypePopupPosition;
    ArrayList<String> spinnerList = new ArrayList();
    ArrayList<String> uniqueIdList = new ArrayList();
    ArrayList<String> availableNameList = new ArrayList();
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String item;
    private Float price;
    private int quantity;
    private String dateValue;
    private Float totalAnmt;
    private ShopkeeperDatabase shopkeeperDatabase;
    private Shopkeeper shopkeeper;
    private ShpokeeperViewModel shpokeeperViewModel;
    private InventoryViewModel inventoryViewModel;
    ArrayAdapter<String> uniqueIdSpinnerAdap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initInstance();
        setListeners();

    }

    private void initView() {
        itemNameEditText = findViewById(R.id.itemNameET);
        priceText = findViewById(R.id.costET);
        quantityText = findViewById(R.id.quantityET);
        dateText = findViewById(R.id.dateText);
        imgaeViewCalendar = findViewById(R.id.calendarImage);
        totalAmntText = findViewById(R.id.totalCostText);
        typeSpinner = findViewById(R.id.selectTypeSpinner);
        uniqueIdTypeSpinner = findViewById(R.id.uniqueIdTypeSpinner);
        goBtn = findViewById(R.id.goBtnReg);
        inventoryreport = findViewById(R.id.inventoryreport);
        dateLayout = findViewById(R.id.setDateLayout);


    }

    private void initInstance() {

        shopkeeperDatabase = ShopkeeperDatabase.getInstance(this);
        shpokeeperViewModel = new ViewModelProvider(this).get(ShpokeeperViewModel.class);
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        uniqueIdPopupValue = "New";


        spinnerList.add("Purchase");
        spinnerList.add("Sale");
        uniqueIdList.add("New");

        textWathcherForEditText(priceText);
        textWathcherForEditText(quantityText);

        ArrayAdapter<String> spinnerAdap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
        spinnerAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(spinnerAdap);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        uniqueIdSpinnerAdap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, uniqueIdList);
        uniqueIdSpinnerAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uniqueIdTypeSpinner.setAdapter(uniqueIdSpinnerAdap);
        uniqueIdTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uniqueIdPopupValue = uniqueIdTypeSpinner.getSelectedItem().toString();
                if (uniqueIdPopupValue.equals("New")) {
                    System.out.println("->>>>unique_Id not "+position);
                    itemNameEditText.setText("");
                    itemNameEditText.setEnabled(true);

                }else if (!uniqueIdPopupValue.equals("New")){
                    System.out.println("->>>>unique_Id yes "+position);
                    itemNameEditText.setEnabled(false);
                    itemNameEditText.setText(availableNameList.get(position-1));
                    uniqueId=String.valueOf(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //uniqueIdPopupValue = uniqueIdTypeSpinner.getSelectedItem().toString();
        spinnerTypePopupValue = typeSpinner.getSelectedItem().toString();

        Calendar calendarStart = Calendar.getInstance();
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMMM yyyy");
        String currentDate = sdfDate.format(calendarStart.getTime());
        dateText.setText(currentDate);

        showUniqueidList();

    }

    private void setListeners(){

        goBtn.setOnClickListener(this);
        inventoryreport.setOnClickListener(this);
        dateLayout.setOnClickListener(this);
        imgaeViewCalendar.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.goBtnReg:{
                if(isValid()){
                   item = itemNameEditText.getText().toString();
                   price = Float.parseFloat(priceText.getText().toString());
                   quantity = Integer.parseInt(quantityText.getText().toString());
                   dateValue = dateText.getText().toString();
                   totalAnmt = Float.parseFloat(totalAmntText.getText().toString());

                   if (uniqueIdPopupValue.equals("New")){
                       String idListSize = String.valueOf(uniqueIdList.size());
                       uniqueId = idListSize;
                   }

                   Thread thread = new Thread(new Runnable() {
                       @Override
                       public void run() {
                           InventoryData inventoryData = inventoryViewModel.inventoryDao.getRoomNameList(item.toLowerCase());
                           InventoryData inventoryIdData = inventoryViewModel.inventoryDao.getRoomIdList(uniqueId,item.toLowerCase());
                           System.out.println("insert data name " + uniqueIdPopupValue );

                           if (uniqueIdPopupValue.equals("New") && inventoryData==null){
                               InsertaUpdateFunction(uniqueId,item.toLowerCase(),dateValue,price,quantity,totalAnmt,spinnerTypePopupValue);
                               InventoryData inventoryData1 = new InventoryData(uniqueId,item.toLowerCase(),quantity);
                               inventoryViewModel.insert(inventoryData1);
                               Looper.prepare();
                               emptyData();
                               Looper.loop();
                               //openReportPage();

                           }else if (uniqueIdPopupValue.equals("New") && inventoryData.name != null){
//                               itemNameEditText.setError("Item Name exist ");
//                               itemNameEditText.setFocusable(true);
                               Looper.prepare();
                               Toast.makeText(MainActivity.this,"Item Name exist",Toast.LENGTH_LONG).show();
                               Looper.loop();

                           }else if(!uniqueIdPopupValue.equals("New")){

                               int quantityItem = inventoryData.quantity;
                               if (typeSpinner.getSelectedItem().toString().equals("Purchase")){
                                   InsertaUpdateFunction(uniqueId,item.toLowerCase(),dateValue,price,quantity,totalAnmt,spinnerTypePopupValue);
                                   quantityItem = quantityItem + quantity;
                                   inventoryData.setQuantity(quantityItem);
                                   inventoryViewModel.update(inventoryData);
                                   Looper.prepare();
                                   System.out.println(">>>>>>Get Quantity " + quantityItem);
                                   emptyData();
                                   Looper.loop();
                                   //openReportPage();
                               }else {
                                   quantityItem = quantityItem - quantity;
                                   if (quantityItem >= 0){
                                       System.out.println(">>>>>>Get Quantity correction " + quantityItem);
                                       InsertaUpdateFunction(uniqueId,item.toLowerCase(),dateValue,price,quantity,totalAnmt,spinnerTypePopupValue);
                                       inventoryData.setQuantity(quantityItem);
                                       inventoryViewModel.update(inventoryData);
                                       Looper.prepare();

                                       emptyData();
                                       Looper.loop();
                                       //openReportPage();
                                   }else {
//                                       quantityText.setError("You don't have enough Quantity");
//                                       quantityText.setFocusable(true);
                                       Looper.prepare();
                                       Toast.makeText(MainActivity.this,"You don't have enough Quantity",Toast.LENGTH_LONG).show();
                                       Looper.loop();
                                   }
                               }

                           }

                           List<Shopkeeper> event = shpokeeperViewModel.shopkeeperDao.getList();
                           System.out.println("insert data print " + event.size() );

                       }
                   });
                   thread.start();



//                   AsyncTask.execute(new Runnable() {
//                       @Override
//                       public void run() {
//
//
//
//
//
//                       }
//
//
//                   });



                   System.out.println(">> insert data " + uniqueIdPopupValue + item + dateValue + price + quantity + totalAnmt+
                           spinnerTypePopupValue);


                }
                break;
            }

            case R.id.inventoryreport:{
                openReportPage();
                break;
            }

            case R.id.setDateLayout:{
                System.out.println(">> insert setDateLayout " );
                getDateByDatePicker();
                break;
            }
            case R.id.calendarImage:{
                System.out.println(">> insert calendarImage " );
                getDateByDatePicker();
                break;
            }
        }

    }



    // set drop down
//    private void setRoomSpinner(Spinner spinner, ArrayList<String> spinnerItem,String spinnerTypeValue, Integer spinnerTypePopupPosition) {
//
//
//        ArrayAdapter<String> spinnerAdap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItem);
//
//        spinnerAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(spinnerAdap);
//
//        //yearSpinner!!.setOnItemSelectedListener(this)
//        if (spinnerTypeValue.equals("spinnerTypePopupValue")) {
//            spinnerTypePopupValue = spinner.getSelectedItem().toString();
//
//        }else if (spinnerTypeValue.equals("uniqueIdPopupValue")){
//            uniqueIdPopupValue = spinner.getSelectedItem().toString();
//        }
//
//            spinnerTypePopupPosition = spinner.getSelectedItemPosition();
//            spinnerTypePopupValue = spinner.getSelectedItem().toString();
//
//
//           System.out.println(">>>>>> signup spinner value" + spinnerTypePopupPosition + spinnerTypePopupValue);
//    }

    public void textWathcherForEditText(EditText editText){

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Float price= 0f;
                int quantity=0;
                System.out.println(priceText.getText().toString());
                if(priceText.getText().toString().equals("") || quantityText.getText().toString().equals("")){

                }else {
                     price = Float.parseFloat(priceText.getText().toString());
                    quantity = Integer.parseInt(quantityText.getText().toString());
                }
               if (price != 0 && quantity != 0f) {
                   Float value = price * quantity;
                   totalAmntText.setText(value.toString());


               }else {
                   totalAmntText.setText("0");
               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        });

    }

    private boolean isValid() {

        if (spinnerTypePopupValue.equalsIgnoreCase("Select")){
            Toast.makeText(this,"Select Type",Toast.LENGTH_SHORT).show();
            return false;

        } else
        if (TextUtils.isEmpty(itemNameEditText.getText())) {
            itemNameEditText.setError("Enter Item Name.");
            itemNameEditText.setFocusable(true);
            return false;



        } else if (TextUtils.isEmpty(priceText.getText())) {
            priceText.setError("Enter price.");
            priceText.setFocusable(true);
            return false;

        } else if (Integer.parseInt(priceText.getText().toString()) <= 0) {
            priceText.setError("price should be greater than 0");
            priceText.setFocusable(true);
            return false;

        }else if (TextUtils.isEmpty(quantityText.getText())) {
            quantityText.setError("Enter Quantity");
            quantityText.setFocusable(true);
            return false;

        } else if (Integer.parseInt(quantityText.getText().toString()) <= 0) {
            priceText.setError("Quantity should be greater than 0");
            priceText.setFocusable(true);
            return false;

        } else {
            return true;
        }

    }

    private void getDateByDatePicker(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Calendar calendarStart = Calendar.getInstance();
                        calendarStart.set(Calendar.YEAR, year);
                        calendarStart.set(Calendar.MONTH, monthOfYear);
                        calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = sdf.format(calendarStart.getTime());
                        dateText.setText(date);


                        //dateTextView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        // for date
//                        dateText = sdf.format(dateTextView.getText().toString());
                        //dateTextView.setText(dateText);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();




    }

    public void InsertaUpdateFunction(final String uniqueId, final String nameText, final String date, final Float price,
                                      final int quantity, final Float totalCost, final String transactionValue) {
        //int size_event = conferenceViewModel.conferenceDao.getNameList(popupEditText);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

          //  Shopkeeper event = shpokeeperViewModel.shopkeeperDao.getNameList(nameText);
              //  System.out.println(">>>>>>> InsertaUpdateFunction main insert " + event);
                shopkeeper = new Shopkeeper(uniqueId,nameText,date,price,quantity,totalCost,transactionValue);
           //   System.out.println(">>>>>>> InsertaUpdateFunction main insert " + shopkeeper);
                shpokeeperViewModel.insert(shopkeeper);


//
            }
        });
    }

    private void openReportPage(){
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    private void showUniqueidList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Shopkeeper> event = shpokeeperViewModel.shopkeeperDao.getList();
                List<InventoryData> inventoryData = inventoryViewModel.inventoryDao.getList();
                System.out.println(">>>>>>>> insert data print " + event.size() + inventoryData.size());

                availableNameList.clear();
                uniqueIdList.clear();

                uniqueIdList.add("New");

                for (int i = 0; i < inventoryData.size(); i++) {
                    String name = inventoryData.get(i).name;
                    String id = inventoryData.get(i).uniqueId;

                    uniqueIdList.add(id + " - " + name);
                    availableNameList.add(name);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        uniqueIdTypeSpinner.setAdapter(uniqueIdSpinnerAdap);
                        uniqueIdSpinnerAdap.notifyDataSetChanged();
                        typeSpinner.setSelection(0);
//    uniqueIdTypeSpinner.setSelection(0);
                    }
                });


//                Looper.prepare();
//      typeSpinner.setSelection(0);
//    uniqueIdTypeSpinner.setSelection(0);
//                Looper.loop();

            }
        });
        thread.start();
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                //LiveData<List<Shopkeeper>> event = shpokeeperViewModel.shopkeeperDao.getAllDetail();
//                shpokeeperViewModel.shopkeeperDao.deleteAllData();
//                inventoryViewModel.inventoryDao.deleteAllData();
//
//
//            }
//        });
    }
    //}
    private void emptyData(){
        Toast.makeText(MainActivity.this,"Data Added Successfully",Toast.LENGTH_LONG).show();
        itemNameEditText.setText("");
        priceText.setText("");
        quantityText.setText("");
        totalAmntText.setText("0.0");
        uniqueIdPopupValue="New";
        showUniqueidList();
    }
}