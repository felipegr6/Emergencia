package br.com.fgr.emergencia.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.ui.activities.LoginActivity;

public class GcmIntentService extends IntentService {

    public static final int NOTIFICATION_ID = (int) System.currentTimeMillis();
    private static final String TAG = "GcmIntentService";
    private NotificationManager mNotificationManager;

    public GcmIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.w("GCMIntentService", "Chegou aqui.");

        Bundle extras = intent.getExtras();
        String titulo = extras.getString("titulo");
        String mensagem = extras.getString("mensagem");

        if (titulo != null && mensagem != null)
            sendNotification(titulo, mensagem);
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);

    }

    private void sendNotification(String titulo, String msg) {

        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, LoginActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(titulo)
                        .setColor(0x00c12e2b)
                        .setLights(Color.RED, 1000, 2000)
                        .setVibrate(new long[]{250, 250, 250, 250})
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }

}