package com.team.skinlesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText unameET =null,passwdET=null;
    Button okBT,cancelBT,adminBT;
    String uname,passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        unameET = (EditText)findViewById(R.id.unameET);
        passwdET = (EditText)findViewById(R.id.passwdET);


        okBT = (Button)findViewById(R.id.okBT);

        okBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = unameET.getText().toString();
                passwd = passwdET.getText().toString();

                if(uname.length()==0 || passwd.length()==0){
                    Toast.makeText(LoginActivity.this, "Please fill login details", Toast.LENGTH_LONG).show();
                    return;
                }

                if(uname.equals(Variables.uname)&&passwd.equals(Variables.passwd)){
                    Variables.storeValues(getApplicationContext(),true,uname,passwd);
                    Intent in = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(in);
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Sorry invalid credentials!", Toast.LENGTH_SHORT).show();
                    unameET.setText("");
                    passwdET.setText("");
                }



            }
        });

        cancelBT = (Button)findViewById(R.id.cancelBT);

        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adminBT = (Button)findViewById(R.id.regBT);

        adminBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(in);
                finish();
            }
        });



    }
}
