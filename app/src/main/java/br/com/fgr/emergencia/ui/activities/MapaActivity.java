package br.com.fgr.emergencia.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.directions.DirectionRequest;
import br.com.fgr.emergencia.models.directions.DirectionResponse;
import br.com.fgr.emergencia.models.directions.Etapa;
import br.com.fgr.emergencia.models.general.Coordenada;
import br.com.fgr.emergencia.utils.GoogleServices;
import br.com.fgr.emergencia.utils.Helper;
import br.com.fgr.emergencia.utils.ServiceGenerator;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    public void onMapReady(final GoogleMap googleMap) {

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

        DirectionRequest request = new DirectionRequest(new Coordenada(mLatitudeOrigem, mLongitudeOrigem),
                new Coordenada(mLatitudeDestino, mLongitudeDestino));

        GoogleServices repo = ServiceGenerator.createService(GoogleServices.class, Helper.URL_GOOGLE_BASE);

        repo.directions(request.getOrigem(), request.getDestino(), "pt-BR", true,
                Helper.getConfiguracoes(this).getModo(), new Callback<DirectionResponse>() {

                    @Override
                    public void success(DirectionResponse dResp, Response response) {

                        List<Etapa> etapas = dResp.getRota().get(0).getTrechos().get(0).getEtapas();
                        List<LatLng> coordIniciais = new ArrayList<>();

                        for (Etapa e : etapas) {

                            Coordenada c1 = e.getLocalidadeInicial();
                            Coordenada c2 = e.getLocalidadeFinal();

                            coordIniciais.add(new LatLng(c1.getLat(), c1.getLgn()));
                            coordIniciais.add(new LatLng(c2.getLat(), c2.getLgn()));

                        }

                        googleMap.addPolyline(new PolylineOptions()
                                .color(0xFFB71C1C)
                                .geodesic(true)
                                .addAll(coordIniciais));

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(MapaActivity.this, getString(R.string.erro_comum), Toast.LENGTH_SHORT)
                                .show();
                        Log.e("MapaActivity", error.getMessage() + "");

                    }

                });

    }

}