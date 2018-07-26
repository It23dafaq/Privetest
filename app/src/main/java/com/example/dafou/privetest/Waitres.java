package com.example.dafou.privetest;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;


public class waitres extends AppCompatActivity  {
  public static TextView tv;
  public static ListView lv;
  public static Spinner sp;
    public ArrayAdapter<String> adapter;
    public ArrayList drinks;
   public String selected="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitres);
        tv = (TextView) findViewById(R.id.Vodka);
        drinks = new ArrayList<String>();
        FetchData fd = new FetchData();
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Rum");
        spinnerArray.add("Vodka");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems = (Spinner) findViewById(R.id.spinner);
        sItems.setAdapter(adapter);


        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                             @Override
                                             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                 selected = sItems.getSelectedItem().toString();
                                                 FetchData fd = new FetchData();
                                                 if (selected.equals("Vodka")) {

                                                     fd.execute();
                                                 }else if (selected.equals("Rum")){

                                                 }
                                             }

                                             @Override
                                             public void onNothingSelected(AdapterView<?> parent) {
                                                 selected = sItems.getSelectedItem().toString();
                                             }
                                             }) ;















        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setSelected(true);
            }

        });
        if (tv.isSelected()) {
            tv.setBackgroundColor(Color.BLUE);
        } else {
            tv.setBackgroundColor(Color.WHITE);
        }





    }






}






