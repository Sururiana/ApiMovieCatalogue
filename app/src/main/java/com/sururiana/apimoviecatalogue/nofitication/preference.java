package com.sururiana.apimoviecatalogue.nofitication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class preference {
    private SharedPreferences.Editor editor;


    @SuppressLint("CommitPrefEdits")
    public preference (Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("reminderPref",Context.MODE_PRIVATE);
        editor =sharedPreferences.edit();
    }
    public void setReleaseTime (String time) {
        editor.putString("DailyReminder", time);
        editor.commit();
    }
    public void setReleaseMsg (String msg) {
        editor.putString("msgReleasse", msg);
    }


    public void setDailyTime (String time) {
        editor.putString("DailyReminder", time);
        editor.commit();
    }
    public void setDailyMsg (String msg) {
        editor.putString("msgDaily", msg);
    }
}
