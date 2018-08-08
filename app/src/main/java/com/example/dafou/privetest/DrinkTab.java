package com.example.dafou.privetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DrinkTab extends Fragment {

  private  View rootView;
    public static Spinner sp;
    public ListView lvdrinks;
    public Button add;
    private EditText editText;


    public String selected="";
    public String resault="";
    public String resaultRum="";
    public String resaultGin="";
    public String resaultTeq="";



    public String[] data;
    public String[] dataRum;
    public String[] dataGin;
    public String[] dataTeq;

    public String[] priceRum;
    public String selectDrink="";
    public int pos;

    public static final String MY_PREFS_NAMEO = "MyPrefsFileo";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


         rootView = inflater.inflate(R.layout.drinkstab, container, false);

        return rootView;







    }







    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Your code here

        lvdrinks = (ListView)rootView.findViewById(R.id.drinklistfinal);

        FetchData fd = new FetchData();
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Rum");
        spinnerArray.add("Vodka");
        spinnerArray.add("Gin");
        spinnerArray.add("Tequila");


        //spiner Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity().getBaseContext(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems = (Spinner) rootView.findViewById(R.id.spfinal);
        sItems.setAdapter(adapter);


        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = sItems.getSelectedItem().toString();
                DrinkTab.fetchData fdt = new DrinkTab.fetchData();
                DrinkTab.fetchdataRum fdR= new DrinkTab.fetchdataRum();
                DrinkTab.fetchDataGin fdG= new DrinkTab.fetchDataGin();
                DrinkTab.fetchDataTequila fdTeq=new DrinkTab.fetchDataTequila();
                if (selected.equals("Vodka")) {

                    fdt.execute();
                }else if (selected.equals("Rum")){
                    fdR.execute();
                }else if (selected.equals("Gin")){
                    fdG.execute();
                }else if (selected.equals("Tequila")){
                    fdTeq.execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected = sItems.getSelectedItem().toString();
            }
        }) ;
        lvdrinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //view.setSelected(true);
               // view.setBackgroundColor(Color.GRAY);
                 Staticvars.selected = (lvdrinks.getItemAtPosition(position).toString());
                 selectDrink=(lvdrinks.getItemAtPosition(position).toString());
                 pos=position;


            }
        });
        add = (Button)rootView.findViewById(R.id.AddFinal);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              String input=priceRum[pos];
              Staticvars.priceRumfinal=input;
              Staticvars.arrayList=new ArrayList<String>();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences( MY_PREFS_NAMEO,Context.MODE_PRIVATE).edit();
                editor.putString("Price", input);
                editor.putString("Drink",selectDrink);
                editor.apply();



              Staticvars.arrayList.add(Staticvars.userName);
              Staticvars.arrayList.add(Staticvars.selected);
              Staticvars.arrayList.add(Staticvars.priceRumfinal);








            }
        });


    }


    private class fetchData extends AsyncTask<Void,Void,Void>{


        fetchData(){

        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://rectifiable-merchan.000webhostapp.com/ShowVodka.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                InputStream inputs = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputs));
                String line = "";
                StringBuilder sb= new StringBuilder();

                //for Vodka
                while((line=br.readLine())!= null){
                    sb.append(line+"\n");
                }
                inputs.close();
                resault=sb.toString();


            }catch (Exception e){
                e.printStackTrace();
            }

            //try parse json data
            try{
                JSONArray ja=new JSONArray(resault);
                JSONObject jo=null;
                data=new String[ja.length()];


                for (int i=0;i<=ja.length();i++){
                    jo=ja.getJSONObject(i);
                    data[i]=jo.getString("Drink");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }
        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);

            //waitres.tv.setText(this.dataParse);
            //json to listView

            lvdrinks.setAdapter(new ArrayAdapter<String>( getActivity().getBaseContext(), android.R.layout.simple_expandable_list_item_1, data));



        }

    }

    private class fetchdataRum extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://rectifiable-merchan.000webhostapp.com/ShowRum.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                InputStream inputs = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputs));
                String line = "";
                StringBuilder sb= new StringBuilder();

                //for Vodka
                while((line=br.readLine())!= null){
                    sb.append(line+"\n");
                }
                inputs.close();
                resaultRum=sb.toString();


            }catch (Exception e){
                e.printStackTrace();
            }

            //try parse json data
            try{
                JSONArray ja=new JSONArray(resaultRum);
                JSONObject jo=null;
                dataRum=new String[ja.length()];
                priceRum=new String[ja.length()];

                for (int i=0;i<=ja.length();i++){
                    jo=ja.getJSONObject(i);
                    dataRum[i]=jo.getString("Drink") ;
                    priceRum[i]=jo.getString("Price");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }
        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);

            //waitres.tv.setText(this.dataParse);
            //json to listView

            lvdrinks.setAdapter(new ArrayAdapter<String>( getActivity().getBaseContext(), android.R.layout.simple_expandable_list_item_1, dataRum));



        }
    }
    private class fetchDataGin extends AsyncTask<Void,Void,Void>{


        fetchDataGin(){

        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://rectifiable-merchan.000webhostapp.com/ShowGin.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                InputStream inputs = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputs));
                String line = "";
                StringBuilder sb= new StringBuilder();

                //for Vodka
                while((line=br.readLine())!= null){
                    sb.append(line+"\n");
                }
                inputs.close();
                resaultGin=sb.toString();


            }catch (Exception e){
                e.printStackTrace();
            }

            //try parse json data
            try{
                JSONArray ja=new JSONArray(resaultGin);
                JSONObject jo=null;
                dataGin=new String[ja.length()];


                for (int i=0;i<=ja.length();i++){
                    jo=ja.getJSONObject(i);
                    dataGin[i]=jo.getString("Drink");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }
        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);

            //waitres.tv.setText(this.dataParse);
            //json to listView

            lvdrinks.setAdapter(new ArrayAdapter<String>( getActivity().getBaseContext(), android.R.layout.simple_expandable_list_item_1, dataGin));



        }

    }
    private class fetchDataTequila extends AsyncTask<Void,Void,Void>{


        fetchDataTequila(){

        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://rectifiable-merchan.000webhostapp.com/ShowTequila.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                InputStream inputs = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputs));
                String line = "";
                StringBuilder sb= new StringBuilder();

                //for Vodka
                while((line=br.readLine())!= null){
                    sb.append(line+"\n");
                }
                inputs.close();
                resaultTeq=sb.toString();


            }catch (Exception e){
                e.printStackTrace();
            }

            //try parse json data
            try{
                JSONArray ja=new JSONArray(resaultTeq);
                JSONObject jo=null;
                dataTeq=new String[ja.length()];


                for (int i=0;i<=ja.length();i++){
                    jo=ja.getJSONObject(i);
                    dataTeq[i]=jo.getString("Drink");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }
        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);

            //waitres.tv.setText(this.dataParse);
            //json to listView

            lvdrinks.setAdapter(new ArrayAdapter<String>( getActivity().getBaseContext(), android.R.layout.simple_expandable_list_item_1, dataTeq));



        }

    }



}
