package com.dino.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    Button check;
    boolean post;
    String[] perm = new String[]{
            Manifest.permission.POST_NOTIFICATIONS
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check = (Button) findViewById(R.id.button);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!post){
                    requestPermissionsNotification();
                } else {
                    Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT );
                }
            }
        });
    }

    private void requestPermissionsNotification() {

        if(ContextCompat.checkSelfPermission (MainActivity.this,perm[0]) == PackageManager.PERMISSION_GRANTED)    {
            post = true;
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                Log.d("Per"," 1");
            } else {
                Log.d("Per"," 2");
            }
            request.launch(perm[0]);
        }
    }

    private ActivityResultLauncher<String> request =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted->{
                if (isGranted){
                    post = true;
                }else {
                    post = false;
                    showPermissionDialog(" Notif Perm");
                }
            });

    public void showPermissionDialog(String s) {
        new  AlertDialog.Builder(
                MainActivity.this
        ).setTitle("Alert for Permition")
                .setPositiveButton("Setings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent rInt = new Intent();
                        rInt.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",getPackageName(), null);
                        rInt.setData(uri);
                        startActivity(rInt);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .show();
    }

}