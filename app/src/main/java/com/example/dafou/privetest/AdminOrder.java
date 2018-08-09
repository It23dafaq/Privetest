package com.example.dafou.privetest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdminOrder extends Fragment {
    public String resault="";
    public String[] dataOrder;
    public String[] dataTziros;
    public ListView orderview;
    private View rootview;
    private TextView tzir;
    public double sinolo=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       rootview= inflater.inflate(R.layout.adminorder,container,false);

        return rootview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AdminOrder.fetchdOrderdata ft = new AdminOrder.fetchdOrderdata();
        ft.execute();
        orderview=(ListView)rootview.findViewById(R.id.listData);
        tzir=(TextView)rootview.findViewById(R.id.tziros);


        //  for (int i = 0; i <= dataTziros.length - 1; i++) {
            //  sinolo = Double.parseDouble(dataTziros[i]) + sinolo;
         // }
         // tzir.setText(String.valueOf(sinolo));




    }

    private class fetchdOrderdata extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://rectifiable-merchan.000webhostapp.com/ShowOrder.php");
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
                dataOrder=new String[ja.length()];
                dataTziros=new String[ja.length()];

                for (int i=0;i<=ja.length();i++){
                    jo=ja.getJSONObject(i);
                    dataOrder[i]=jo.getString("Posotita")+jo.getString("UserName")+" "+
                            jo.getString("DrinkName")+" "+jo.getString("Price");
                    dataTziros[i]=jo.getString("Price");
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

            orderview.setAdapter(new ArrayAdapter<String>( getActivity().getBaseContext(), android.R.layout.simple_expandable_list_item_1, dataOrder));



        }
    }
}
