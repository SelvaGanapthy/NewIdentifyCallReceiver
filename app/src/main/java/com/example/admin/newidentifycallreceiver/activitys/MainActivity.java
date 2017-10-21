package com.example.admin.newidentifycallreceiver.activitys;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.admin.newidentifycallreceiver.R;

public class MainActivity extends AppCompatActivity {
    public static final int requestcode_permisson = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermisson())
            Toast.makeText(getApplicationContext(), "permission success", Toast.LENGTH_SHORT).show();
        else
            requestPermission();
    }

    public boolean checkPermisson() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        return (result == PackageManager.PERMISSION_GRANTED) && (result1 == PackageManager.PERMISSION_GRANTED);

    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.RECORD_AUDIO}, requestcode_permisson);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case requestcode_permisson:
                if (grantResults.length > 0) {
                    boolean StoragePermisson = grantResults[0] == PackageManager.PERMISSION_DENIED;
                    boolean RecordPermisson = grantResults[1] == PackageManager.PERMISSION_DENIED;
                    if (StoragePermisson && RecordPermisson) {
                        Toast.makeText(getApplicationContext(), "Permission denied sure u accept it", Toast.LENGTH_SHORT).show();
                        requestPermission();
                    } else
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
}
