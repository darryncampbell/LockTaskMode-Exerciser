package com.darryncampbell.locktaskmode_exerciser;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_CALCULATOR);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
