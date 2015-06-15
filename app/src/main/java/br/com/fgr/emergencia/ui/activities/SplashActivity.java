package br.com.fgr.emergencia.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.List;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.utils.Helper;

public class SplashActivity extends AppCompatActivity {

    protected GoogleCloudMessaging gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                if (Helper.getRegistrationGCM(SplashActivity.this).equals(""))
                    new CadastroGCM(SplashActivity.this).execute();
                else {

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);

                    finish();

                    Log.w("RegId", Helper.getRegistrationGCM(SplashActivity.this));

                }

            }
        }, 3000);

    }

    private class CadastroGCM extends AsyncTask<Void, Void, Void> {

        private Context context;

        public CadastroGCM(Context context) {
            this.context = context;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);

            finish();

        }

        @Override
        protected Void doInBackground(Void... params) {

            if (gcm == null)
                gcm = GoogleCloudMessaging.getInstance(context);

            final String regId;

            try {

                regId = gcm.register(getResources().getString(R.string.sender_id));
                Helper.setRegistrationGCM(context, regId);

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Usuario");
                query.whereEqualTo("regId", regId);

                query.findInBackground(new FindCallback<ParseObject>() {

                    @Override
                    public void done(List<ParseObject> list, ParseException e) {

                        if (list.isEmpty()) {

                            ParseObject usuario = new ParseObject("Usuario");

                            usuario.put("email", "");
                            usuario.put("senha", "");
                            usuario.put("regId", regId);

                            usuario.saveInBackground();

                        }

                    }

                });

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

    }

}