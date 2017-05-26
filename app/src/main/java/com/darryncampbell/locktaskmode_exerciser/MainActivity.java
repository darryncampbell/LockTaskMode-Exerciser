package com.darryncampbell.locktaskmode_exerciser;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "Lock Task Example";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startLockTaskButton = (Button) findViewById(R.id.btn_startLockTaskMode);
        startLockTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Starting lock task mode", Toast.LENGTH_SHORT).show();
                startLockTask();
            }
        });

        Button stopLockTaskButton = (Button) findViewById(R.id.btn_stopLockTaskMode);
        stopLockTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Stopping lock task mode", Toast.LENGTH_SHORT).show();
                try
                {
                    stopLockTask();
                }
                catch (SecurityException ex)
                {
                    Log.e(TAG, "Security Exception: " + ex.getMessage());
                    Toast.makeText(getApplicationContext(), "SECURITY ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button getLockTaskButton = (Button) findViewById(R.id.btn_getLockTaskMode);
        getLockTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
                String szLockTaskState = "";
                int ilockTaskState = -1;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    ilockTaskState = activityManager.getLockTaskModeState();
                }
                switch (ilockTaskState)
                {
                    case ActivityManager.LOCK_TASK_MODE_NONE:
                        szLockTaskState = "None";
                        break;
                    case ActivityManager.LOCK_TASK_MODE_PINNED:
                        szLockTaskState = "Pinned";
                        break;
                    case ActivityManager.LOCK_TASK_MODE_LOCKED:
                        szLockTaskState = "Locked";
                        break;
                    default:
                        szLockTaskState = "Unknown";
                }
                Toast.makeText(getApplicationContext(), "State: " + szLockTaskState, Toast.LENGTH_SHORT).show();
            }
        });

        Button launchCalculatorButton = (Button) findViewById(R.id.btn_launchCalculator);
        launchCalculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<HashMap<String,Object>> items =new ArrayList<HashMap<String,Object>>();
                final PackageManager pm = getPackageManager();
                List<PackageInfo> packs = pm.getInstalledPackages(0);
                for (PackageInfo pi : packs)
                {
                    if( pi.packageName.toString().toLowerCase().contains("calcul")){
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("appName", pi.applicationInfo.loadLabel(pm));
                        map.put("packageName", pi.packageName);
                        items.add(map);
                    }
                }
                if(items.size()>=1)
                {
                    String packageName = (String) items.get(0).get("packageName");
                    Intent i = pm.getLaunchIntentForPackage(packageName);
                    if (i != null)
                        startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Calculator not found on this device", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
