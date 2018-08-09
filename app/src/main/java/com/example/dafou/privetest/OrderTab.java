package com.example.dafou.privetest;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
    private ArrayList<String> finalOrder;
    private String nameDrink ="",how_many="",name="";
    private ArrayAdapter adapter;
    public String extra;
    public String total="";









    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        finalOrder = new ArrayList<String>();
         adapter = new ArrayAdapter<String>(
                getActivity().getBaseContext(), android.R.layout.simple_selectable_list_item, finalOrder);
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
       int counter=0;
       waitress wt = new waitress();
        if (adapter != null  ) {

            orders.setAdapter(adapter);
        }
        username=(TextView)rootView.findViewById(R.id.userna);
        SharedPreferences prefs = getActivity().getSharedPreferences(wt.MY_PREFS_NAME, Context.MODE_PRIVATE);
        String restoredText = prefs.getString("name", null);
        if (restoredText != null) {
            name = prefs.getString("name", null);//"No name defined" is the default value.
            username.setText(name);
           finalOrder.add(name);
           counter++;

           }
      DrinkTab dt = new DrinkTab();
        SharedPreferences prefs1 = getActivity().getSharedPreferences(dt.MY_PREFS_NAMEO, Context.MODE_PRIVATE);
        String DrinkName = prefs1.getString("Drink", null);
        String DrinkPrice = prefs1.getString("Price", null);
        String extra1 = prefs1.getString("extra", null);
        String total1 = prefs1.getString("how", null);
        username.setText(extra);
        if (DrinkName != null && DrinkPrice!=null && extra1!=null && total1!=null) {
             nameDrink = prefs1.getString("Drink", null);//"No name defined" is the default value.
             total = prefs1.getString("Price", null);//"No name defined" is the default value.
            extra=prefs1.getString("extra", null);//"No name defined" is the default value.
            how_many=prefs1.getString("how",null);//
            finalOrder.add(how_many);
            counter++;
            finalOrder.add(nameDrink+" "+extra);
            counter++;
           finalOrder.add(total);

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true);
                    builder.setTitle("Send/Print");
                    builder.setMessage("Εισαι σιγουρος οτι η παραγγελια ειναι σωστη?");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    (new inserOrder()).execute(how_many,name,nameDrink+" "+extra,total);
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();


                }
            });
        }





    public  class inserOrder extends AsyncTask<String,Void,String> {


        protected String doInBackground(String... params) {

            String login_url = "https://rectifiable-merchan.000webhostapp.com/InsertOrder.php";

             try {
                 String how_many= params[0];
                 String user_name = params[1];
                 String drink_name = params[2];
                 String drink_price = params[3];
                 String comments=params[4];
                 URL url = new URL(login_url);
                 HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                 httpURLConnection.setRequestMethod("POST");
                 httpURLConnection.setDoOutput(true);
                 httpURLConnection.setDoInput(true);
                 OutputStream outputStream = httpURLConnection.getOutputStream();
                 BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                 String Post_data =  URLEncoder.encode("How_Many","UTF-8")+"="+URLEncoder.encode(how_many,"UTF-8")+"&"
                         +URLEncoder.encode("UserName","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                         +URLEncoder.encode("DrinkName","UTF-8")+"="+URLEncoder.encode(drink_name,"UTF-8")+"&"
                         +URLEncoder.encode("Price","UTF-8")+"="+URLEncoder.encode(drink_price,"UTF-8")+"&"
                         +URLEncoder.encode("Comments","UTF-8")+"="+URLEncoder.encode(comments,"UTF-8");
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






