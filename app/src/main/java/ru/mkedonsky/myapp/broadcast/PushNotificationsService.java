package ru.mkedonsky.myapp.broadcast;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

public class PushNotificationsService extends FirebaseMessagingService {
    private static final String TAG = "FireBase" ;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        try {
            long ticketId = Long.parseLong(Objects.requireNonNull(remoteMessage.getData().get("ticketId")));
            if (remoteMessage.getNotification() != null) {
                Log.d("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            }
            Toast.makeText(this, String.valueOf(ticketId), Toast.LENGTH_LONG).show();
        } catch (NullPointerException exc) {
            Toast.makeText(this, "Exception!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        // sendRegistrationToServer(token);
    }
}
