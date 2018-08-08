package com.example.dafou.privetest;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class OrderTab extends Fragment {

    static public GridView orders;
    private View rootView;
    public TextView username;
    private TextView ordertext;
    public Button printInsert;
    private DrinkTab dt;
    public String price;
    public ArrayList ar;
    public String[] finalar;
    private String names;
    public ArrayList finalOrder;
    public String nameDrink ="",namePrice="",name="";
    static AlertDialog alertDialog;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        finalOrder = new ArrayList<String>();
       // Bundle args = getArguments();
       // username = (TextView)rootView.findViewById(R.id.username);
      //  if(args!=null) {

       //     String index = args.getString("index");
       //     username.setText(index);
       // }


        rootView = inflater.inflate(R.layout.ordertab, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ordertext = rootView.findViewById(R.id.order);
        printInsert = (Button) rootView.findViewById(R.id.Print);
        orders = (GridView) rootView.findViewById(R.id.orders);
        dt = new DrinkTab();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity().getBaseContext(), android.R.layout.simple_selectable_list_item, finalOrder);

        username=(TextView)rootView.findViewById(R.id.userna);
        SharedPreferences prefs = getActivity().getSharedPreferences(waitress.MY_PREFS_NAME, Context.MODE_PRIVATE);
        String restoredText = prefs.getString("name", null);
        if (restoredText != null) {
            name = prefs.getString("name", null);//"No name defined" is the default value.
            username.setText(name);
            adapter.add(name);



        }
        SharedPreferences prefs1 = getActivity().getSharedPreferences(DrinkTab.MY_PREFS_NAMEO, Context.MODE_PRIVATE);
        String DrinkName = prefs1.getString("Drink", null);
        String DrinkPrice = prefs1.getString("Price", null);
        if (DrinkName != null && DrinkPrice!=null) {
             nameDrink = prefs1.getString("Drink", null);//"No name defined" is the default value.
             namePrice = prefs1.getString("Price", null);//"No name defined" is the default value.
            adapter.add(nameDrink);
            adapter.add(namePrice);


        }

        if (adapter != null) {





                orders.setAdapter(adapter);





        }








       /* ar = new ArrayList<String>();
        if (Staticvars.arrayList != null) {
            ar.addAll(Staticvars.arrayList);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity().getBaseContext(), android.R.layout.simple_selectable_list_item, ar);
            orders.setAdapter(adapter);
            */
            printInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    (new inserOrder()).execute(name,nameDrink,namePrice);

                }
            });
        }





    public  class inserOrder extends AsyncTask<String,Void,String> {


        protected String doInBackground(String... params) {

            String login_url = "https://rectifiable-merchan.000webhostapp.com/InsertOrder.php";

             try {
                 String user_name = params[0];
                 String drink_name = params[1];
                 String drink_price = params[2];
                 URL url = new URL(login_url);
                 HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                 httpURLConnection.setRequestMethod("POST");
                 httpURLConnection.setDoOutput(true);
                 httpURLConnection.setDoInput(true);
                 OutputStream outputStream = httpURLConnection.getOutputStream();
                 BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                 String Post_data = URLEncoder.encode("UserName","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                         +URLEncoder.encode("DrinkName","UTF-8")+"="+URLEncoder.encode(drink_name,"UTF-8")+"&"
                         +URLEncoder.encode("Price","UTF-8")+"="+URLEncoder.encode(drink_price,"UTF-8");
                 bufferedWriter.write(Post_data);
                 bufferedWriter.flush();
                 bufferedWriter.close();
                 InputStream inputStream = httpURLConnection.getInputStream();
                 BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                 String s="" ;
                 String Line="";
                 while((Line=bufferedReader.readLine())!= null){
                     s+=Line;

                 }
                 bufferedReader.close();
                 inputStream.close();
                 httpURLConnection.disconnect();
                 return s;
             } catch (MalformedURLException e) {
                 e.printStackTrace();
             } catch (IOException e) {

                 e.printStackTrace();
             }

            return null;
        }






    }

}






