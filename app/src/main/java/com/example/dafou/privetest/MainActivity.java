package com.example.dafou.privetest;

import android.app.AlertDialog;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

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

public class MainActivity extends AppCompatActivity {
    EditText user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText)findViewById(R.id.Username);
        pass = (EditText)findViewById(R.id.Password);
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               Onlogin(v);

    }
});
    }


    public void Onlogin(View view){
        String username=user.getText().toString();
        String password=pass.getText().toString();

        Staticvars.userName=username;
        String type ="LOGIN";

        BackgroundLogin backglogin = new BackgroundLogin(this);
        backglogin.execute(type,username,password);



    }
    public class BackgroundLogin extends AsyncTask<String,Void,String> {
        Context context;
        AlertDialog alertDialog;

        BackgroundLogin(Context ctx){
            context=ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String type= params[0];
            String login_url="https://rectifiable-merchan.000webhostapp.com/Login.php";
            try {
                String user_name=params[1];
                String Password=params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String Post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("Password","UTF-8")+"="+URLEncoder.encode(Password,"UTF-8");
                bufferedWriter.write(Post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String Resault="" ;
                String Line="";
                while((Line=bufferedReader.readLine())!= null){
                    Resault+=Line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return Resault;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e){

                e.printStackTrace();
            }
            if(type.equals("login")){

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("LoginStatus");

        }

        @Override
        protected void onPostExecute(String resault) {
            alertDialog.setMessage(resault);

            alertDialog.show();
            if (resault.equals("connectedlogin succes panos")){
                startActivity(new Intent(MainActivity.this, AdminAct.class));

            }else if (resault.equals("connectedlogin succes")){
                //startActivity(new Intent(MainActivity.this, waitress.class));
                String username=user.getText().toString();
                Intent intent = new Intent(MainActivity.this, waitress.class);
                intent.putExtra("name",username);




                startActivity(intent);
            }else {
                alertDialog.show();
            }
            alertDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
