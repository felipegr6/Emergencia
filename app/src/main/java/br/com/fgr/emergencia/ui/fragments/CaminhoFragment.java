package br.com.fgr.emergencia.ui.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.directions.DirectionResponse;

public class CaminhoFragment extends MapFragment {

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

    public CaminhoFragment() {
    }

    public static CaminhoFragment newInstance(double param1, double param2, double param3, double param4) {

        CaminhoFragment fragment = new CaminhoFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        double latMedia = (mLatitudeOrigem + mLatitudeDestino) / 2;
        double lgnMedia = (mLongitudeOrigem + mLongitudeDestino) / 2;

        View view = inflater.inflate(R.layout.fragment_caminho, container, false);

        new DownloadJsonDistance(getActivity()).execute();

        iniciarMapa();

        MarkerOptions destino = new MarkerOptions()
                .position(new LatLng(mLatitudeDestino, mLongitudeDestino))
                .title("Destino")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latMedia, lgnMedia))
                .zoom(13)
                .build();

        googleMap.addMarker(destino);

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        return view;

    }

    private void iniciarMapa() {

        if (googleMap == null) {

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

    private class DownloadJsonDistance extends AsyncTask<Void, Void, Void> {

        private RequestQueue mRequestQueue;
        private Cache cache;
        private URL url;
        private InputStream is;

        public DownloadJsonDistance(Context context) {

            cache = new DiskBasedCache(context.getCacheDir(), 16 * 1024 * 1024);
            mRequestQueue = new RequestQueue(cache, new BasicNetwork(new HurlStack()));
            mRequestQueue.start();

        }

        @Override
        protected Void doInBackground(Void... params) {

            StringBuffer stringBuffer = new StringBuffer();

            String urlString = "http://maps.googleapis.com/maps/api/directions/json?origin="
                    + mLatitudeOrigem
                    + ","
                    + mLongitudeOrigem
                    + "&destination="
                    + mLatitudeDestino
                    + ","
                    + mLongitudeDestino
                    + "&sensor=false&units=metric&mode=driving";

            Log.w(TAG, urlString);

            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                is = urlConnection.getInputStream();
                urlConnection.connect();

                InputStreamReader inputStream = new InputStreamReader(is);
                BufferedReader bufferedReader = new BufferedReader(inputStream);

                String line = null;

                line = bufferedReader.readLine();

                while (line != null) {

                    Log.w("Result", line);

                    stringBuffer.append(line);
                    line = bufferedReader.readLine();

                }

                Log.w("Result", stringBuffer.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // DirectionResponse directionResponse = converterJson(stringBuffer.toString());

            /*

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    Log.w("ResponseCaminhoFragment", response);
                    Log.w("Tamanho", response.toString().length() + "");
                    // JsonResponse jsonResponse = converterJson(response);

                }

            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e(TAG, error.getMessage());

                }

            });

            mRequestQueue.add(stringRequest);
            */

            return null;

        }

        private DirectionResponse converterJson(String jsonConteudo) {

            Gson conteudos = new Gson();

            DirectionResponse respostas = conteudos.fromJson(jsonConteudo,
                    DirectionResponse.class);

            return respostas;

        }

    }

}
