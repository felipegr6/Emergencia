package br.com.fgr.emergencia.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.ui.activities.MainActivity;

public class GcmIntentService extends GcmListenerService {

    public static final int NOTIFICATION_ID = (int) System.currentTimeMillis();
    private static final String TAG = "GcmIntentService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {

        Log.w(TAG, "Chegou aqui.");

        String title = data.getString("titulo");
        String message = data.getString("mensagem");

        if (title != null && message != null)
            sendNotification(title, message);

    }

    private void sendNotification(String title, String msg) {

        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent1 = new Intent(this, MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent1, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setColor(0x00c12e2b)
                        .setAutoCancel(true)
                        .setLights(Color.RED, 1000, 2000)
                        .setSound(defaultSoundUri)
                        .setVibrate(new long[]{250, 250, 250, 250})
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }

}
