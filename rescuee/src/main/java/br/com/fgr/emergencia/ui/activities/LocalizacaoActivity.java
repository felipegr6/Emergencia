package br.com.fgr.emergencia.ui.activities;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.ui.fragments.ListaHospitaisFragment;
import br.com.fgr.emergencia.utils.Helper;

public class LocalizacaoActivity extends BaseActivity implements ConnectionCallbacks,
        OnConnectionFailedListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    private boolean pesquisado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = this.getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.vermelho_status));

        }

        pesquisado = false;

        if (checkPlayServices())
            buildGoogleApiClient();

    }

    @Override
    protected void onStart() {

        super.onStart();

        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();

    }

    @Override
    protected void onResume() {

        super.onResume();

        checkPlayServices();

    }

    @Override
    public void onPause() {

        super.onPause();

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.localizacao, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_filter) {
            startActivityForResult(new Intent(LocalizacaoActivity.this, ConfiguracaoActivity.class),
                    Helper.REQ_FILTRO_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_localizacao;
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (!pesquisado)
            listarHospitais();

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("LocalizacaoActivity", "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }

    private boolean checkPlayServices() {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {

            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
                GooglePlayServicesUtil.getErrorDialog(resultCode,
                        this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            else {

                Toast.makeText(getApplicationContext(),
                        "Seu dispositivo não suporta esta funcionallidade.", Toast.LENGTH_LONG)
                        .show();
                finish();

            }

            return false;

        }

        return true;

    }

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Helper.REQ_FILTRO_CODE && resultCode == RESULT_OK)
            recreate();

    }

    private void listarHospitais() {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        pesquisado = true;

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (mLastLocation != null)
            ft.replace(R.id.loc_fragment_container, ListaHospitaisFragment.newInstance(mLastLocation
                    .getLatitude(), mLastLocation.getLongitude()));
        else
            ft.replace(R.id.loc_fragment_container, ListaHospitaisFragment.newInstance(-23.552133, -46.6331418));

        ft.addToBackStack("Lista");
        ft.commit();

    }

}