package br.com.fgr.emergencia.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.fgr.emergencia.R;

public class MapaFragment extends MapFragment {

    protected static final String TAG = "CaminhoFragment";
    private static final String LAT_ORIGEM = "latitude_origem";
    private static final String LGN_ORIGEM = "longitude_origem";
    private static final String LAT_DESTINO = "latitude_destino";
    private static final String LGN_DESTINO = "longitude_destino";
    protected double mLatitudeOrigem;
    protected double mLongitudeOrigem;
    protected double mLatitudeDestino;
    protected double mLongitudeDestino;
    private GoogleMap googleMap;

    public MapaFragment() {
    }

    public static MapaFragment newInstance(double param1, double param2, double param3, double param4) {

        MapaFragment fragment = new MapaFragment();
        Bundle args = new Bundle();

        args.putDouble(LAT_ORIGEM, param1);
        args.putDouble(LGN_ORIGEM, param2);
        args.putDouble(LAT_DESTINO, param3);
        args.putDouble(LGN_DESTINO, param4);

        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            mLatitudeOrigem = getArguments().getDouble(LAT_ORIGEM);
            mLongitudeOrigem = getArguments().getDouble(LGN_ORIGEM);
            mLatitudeDestino = getArguments().getDouble(LAT_DESTINO);
            mLongitudeDestino = getArguments().getDouble(LGN_DESTINO);

        }

        getActivity().setTitle(getResources().getString(R.string.title_fragment_map));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        double latMedia = (mLatitudeOrigem + mLatitudeDestino) / 2;
        double lgnMedia = (mLongitudeOrigem + mLongitudeDestino) / 2;

        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        iniciarMapa();

        MarkerOptions destino = new MarkerOptions()
                .position(new LatLng(mLatitudeDestino, mLongitudeDestino))
                .title("Destino")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        CameraUpdate center = CameraUpdateFactory.newLatLngZoom(new LatLng(latMedia, lgnMedia), 15);

        Marker marker = googleMap.addMarker(destino);

        marker.showInfoWindow();

        googleMap.moveCamera(center);

        return view;

    }

    private void iniciarMapa() {

        if (googleMap == null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                googleMap = ((MapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
            else
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null)
                Toast.makeText(getActivity().getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();

        }

        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true); // true to enable
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

    }

}
