package com.example.dafou.privetest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import static android.app.PendingIntent.getActivities;
import static android.app.PendingIntent.getActivity;


public class FetchData extends AsyncTask<Void,Void,Void> {

    String data="";
    String singleParse="";
    String dataParse="";
    public String Resualt="";
     String[] Rum;







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
               while (line != null) {
                   sb.append(line+"\n");
               }
               Resualt=sb.toString();
               inputs.close();
           } catch (MalformedURLException e) {
               e.printStackTrace();
           }  catch (IOException e) {
               e.printStackTrace();
           }
           try {
               JSONArray ja = new JSONArray(data);

               Rum=new String[ja.length()];

               for(int i=0;i<ja.length();i++){
                   JSONObject jo =(JSONObject)ja.get(i);
                   Rum[i]=jo.getString("Drink");
                  // singleParse= "Drink:"+jo.get("Drink")+"\n"+
                  //         "Price:"+jo.get("Price")+"\n";
                  // dataParse=dataParse+singleParse+"\n";





               }

           } catch (JSONException e) {
               e.printStackTrace();
           }

           return null;
       }
    @Override
    protected void onPostExecute(Void avoid) {
        super.onPostExecute(avoid);
        waitres wt = new waitres();
        //the array adapter to load data into list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(waitres.getContext(), android.R.layout.simple_list_item_1, Rum);

        //attaching adapter to listview

        wt.lvdrinks.setAdapter(arrayAdapter);






    }





}

