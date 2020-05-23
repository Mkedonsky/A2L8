package ru.mkedonsky.myapp.broadcast;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import ru.mkedonsky.myapp.R;

public class MyReceiver extends BroadcastReceiver {
    private int messageId = 1000;

    @Override
        public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                .setSmallIcon(R.mipmap.baseline_battery_alert_white_18dp)
                .setContentTitle("Attention!!")
                .setContentText("Battery is low!");
        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(messageId++,builder.build());
        }
}


