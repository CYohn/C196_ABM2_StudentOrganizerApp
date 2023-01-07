package UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.zybooks.c196_abm2_charity_yohn.R;

public class AlertBroadcastReceiver extends BroadcastReceiver {

    String channel_id = "studentOrganizerChannel";
    static int notificationId;

    @Override
    public void onReceive(Context context, Intent intent) {

        creatNotificationChannel(context, channel_id);

        Toast.makeText(context, intent.getStringExtra("alertCreatedToast"),
                Toast.LENGTH_LONG).show();

        Notification notify = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("alertMessage"))
                .setContentTitle(intent.getStringExtra("alertMessage"))
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId++, notify);


        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
    }

    private void creatNotificationChannel(Context context, String CHANNEL_ID){
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}