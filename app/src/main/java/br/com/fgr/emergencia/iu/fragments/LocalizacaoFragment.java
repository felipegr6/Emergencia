package br.com.fgr.emergencia.iu.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.bd.BDHospitalHelper;
import br.com.fgr.emergencia.bd.HospitalProvider;
import br.com.fgr.emergencia.modelos.Coordenada;
import br.com.fgr.emergencia.modelos.Hospital;
import br.com.fgr.emergencia.modelos.json.Elementos;
import br.com.fgr.emergencia.modelos.json.JsonResponse;

public class LocalizacaoFragment extends ListFragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static double LAT_USUARIO = 0.0;
    public static double LGN_USUARIO = 0.0;
    private final String TAG = this.getClass().getSimpleName();
    protected GoogleApiClient mGoogleApiClient;
    private List<Hospital> hospitais;

    public LocalizacaoFragment() {

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
    public void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        Hospital hosp = (Hospital) getListAdapter().getItem(position);

        CaminhoFragment mCaminhoFragment = CaminhoFragment.newInstance(LAT_USUARIO,
                LGN_USUARIO,
                hosp.getLocalizacao().getLat(),
                hosp.getLocalizacao().getLgn());

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.loc_fragment_container, mCaminhoFragment);
        fragmentTransaction.commit();

        /*
        Hospital hosp = (Hospital) getListAdapter().getItem(position);

        String urlCoord = "http://maps.google.com/maps?saddr=" + LAT_USUARIO
                + "," + LGN_USUARIO + "&daddr="
                + hosp.getLocalizacao().getLat() + ","
                + hosp.getLocalizacao().getLgn();

        Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(urlCoord));

        startActivity(i);
        */

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

    private class DownloadJson extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialogInternet;
        private Context context;
        private RequestQueue mRequestQueue;
        private StringRequest mStringRequest;
        private ArrayAdapter<Hospital> adapter;

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

            String url;

            hospitais = new ArrayList<>();

            Cursor cursor = context.getContentResolver().query(HospitalProvider.CONTENT_URI, BDHospitalHelper.PROJECAO, null, null, null);

            while (cursor.moveToNext()) {

                Hospital hospital = new Hospital();
                Coordenada localizacao = new Coordenada();

                localizacao.setLat(cursor.getDouble(BDHospitalHelper.NUM_COLUNA_LATITUDE));
                localizacao.setLgn(cursor.getDouble(BDHospitalHelper.NUM_COLUNA_LONGITUDE));

                hospital.setNome(cursor.getString(BDHospitalHelper.NUM_COLUNA_NOME));
                hospital.setLocalizacao(localizacao);

                hospitais.add(hospital);

            }

            cursor.close();

            while (LAT_USUARIO == 0.0 && LGN_USUARIO == 0.0) {

            }

            url = "http://maps.googleapis.com/maps/api/distancematrix/json?origins="
                    + LocalizacaoFragment.LAT_USUARIO
                    + ","
                    + LocalizacaoFragment.LGN_USUARIO
                    + ""
                    + "&destinations=";

            for (Hospital h : hospitais) {

                url = url
                        + h.getLocalizacao().getLat()
                        + ","
                        + h.getLocalizacao().getLgn()
                        + "|";

            }

            url = url + "&mode=driving&language=pt-BR&sensor=false";

            Log.i(TAG, url);

            mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    Log.w("response", response);

                    converterJson(response);
                    Collections.sort(hospitais);

                    adapter = new ArrayAdapter<>(context, R.layout.rowlayout, R.id.textItem, hospitais);
                    setListAdapter(adapter);

                    dialogInternet.dismiss();

                }

            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    if (error instanceof NetworkError)
                        Toast.makeText(context, getResources().getString(R.string.erro_sem_conexao), Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

                    dialogInternet.dismiss();

                }

            });

            mRequestQueue.add(mStringRequest);

            return null;

        }

        private void converterJson(String jsonConteudo) {

            List<Elementos> elementos;

            Gson conteudos = new Gson();

            JsonResponse respostas = conteudos.fromJson(jsonConteudo,
                    JsonResponse.class);

            if (respostas != null)
                elementos = respostas.getLinhas().get(0).getElementos();
            else
                elementos = new ArrayList<>();

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

        }

    }

}