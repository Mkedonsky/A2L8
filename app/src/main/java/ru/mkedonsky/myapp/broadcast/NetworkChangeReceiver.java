package ru.mkedonsky.myapp.broadcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import ru.mkedonsky.myapp.R;


public class NetworkChangeReceiver extends BroadcastReceiver {
    private int messageId = 1000;

    @Override
    public void onReceive(final Context context, final Intent intent) {
    int status = NetworkUtil.getConnectivityStatusString(context);

            if (status == NetworkUtil.NETWORK_STATUS_WIFI){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                        .setSmallIcon(R.mipmap.baseline_signal_wifi_4_bar_white_18dp)
                        .setContentTitle("Attention")
                        .setContentText("WiFi connection");
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                assert notificationManager != null;
                notificationManager.notify(messageId++, builder.build());
            }

            if (status == NetworkUtil.NETWORK_STATUS_MOBILE){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                    .setSmallIcon(R.mipmap.baseline_signal_cellular_4_bar_white_18dp)
                    .setContentTitle("Attention")
                    .setContentText("Cellular connection");
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.notify(messageId++, builder.build());
        }

            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                    .setSmallIcon(R.mipmap.baseline_signal_wifi_off_white_18dp,R.mipmap.baseline_signal_cellular_off_white_18dp)
                    .setContentTitle("Attention")
                    .setContentText("Not connection!");
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.notify(messageId++, builder.build());
        }
    }
}




