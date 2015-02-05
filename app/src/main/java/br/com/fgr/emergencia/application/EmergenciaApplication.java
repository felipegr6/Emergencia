package br.com.fgr.emergencia.application;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;

import io.fabric.sdk.android.Fabric;

public class EmergenciaApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        Fabric.with(this, new Crashlytics());

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "cHLwj5iphEObLK60BLhIhPYtaoYvUauF2AeLqFKA", "9IKqj8DCTuYCIjUBVBfe07iV4rmUQTw1m53wz0vt");

    }

}
