package br.com.fgr.emergencia.ui.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.distancematrix.DistanceMatrixResponse;
import br.com.fgr.emergencia.models.distancematrix.Elementos;
import br.com.fgr.emergencia.models.general.Coordenada;
import br.com.fgr.emergencia.models.general.Hospital;
import br.com.fgr.emergencia.utils.Helper;
import br.com.fgr.emergencia.utils.HospitalAdapter;

public class ListaHospitaisFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static double LAT_USUARIO = 0.0;
    public static double LGN_USUARIO = 0.0;
    private final String TAG = this.getClass().getSimpleName();
    protected GoogleApiClient mGoogleApiClient;
    protected RecyclerView recyclerView;
    protected GestureDetectorCompat detector;
    private List<Hospital> hospitais;

    public ListaHospitaisFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_localizacao,
                container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        detector = new GestureDetectorCompat(getActivity(), new RecyclerViewOnGestureListener());

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

                detector.onTouchEvent(motionEvent);

                return false;

            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

        });

        buildGoogleApiClient();

        new DownloadJson(getActivity()).execute();

        return view;

    }

    @Override
    public void onResume() {

        super.onResume();

        mGoogleApiClient.connect();

        LAT_USUARIO = 0.0;
        LGN_USUARIO = 0.0;

    }

    @Override
    public void onPause() {

        super.onPause();

        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();

    }

    @Override
    public void onConnected(Bundle bundle) {

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {

            LAT_USUARIO = mLastLocation.getLatitude();
            LGN_USUARIO = mLastLocation.getLongitude();

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());

    }

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    public void onLocationChanged(Location location) {

        LAT_USUARIO = location.getLatitude();
        LGN_USUARIO = location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {

            View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            int position = recyclerView.getChildPosition(view);

            Hospital hosp = hospitais.get(position);

            MapaFragment mCaminhoFragment = MapaFragment.newInstance(LAT_USUARIO,
                    LGN_USUARIO,
                    hosp.getLocalizacao().getLat(),
                    hosp.getLocalizacao().getLgn());

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.loc_fragment_container, mCaminhoFragment);
            fragmentTransaction.commit();

            return super.onSingleTapConfirmed(motionEvent);

        }

        public void onLongPress(MotionEvent motionEvent) {

            View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            int position = recyclerView.getChildPosition(view);

            // handle long press

            super.onLongPress(motionEvent);

        }

    }

    private class DownloadJson extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialogInternet;
        private Context context;
        private RequestQueue mRequestQueue;
        private StringRequest mStringRequest;
        private HospitalAdapter hospitalAdapter;

        public DownloadJson(Context context) {

            this.dialogInternet = new ProgressDialog(context);
            this.context = context;
            this.mRequestQueue = Volley.newRequestQueue(context);

            mRequestQueue.getCache().clear();

        }

        @Override
        protected void onPreExecute() {

            String title = getResources().getString(R.string.titulo_dialog_hospitais);
            String message = getResources().getString(R.string.descricao_dialog_hospitais);
            boolean indeterminate = true;

            dialogInternet.setTitle(title);
            dialogInternet.setMessage(message);
            dialogInternet.setCancelable(false);
            dialogInternet.setIndeterminate(indeterminate);

            dialogInternet.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            while (true) {

                if (LAT_USUARIO != 0.0 && LGN_USUARIO != 0.0)
                    break;

            }

            String url;

            hospitais = new ArrayList<>();

            final ParseQuery<ParseObject> hospitaisParse = ParseQuery.getQuery("Hospital");
            hospitaisParse.setLimit(5);
            hospitaisParse.whereWithinKilometers("localizacao", new ParseGeoPoint(LAT_USUARIO, LGN_USUARIO), Helper.getRaioMaximo(getActivity()));

            hospitaisParse.findInBackground(new FindCallback<ParseObject>() {

                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {

                    ParseGeoPoint geoPoint = new ParseGeoPoint(LAT_USUARIO, LGN_USUARIO);
                    Coordenada coordAux;

                    if (parseObjects != null) {

                        for (ParseObject p : parseObjects) {

                            ParseGeoPoint pontos = p.getParseGeoPoint("localizacao");
                            String nomeHospital = p.getString("nome");
                            Hospital hospAux = new Hospital();

                            coordAux = new Coordenada(pontos.getLatitude(), pontos.getLongitude());

                            hospAux.setLocalizacao(coordAux);
                            hospAux.setNome(nomeHospital);

                            hospitais.add(hospAux);

                            Log.w("Parse", geoPoint.distanceInKilometersTo(pontos) + " ");

                        }

                        String url = construirUrlMapa(hospitais);

                        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                Log.w("response", response);

                                if (converterJson(response)) {

                                    Collections.sort(hospitais);

                                    hospitalAdapter = new HospitalAdapter(hospitais, R.layout.new_row_layout);
                                    recyclerView.setAdapter(hospitalAdapter);

                                } else
                                    Toast.makeText(getActivity(), "Deu ruim, tente novamente.", Toast.LENGTH_SHORT).show();

                                dialogInternet.dismiss();

                            }

                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                                if (error instanceof NetworkError)
                                    Toast.makeText(context, getResources().getString(R.string.erro_sem_conexao), Toast.LENGTH_LONG).show();
                                else {

                                    Log.e("LocalizacaoFragment", error.getMessage() + " ");
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

                                }

                                dialogInternet.dismiss();

                            }

                        });


                        mRequestQueue.add(mStringRequest);

                    }

                }

            });

            return null;

        }

        private String construirUrlMapa(List<Hospital> hospitalList) {

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("http://maps.googleapis.com/maps/api/distancematrix/json?origins=");
            stringBuilder.append(ListaHospitaisFragment.LAT_USUARIO);
            stringBuilder.append(",");
            stringBuilder.append(ListaHospitaisFragment.LGN_USUARIO);
            stringBuilder.append("&destinations=");

            for (Hospital h : hospitalList) {

                stringBuilder.append(h.getLocalizacao().getLat());
                stringBuilder.append(",");
                stringBuilder.append(h.getLocalizacao().getLgn());
                stringBuilder.append("|");

            }

            stringBuilder.append("&mode=driving&language=pt-BR&sensor=true");

            return stringBuilder.toString();

        }

        private boolean converterJson(String jsonConteudo) {

            List<Elementos> elementos;
            boolean resposta;

            Gson conteudos = new Gson();

            DistanceMatrixResponse respostas = conteudos.fromJson(jsonConteudo,
                    DistanceMatrixResponse.class);

            if (respostas.getStatus().equals("OK")) {

                elementos = respostas.getLinhas().get(0).getElementos();

                if (elementos.get(0).getStatus().equals("OK")) {

                    for (int k = 0; k < hospitais.size(); k++) {

                        hospitais.get(k).setDistancia(
                                elementos.get(k).getDistancia().getTexto());
                        hospitais.get(k).setValorDistancia(
                                elementos.get(k).getDistancia().getValor());
                        hospitais.get(k).setTempo(
                                elementos.get(k).getDuracao().getTexto());
                        hospitais.get(k).setValorTempo(
                                elementos.get(k).getDuracao().getValor());

                    }

                    resposta = true;

                } else
                    resposta = false;

            } else
                resposta = false;

            return resposta;

        }

    }

}