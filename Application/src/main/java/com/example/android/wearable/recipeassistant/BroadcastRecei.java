package com.example.android.wearable.recipeassistant;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.ArrayList;

public class BroadcastRecei extends BroadcastReceiver {
    databaseHelper databaseHelper;
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    /**
     * This method is to fetch all user records from SQLite
     */

    @Override
    public void onReceive(Context context, Intent intent) {

        databaseHelper = new databaseHelper(context);

        if ( databaseHelper.CheckNotFavorite(0).isEmpty() == true) {
            NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainPage.class), PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(R.drawable.logo).setTicker("HETT").setWhen(System.currentTimeMillis())
                    .setNumber(1).setContentTitle("RecipeAssistant").setContentText("오늘은 하루도 즐거운 하루 되세요!")
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true).setPriority(Notification.PRIORITY_MAX);

            notificationmanager.notify(1, builder.build());
        } else {
            NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainPage.class), PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(R.drawable.logo).setTicker("HETT").setWhen(System.currentTimeMillis())
                    .setNumber(1).setContentTitle("RecipeAssistant").setContentText("오늘은 " + databaseHelper.CheckNotFavorite(0).get((int) Math.random() * databaseHelper.CheckNotFavorite(0).size()).getTitleText() + " 는 어떠세요?")
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true).setPriority(Notification.PRIORITY_MAX);

            notificationmanager.notify(1, builder.build());
        }

    }
}
