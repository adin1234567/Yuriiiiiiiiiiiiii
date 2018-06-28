package edu.arizona.uas.jefferychang.hw3;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Date;
import java.util.concurrent.TimeUnit;


public class AlarmService extends IntentService {
    private static final String TAG = "AlarmService";
    //CHANGE THE NOTIFY DURATION HERE!!!////
    private static final long Notification_INTERVAL_S = TimeUnit.SECONDS.toSeconds(10);
    private BloodGlucose mBloodGlucose;
    private NotificationManager notifManager;
    final int NOTIFY_ID = 1002;

    public static Intent newIntent(Context context) {
        return new Intent(context, AlarmService.class);
    }

    public AlarmService() {
        super(TAG);
    }

    public static void setServiceAlarm(Context context) {
        Intent i = AlarmService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(
                context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), Notification_INTERVAL_S, pi);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
            Date today = new Date();
            BloodGlucoseHistory history = BloodGlucoseHistory.get(this);
            mBloodGlucose = history.getBloodGlucoseByDate(today);
            if(ShouldNotify(mBloodGlucose) ){

                String name = "my_package_channel";
                String id = "my_package_channel_1"; // The user-visible name of the channel.
                String description = "my_package_first_channel"; // The user-visible description of the channel.
                PendingIntent pendingIntent;
                NotificationCompat.Builder builder;
                builder = new NotificationCompat.Builder(this, id);

                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                if (notifManager == null) {
                    notifManager =
                            (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel mChannel = notifManager.getNotificationChannel(id);
                    if (mChannel == null) {
                        mChannel = new NotificationChannel(id, name, importance);
                        mChannel.setDescription(description);
                        notifManager.createNotificationChannel(mChannel);
                    }
                    builder.setContentTitle("Please Complete Today's Entry")  // required
                            .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                            .setContentText(this.getString(R.string.app_name))  // required
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setTicker("Please Complete Today's Entry");
                }
                else {
                    builder.setContentTitle("Please Complete Today's Entry")                           // required
                            .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                            .setContentText(this.getString(R.string.app_name))  // required
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setTicker("Please Complete Today's Entry")
                            .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                }
                Notification notification = builder.build();
                notifManager.notify(NOTIFY_ID, notification);
            }
        }
    public boolean ShouldNotify(BloodGlucose bloodGlucose){
        if(bloodGlucose == null){
            return true;
        }
        else if ((bloodGlucose.getBreakfast())==0 ||
                (bloodGlucose.getLunch())==0 ||
                (bloodGlucose.getDinner())==0 ||
               (bloodGlucose.getFasting())==0
                ){
            return true;
        }
        return false;
    }
}




