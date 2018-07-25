package com.example.dafou.privetest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ProtocolException;
import java.net.URL;

import static android.app.PendingIntent.getActivity;


public class FetchData extends AsyncTask<Void,Void,Void> {

    Activity mActivity;
    String data="";
    String singleParse="";
    String dataParse="";
    ArrayAdapter<String> adapter;

     FetchData(){

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

               while (line != null) {
                   line = br.readLine();
                   data = data + line;
               }
           } catch (MalformedURLException e) {
               e.printStackTrace();
           }  catch (IOException e) {
               e.printStackTrace();
           }
           try {
               JSONArray ja = new JSONArray(data);

               for(int i=0;i<ja.length();i++){
                   JSONObject jo =(JSONObject)ja.get(i);
                   singleParse= "Drink:"+jo.get("Drink")+"\n"+
                           "Price:"+jo.get("Price")+"\n";
                   dataParse=dataParse+singleParse+"\n";




               }

           } catch (JSONException e) {
               e.printStackTrace();
           }

           return null;
       }
    @Override
    protected void onPostExecute(Void avoid) {
        super.onPostExecute(avoid);

        waitres.tv.setText(this.dataParse);


    }




}

