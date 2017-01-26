package br.com.fgr.emergencia.ui.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.events.PermissionEvent;
import br.com.fgr.emergencia.ui.activities.LocationActivity;
import br.com.fgr.emergencia.utils.Helper;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MainFragment extends BaseFragment {
    public static Fragment newInstance() {
        return new MainFragment();
    }

    public MainFragment() {

    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override public void onResume() {
        super.onResume();

        EventBus.getDefault().registerSticky(this);
    }

    @Override public void onPause() {

        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);

        super.onPause();
    }

    @SuppressWarnings("unused") @OnClick(R.id.button_emergencia) public void searchHospitals() {
        if (Helper.isOnline(getActivity()) && hasAnyLocationPermission()) {
            Intent intent = new Intent(getActivity(), LocationActivity.class);
            startActivity(intent);
        } else if (!hasAnyLocationPermission()) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 16);
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.erro_sem_conexao),
                Toast.LENGTH_SHORT).show();
        }
    }

    @Override public int getLayout() {
        return R.layout.fragment_principal;
    }

    private boolean hasAnyLocationPermission() {

        boolean hasFineLocation = ActivityCompat.checkSelfPermission(getActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean hasCoarseLocation = ActivityCompat.checkSelfPermission(getActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        return hasCoarseLocation || hasFineLocation;
    }

    @SuppressWarnings("unused") public void onEvent(PermissionEvent event) {
        Intent intent = new Intent(getActivity(), LocationActivity.class);
        startActivity(intent);
    }
}
