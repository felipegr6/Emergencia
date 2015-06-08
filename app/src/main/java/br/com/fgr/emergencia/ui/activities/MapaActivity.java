package br.com.fgr.emergencia.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.fgr.emergencia.R;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String LAT_ORIGEM = "latitude_origem";
    public static final String LGN_ORIGEM = "longitude_origem";
    public static final String LAT_DESTINO = "latitude_destino";
    public static final String LGN_DESTINO = "longitude_destino";

    private double mLatitudeOrigem;
    private double mLongitudeOrigem;
    private double mLatitudeDestino;
    private double mLongitudeDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        if (getIntent() != null) {

            mLatitudeOrigem = getIntent().getDoubleExtra(LAT_ORIGEM, 0.0);
            mLongitudeOrigem = getIntent().getDoubleExtra(LGN_ORIGEM, 0.0);
            mLatitudeDestino = getIntent().getDoubleExtra(LAT_DESTINO, 0.0);
            mLongitudeDestino = getIntent().getDoubleExtra(LGN_DESTINO, 0.0);

        }

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        double latMedia = (mLatitudeOrigem + mLatitudeDestino) / 2;
        double lgnMedia = (mLongitudeOrigem + mLongitudeDestino) / 2;

        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);


        MarkerOptions destino = new MarkerOptions()
                .position(new LatLng(mLatitudeDestino, mLongitudeDestino))
                .title("Destino")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        CameraUpdate center = CameraUpdateFactory.newLatLngZoom(new LatLng(latMedia, lgnMedia), 15);

        Marker marker = googleMap.addMarker(destino);

        marker.showInfoWindow();

        googleMap.moveCamera(center);

    }

}