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

import com.sururiana.apimoviecatalogue.DetailActivity;
import com.sururiana.apimoviecatalogue.R;
import com.sururiana.apimoviecatalogue.api.MoviesRepository;
import com.sururiana.apimoviecatalogue.api.OnGetReleaseMovie;
import com.sururiana.apimoviecatalogue.model.Movie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.sururiana.apimoviecatalogue.DetailActivity.EXTRA_MOVIE;

public class ReleaseNotif extends BroadcastReceiver {
    int ID_NOTIF = 2;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
    Date date = new Date();
    MoviesRepository moviesRepository;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String now = format.format(date);
        getRelease(context, now, now);
    }

    private void getRelease(final Context context, final String data_gte, String data_lte) {
        moviesRepository = MoviesRepository.getInstance();
        moviesRepository.getRelease(data_gte, data_lte, new OnGetReleaseMovie() {
            @Override
            public void onSuccess(ArrayList<Movie> movies) {
                String title = movies.get(0).getTitle();
                String msg = title+context.getString(R.string.msg_released);
                int id = movies.get(0).getId();
                movieArrayList.addAll(movies);
                for (Movie movie : movies) {
                    if (movie.getReleaseDate().equals(data_gte)){
                        showNotification(context, title, msg, id);
                    }
                }
            }

            @Override
            public void onError() {
            }
        });
    }

    public void showNotification(Context context, String title, String msg, int id){
        String ID_CHANEL = "channel 2", NAME_CHANNEL = "alarmChanel";
        NotificationManager nm =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i = new Intent(context, DetailActivity.class);
        i.putExtra(EXTRA_MOVIE,movieArrayList.get(0));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id,i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri notifSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,msg)
                .setContentTitle(title)
                .setContentText(msg)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(msg))
                .setSound(notifSound)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentIntent(pendingIntent)
                .setColorized(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(ID_CHANEL, NAME_CHANNEL, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(R.color.colorPrimary);
            notificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(ID_CHANEL);

            Objects.requireNonNull(nm).createNotificationChannel(notificationChannel);
        }
        Objects.requireNonNull(nm).notify(id, builder.build());
    }

    public void setAlarm (Context context, String type, String msg, String time){
        cancelNotif(context);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, ReleaseNotif.class);
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

    public void cancelNotif(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, DailyNotif.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_NOTIF, i, 0);
        Objects.requireNonNull(am).cancel(pendingIntent);
    }
}
