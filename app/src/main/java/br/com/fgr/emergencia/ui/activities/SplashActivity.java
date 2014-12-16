package br.com.fgr.emergencia.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import br.com.fgr.emergencia.ui.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread background = new Thread() {

            public void run() {

                try {

                    sleep(3000);

                    Intent intent = new Intent(SplashActivity.this,
                            MainActivity.class);

                    startActivity(intent);

                    finish();

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            }

        };

        background.start();

    }


}
