package com.sururiana.apimoviecatalogue;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.sururiana.apimoviecatalogue.nofitication.DailyNotif;
import com.sururiana.apimoviecatalogue.nofitication.ReleaseNotif;
import com.sururiana.apimoviecatalogue.nofitication.preference;

public class NotifActivity extends AppCompatActivity {
    preference preference;
    DailyNotif dailyNotif;
    ReleaseNotif releaseNotif;
    SharedPreferences prefDailyReminder, prefReleaseReminder;
    SharedPreferences.Editor editDR, editRR;
    Switch switchDaily, switchNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);
        dailyNotif = new DailyNotif();
        releaseNotif = new ReleaseNotif();
        switchDaily = findViewById(R.id.switchDaily);
        switchNew = findViewById(R.id.switchNew);

        preference = new preference(this);

        prefDailyReminder = getSharedPreferences("dailyReminder",MODE_PRIVATE);
        boolean checkDailyReminder = prefDailyReminder.getBoolean("Daily", false);
        switchDaily.setChecked(checkDailyReminder);

        prefReleaseReminder = getSharedPreferences("releaseReminder", MODE_PRIVATE);
        boolean checkReleaseReminder = prefReleaseReminder.getBoolean("Release", false);
        switchNew.setChecked(checkReleaseReminder);

        setDaily();
        setRelease();
    }

    public void setDaily() {
        editDR = prefDailyReminder.edit();
        switchDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editDR.putBoolean("Daily",true);
                    editDR.apply();
                    String msg = getResources().getString(R.string.msg_daily);
                    preference.setDailyTime("07:00");
                    preference.setDailyMsg(msg);
                    dailyNotif.setAlarm(NotifActivity.this,"reminderDaily",msg,"07:00");
                } else{
                    editDR.putBoolean("Daily", false);
                    editDR.apply();
                    dailyNotif.cancelNotif(NotifActivity.this);
                }
            }
        });
    }
    public void setRelease(){
        editRR = prefReleaseReminder.edit();
        switchNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editRR.putBoolean("Release", true);
                    editRR.apply();
                    String msg = getResources().getString(R.string.msg_released);
                    preference.setReleaseTime("08:00");
                    preference.setReleaseMsg(msg);
                    releaseNotif.setAlarm(NotifActivity.this, "reminderRelease",msg,"08:00");
                } else {
                    editRR.putBoolean("Release", false);
                    editRR.apply();
                    releaseNotif.cancelNotif(NotifActivity.this);
                }
            }
        });
    }
}
