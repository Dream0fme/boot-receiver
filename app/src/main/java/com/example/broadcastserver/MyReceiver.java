package com.example.broadcastserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED) || intent.getAction().equals("android.intent.action.QUICKBOOT_POWERON")) {
            Toast.makeText(context.getApplicationContext(), "Service: Autorun", Toast.LENGTH_LONG).show();
            Log.d("myapp", "Service: Autorun");

            Intent App = new Intent(context, MainActivity.class);
            App.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(App);

            Intent serviceIntent = new Intent(context, MyService.class);
            serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");

            ContextCompat.startForegroundService(context, serviceIntent);
            /*
            Intent intentService = new Intent(context, MyService.class);
            intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(intentService);

             */
        }


        /*
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Intent intentService = new Intent(context, MyService.class);
                // intentService.addFlags(Intent.ACTION_SCREEN_ON);
                context.startService(intentService);
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Intent intentService = new Intent(context, MyService.class);
                // intentService.addFlags(Intent.ACTION_SCREEN_ON);
                context.startService(intentService);
            } else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
                Intent intentService = new Intent(context, MyService.class);
                // intentService.addFlags(Intent.ACTION_SCREEN_ON);
                context.startService(intentService);
            }
        }

         */

    }
}
