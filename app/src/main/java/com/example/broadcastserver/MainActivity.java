package com.example.broadcastserver;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    Button btnStartService, btnStopService;
    Intent intentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartService = findViewById(R.id.buttonStartService);
        btnStopService = findViewById(R.id.buttonStopService);
        intentService = new Intent(this, MyService.class);

        final ComponentName onBootReceiver = new ComponentName(getApplication().getPackageName(), MyReceiver.class.getName());
        if (getPackageManager().getComponentEnabledSetting(onBootReceiver) != PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            getPackageManager().setComponentEnabledSetting(onBootReceiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        }

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
                btnStartService.setEnabled(false);
                btnStopService.setEnabled(true);

            }
        });
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
                btnStartService.setEnabled(true);
                btnStopService.setEnabled(false);
            }
        });
    }

    public void click_Service(View v) {
        if (!isMyServiceRunning(MyService.class)) {
            startService(intentService);
        } else {
            stopService(intentService);
        }
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, MyService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");

        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, MyService.class);
        stopService(serviceIntent);
    }

    public void checkRunning(){
        if (!isMyServiceRunning(MyService.class)) {
            btnStartService.setEnabled(true);
            btnStopService.setEnabled(false);
        } else {
            btnStartService.setEnabled(false);
            btnStopService.setEnabled(true);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
