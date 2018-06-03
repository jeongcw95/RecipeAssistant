package com.example.android.wearable.recipeassistant;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;

import java.util.Calendar;

public class MainPage extends Activity {
    databaseHelper dh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        dh = new databaseHelper(getApplicationContext());
        new AlarmHATT(getApplicationContext()).Alarm();

    }

    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context = context;
        }
        public void Alarm() {
            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MainPage.this, BroadcastRecei.class);

            PendingIntent sender = PendingIntent.getBroadcast(MainPage.this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기

            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 10, 0);

            //알람 예약
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

        }
    }

    public void StartButtonClicked(View view) {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }

    public void FavoriteButtonClicked(View view) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }

    public void LogOutButtonClicked(View view){
        Splash.AutoLoginChecked = 0;
        Splash.LoginUser.setAutologin(0);
        dh.updateUser(Splash.LoginUser);
        if(Splash.AutoLoginActive == true) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}