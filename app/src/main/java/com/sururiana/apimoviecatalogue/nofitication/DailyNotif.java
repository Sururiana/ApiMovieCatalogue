package com.sururiana.apimoviecatalogue.nofitication;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.sururiana.apimoviecatalogue.MainActivity;
import com.sururiana.apimoviecatalogue.R;

import java.util.Calendar;
import java.util.Objects;

public class DailyNotif extends BroadcastReceiver {
    int ID_NOTIF = 1;
    @Override
    public void onReceive(Context context, Intent i) {
        showDailyNotif(context, context.getResources().getString(R.string.app_name),context.getString(R.string.msg_daily), ID_NOTIF);
    }

    private void showDailyNotif(Context context, String title, String msg, int id){
        String CHANNEL_ID = "chanel_1", CHANNEL_NAME = "alarmChanel";
        NotificationManager nm =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri notifSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, msg)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(msg)
                .setColorized(true)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(notifSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(R.color.colorPrimary);
            notificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);

            Objects.requireNonNull(nm).createNotificationChannel(notificationChannel);
        }
        Objects.requireNonNull(nm).notify(id, builder.build());
    }

    public void cancelNotif(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, DailyNotif.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_NOTIF, i, 0);
        Objects.requireNonNull(am).cancel(pendingIntent);
    }

    public void setAlarm (Context context, String type, String msg, String time){
        cancelNotif(context);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, DailyNotif.class);
        i.putExtra("type", type);
        i.putExtra("msg", msg);
        String[] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_NOTIF, i, 0);
        Objects.requireNonNull(am).setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
