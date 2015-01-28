package br.com.fgr.emergencia.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Helper {

    private static final String RESCUEE_PREFERENCES = "rescuee_preferences";
    private static final String GCM_REGID = "gcmRegId";

    public static String getRegistrationGCM(Context context) {

        SharedPreferences preferences = context.getSharedPreferences(RESCUEE_PREFERENCES, Context.MODE_MULTI_PROCESS);

        return preferences.getString(GCM_REGID, "");

    }

    public static boolean setRegistrationGCM(Context context, String registrationID) {

        SharedPreferences preferences = context.getSharedPreferences(RESCUEE_PREFERENCES, Context.MODE_MULTI_PROCESS);

        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(GCM_REGID, registrationID);

        return editor.commit();

    }

}
