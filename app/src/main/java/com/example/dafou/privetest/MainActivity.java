package com.example.dafou.privetest;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

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
        String type ="LOGIN";

        BackgroundLogin backglogin = new BackgroundLogin(this);
        backglogin.execute(type,username,password);
        if(backglogin.alertDialog.equals("login succes panos")){

            startActivity(new Intent(MainActivity.this, AdminAct.class));
        }else if(backglogin.alertDialog.equals("login succes")){
            startActivity(new Intent(MainActivity.this, Waitres.class));
        }
    }

}
