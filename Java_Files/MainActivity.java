package com.team.skinlesion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;



import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView searchBT,profileBT,exitBT,settingsBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Variables.isLoggedIn=(Boolean)Variables.getValues(getApplicationContext()).get(0);
        if(!Variables.isLoggedIn){
            Intent in = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(in);
            finish();
        }
        else{
                checkStoragePermission();
            Variables.uname=(String)Variables.getValues(getApplicationContext()).get(1);
            Variables.passwd=(String)Variables.getValues(getApplicationContext()).get(2);
            messageDialog(Variables.uname+","+Variables.passwd);
        }

        searchBT = (TextView)findViewById(R.id.searchBT);
        searchBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageDialog("Feature not enabled");
            }
        });

        profileBT = (TextView)findViewById(R.id.profileBT);
        profileBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //messageDialog("Feature not enabled");
                //Intent in = new Intent(MainActivity.this,ProfileActivity.class);
                //startActivity(in);
                Intent in = new Intent(MainActivity.this,PhotoUploadActivity.class);
                startActivity(in);
            }
        });
        settingsBT = findViewById(R.id.settingsBT);
        settingsBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //messageDialog("Feature not enabled");

                //Intent in = new Intent(MainActivity.this,SettingsActivity.class);
                //startActivity(in);
            }
        });
        exitBT = (TextView)findViewById(R.id.exitBT);
        exitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variables.storeValues(getApplicationContext(),false,"","");
                Intent in = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(in);
                finish();

            }
        });
    }


    public void showExit(){
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Do you wish to exit ? ")
                .setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int id) {
                                finish();
                            }
                        }
                )
                .setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int id) {
                                //Toast.makeText(LoginActivity.this, "Feature Not Active", Toast.LENGTH_SHORT).show();

                            }
                        }
                ).show();
    }
    public void messageDialog(final String msg){
        new AlertDialog.Builder(this)
                .setTitle("Information !")
                .setMessage(msg)
                .setNeutralButton(
                        "Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dlg, int id) {
                                //finish();
                            }
                        }
                ).show();


    }
    public void showPermissionLogout(){
        new AlertDialog.Builder(this)
                .setTitle("Permission Error")
                .setMessage("Permission needed. Do you wish to logout ? ")
                .setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int id) {

                                Variables.fromLogin = false;
                                Intent in = new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(in);
                                finish();
                            }
                        }
                )
                .setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int id) {
                                //Toast.makeText(LoginActivity.this, "Feature Not Active", Toast.LENGTH_SHORT).show();
                                checkStoragePermission();
                            }
                        }
                ).show();
    }
    ///////////////////////////////////////////////////////////
    ////////////Check Permissions//////////////
    ///////////////////////////////
    public final int WRITE_STORAGE_PERMISSION=101;
    static boolean  WRITE_STORAGE_FLAG =false;



    public void checkStoragePermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Need this permission ", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_STORAGE_PERMISSION);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_STORAGE_PERMISSION);

            }
        } else {
            //Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            WRITE_STORAGE_FLAG=true;
            ///createDIR(Variables.PIC_DIR);

        }

    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case WRITE_STORAGE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(this, "Storage Permission granted", Toast.LENGTH_SHORT).show();
                    WRITE_STORAGE_FLAG=true;
                    //createDIR(Variables.PIC_DIR);

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    showPermissionLogout();
                    WRITE_STORAGE_FLAG=false;
                }
                return;
            }

        }
    }
    public boolean checkNetwork(){

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean status=activeNetwork != null&& activeNetwork.isConnectedOrConnecting();
        //Toast.makeText(this, "Network"+status, Toast.LENGTH_SHORT).show();
        return status;
    }
    ///////////////////////////////

}
