package br.com.fgr.emergencia.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import br.com.fgr.emergencia.utils.Helper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override protected void onHandleIntent(Intent intent) {
        try {

            String token = FirebaseInstanceId.getInstance().getToken();
            // [END get_token]
            Log.e(TAG, "GCM Registration Token: " + token);

            sendRegistrationToServer(token);

            Helper.setSentTokenToServer(this, true);
        } catch (Exception e) {
            Log.e(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            Helper.setSentTokenToServer(this, false);
        }

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Helper.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Persist registration to third-party servers.
     * <p/>
     * Modify this method to associate the user's GCM registration token with any server-side
     * account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(final String token) {
        // Add custom implementation, as needed.
        Log.e("token", token);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Usuario");
        query.whereEqualTo("regId", token);

        query.findInBackground((list, e) -> {
            if (list.isEmpty()) {
                ParseObject usuario = new ParseObject("Usuario");

                usuario.put("email", "");
                usuario.put("senha", "");
                usuario.put("regId", token);
                usuario.saveInBackground();
            }
        });
    }
}
