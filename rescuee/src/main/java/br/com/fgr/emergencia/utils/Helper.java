package br.com.fgr.emergencia.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import br.com.fgr.emergencia.models.general.Configuration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Helper {

    public static final int OFFSET_RAIO = 5;
    public static final int OFFSET_HOSPITAIS = 5;
    public static final int CONST_RAIO = 1;
    public static final int CONST_HOSPITAIS = 2;
    public static final int REQ_FILTRO_CODE = 1001;
    public static final String URL_GOOGLE_BASE = "https://maps.googleapis.com/maps/api/";

    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    private static final String RESCUEE_PREFERENCES = "rescuee_preferences";
    private static final String GCM_REGID = "gcmRegId";
    private static final String RAIO_MAXIMO = "raio_maximo";
    private static final String RAIO = "raio";
    private static final String TUTORIAL = "tutorial";
    private static final String HOSPITAIS = "hospitais";
    private static final String EMAIL_PATTERN = "\\b[a-z0-9._%+-]+@(?:[a-z0-9-]+\\.)+[a-z]{2,4}\\b";
    private static final int offsetRaio = 2;
    private static final int offsetHospitais = 5;
    private static final String MEIO_TRANSPORTE = "meio";

    public static Map<Integer, String> MAP_MEIO_TRANSPORTE;
    private static Configuration configuracao;

    static {

        MAP_MEIO_TRANSPORTE = new HashMap<>();

        MAP_MEIO_TRANSPORTE.put(0, "driving");
        MAP_MEIO_TRANSPORTE.put(1, "walking");
    }

    private Helper() {

    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void setConfiguracoes(Context context, Configuration config) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        configuracao = config;

        final SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(RAIO, config.getRadius());
        editor.putInt(HOSPITAIS, config.getHospitals());
        editor.putString(MEIO_TRANSPORTE, config.getMode());

        editor.apply();
    }

    public static Configuration getConfiguracoes(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Configuration configuration;

        configuration =
            new Configuration(preferences.getInt(RAIO, 0), preferences.getInt(HOSPITAIS, 0),
                preferences.getString(MEIO_TRANSPORTE, MAP_MEIO_TRANSPORTE.get(0)));

        return configuration;
    }

    public static boolean setRegistrationGCM(Context context, String registrationID) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(GCM_REGID, registrationID);

        return editor.commit();
    }

    public static String getRegistrationGCM(Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        return preferences.getString(GCM_REGID, "");
    }

    public static boolean setFirstTimeTutorial(Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(TUTORIAL, false);

        return editor.commit();
    }

    public static boolean getFirstTimeTutorial(Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        return preferences.getBoolean(TUTORIAL, true);
    }

    public static void setSentTokenToServer(Context context, boolean sentToken) {

        SharedPreferences sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences.edit().putBoolean(Helper.SENT_TOKEN_TO_SERVER, sentToken).apply();
    }

    public static boolean isSentTokenToServer(Context context) {

        SharedPreferences sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getBoolean(Helper.SENT_TOKEN_TO_SERVER, false);
    }

    public static boolean validateEmail(String email) {

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email.toLowerCase());

        return matcher.matches();
    }

    public static boolean validatePassword(String senha) {
        return senha != null && senha.length() >= 6;
    }

    public static String formatarInformacao(int constante, int progress, boolean isExibicao) {
        StringBuilder stringBuilder = new StringBuilder();

        switch (constante) {
            case CONST_RAIO:
                stringBuilder.append((offsetRaio * progress + 1));
                if (isExibicao) stringBuilder.append("km");
                break;
            case CONST_HOSPITAIS:
                stringBuilder.append(offsetHospitais * (progress + 1));
                break;
            default:
                return null;
        }

        return stringBuilder.toString();
    }
}
