package br.com.fgr.emergencia.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.utils.Helper;

public class SplashActivity extends Activity {

    protected GoogleCloudMessaging gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();

        if (Helper.getRegistrationGCM(this).equals(""))
            new CadastroGCM(this).execute();
        else
            Log.w("RegId", Helper.getRegistrationGCM(this));

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                finish();

            }
        }, 3000);

    }

    private class CadastroGCM extends AsyncTask<Void, Void, Void> {

        private Context context;

        public CadastroGCM(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            if (gcm == null)
                gcm = GoogleCloudMessaging.getInstance(context);

            String regId = "";

            try {

                regId = gcm.register(getResources().getString(R.string.sender_id));
                Helper.setRegistrationGCM(context, regId);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Log.w("RegistrationID", regId);
            }

            return null;

        }

    }

}