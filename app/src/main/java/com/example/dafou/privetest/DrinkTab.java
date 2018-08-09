package com.example.dafou.privetest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
    public String selectedPos=" ";
    public String resault="";
    public String resaultRum="";
    public String resaultGin="";
    public String resaultTeq="";
    public double sinolikhPosotita=0;
    public  ArrayAdapter<CharSequence> adapter1;




    public String[] data;
    public String[] dataRum;
    public String[] dataGin;
    public String[] dataTeq;


    public String[] priceRum;
    public String selectDrink="";
    public int pos;

    public String MY_PREFS_NAMEO = "MyPrefsFileo";



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
        final PopupWindow popupWindow = new PopupWindow();
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
        final Spinner posotita = (Spinner) rootView.findViewById(R.id.spinnerpos);

        List<String> spinnerArraypos =  new ArrayList<String>();
        spinnerArraypos.add("1");
        spinnerArraypos.add("2");
        spinnerArraypos.add("3");
        spinnerArraypos.add("4");
        spinnerArraypos.add("5");
        spinnerArraypos.add("6");
        spinnerArraypos.add("Μπουκαλι");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                getActivity().getBaseContext(), android.R.layout.simple_spinner_item, spinnerArraypos);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        posotita.setAdapter(adapter1);




             posotita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     selectedPos = posotita.getSelectedItem().toString();
                     if (selectedPos.equals("1")) {
                         sinolikhPosotita = 1;

                     } else if (selectedPos.equals("2")) {
                         sinolikhPosotita = 2 ;
                     } else if (selectedPos.equals("3")) {
                         sinolikhPosotita = 3 ;
                     } else if (selectedPos.equals("4")) {
                         sinolikhPosotita = 4 ;
                     } else if (selectedPos.equals("5")) {
                         sinolikhPosotita = 5 ;
                     } else if (selectedPos.equals("6")) {
                         sinolikhPosotita = 6 ;
                     } else if (selectedPos.equals("Μπουκαλι")){
                            if(priceRum!=null ){
                                sinolikhPosotita=(Double.parseDouble(priceRum[pos])*10)+10;
                            }
                     }
                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> parent) {
                     selectedPos = posotita.getSelectedItem().toString();
                 }
             });

        add = (Button)rootView.findViewById(R.id.AddFinal);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             showSortPopup(v);

              String input=priceRum[pos];
              double telikh=sinolikhPosotita*Double.parseDouble(input);

                SharedPreferences.Editor editor = getActivity().getSharedPreferences( MY_PREFS_NAMEO,Context.MODE_PRIVATE).edit();
                editor.putString("how", String.valueOf(sinolikhPosotita));
                editor.putString("Drink",selectDrink);
                editor.putString("Price",String.valueOf(telikh));


                editor.apply();











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


    private void showSortPopup(View view) {

        final View popupView = getLayoutInflater().inflate(R.layout.activity_pop_up, null);
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final PopupWindow popupWindow = new PopupWindow(popupView, popupView.getMeasuredWidth(), popupView.getMeasuredHeight(), true);
        popupWindow.showAtLocation(popupView, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setIgnoreCheekPress();





        Button btnOk = (Button) popupView.findViewById(R
                .id.Confirm);
        Button btnCancel = (Button) popupView.findViewById(R.id.cancel);

      final RadioGroup mMode = (RadioGroup) popupView.findViewById(R.id.rad);

        mMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected=mMode.getCheckedRadioButtonId();
                RadioButton  rdb=(RadioButton)popupView.findViewById(selected);
               String extra= rdb.getText().toString();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences( MY_PREFS_NAMEO,Context.MODE_PRIVATE).edit();
                editor.putString("extra", extra);
                editor.apply();

                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    /*  View layout = getLayoutInflater().inflate(R.layout.activity_pop_up, null);
        final PopupWindow popup =  new PopupWindow(layout);
        popup.setContentView(layout);
        final RadioGroup rd = (RadioGroup)rootView.findViewById(R.id.rad);
        Button bt=(Button)rootView.findViewById(R.id.Confirm);

        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);


        popup.showAsDropDown(view);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popup.dismiss();
                return true;
            }
        });
*/}
    }



