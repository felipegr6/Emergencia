package br.com.fgr.emergencia.application;

import android.support.multidex.MultiDexApplication;
import br.com.fgr.emergencia.BuildConfig;
import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class EmergencyApplication extends MultiDexApplication {

    @Override public void onCreate() {

        super.onCreate();

        if (BuildConfig.CRASH_REPORT) Fabric.with(this, new Crashlytics());

        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "cHLwj5iphEObLK60BLhIhPYtaoYvUauF2AeLqFKA",
            "9IKqj8DCTuYCIjUBVBfe07iV4rmUQTw1m53wz0vt");
    }
}
