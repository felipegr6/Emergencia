package br.com.fgr.emergencia.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Helper {

    private static final String RESCUEE_PREFERENCES = "rescuee_preferences";
    private static final String GCM_REGID = "gcmRegId";
    private static final String RAIO_MAXIMO = "raio_maximo";

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

    public static float getRaioMaximo(Context context) {

        SharedPreferences preferences = context.getSharedPreferences(RESCUEE_PREFERENCES, Context.MODE_MULTI_PROCESS);
        float raio;

        raio = preferences.getFloat(RAIO_MAXIMO, -1);

        if (raio == -1)
            raio = 5;

        return raio;

    }

    public static boolean setRaioMaximo(Context context, float raioMaximo) {

        SharedPreferences preferences = context.getSharedPreferences(RESCUEE_PREFERENCES, Context.MODE_MULTI_PROCESS);

        final SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(RAIO_MAXIMO, raioMaximo);

        return editor.commit();

    }

}
