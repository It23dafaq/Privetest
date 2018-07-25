package com.example.dafou.privetest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.InputStream;

import java.util.ArrayList;


public class waitres extends AppCompatActivity  {
  public static TextView tv;
  public static ListView lv;
  public static Spinner sp;
    public ArrayAdapter<String> adapter;
    public ArrayList drinks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitres);
        tv = (TextView) findViewById(R.id.Vodka);

        drinks = new ArrayList<String>();





        FetchData fd = new FetchData();
        fd.execute();





    }






}






