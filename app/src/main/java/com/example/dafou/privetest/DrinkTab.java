package com.example.dafou.privetest;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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


    public String selected="";
    public String resault="";
    public String resaultRum="";

    public String[] data;
    public String[] dataRum;
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
                if (selected.equals("Vodka")) {

                    fdt.execute();
                }else if (selected.equals("Rum")){
                    fdR.execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected = sItems.getSelectedItem().toString();
            }
        }) ;

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


                for (int i=0;i<=ja.length();i++){
                    jo=ja.getJSONObject(i);
                    dataRum[i]=jo.getString("Drink");
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




}
