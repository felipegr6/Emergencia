package br.com.fgr.emergencia.ui.fragments;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.fgr.emergencia.modelos.Coordenada;
import br.com.fgr.emergencia.modelos.Hospital;
import br.com.fgr.emergencia.modelos.json.Elementos;
import br.com.fgr.emergencia.modelos.json.JsonResponse;
import br.com.fgr.emergencia.ui.R;

public class LocalizacaoFragment extends ListFragment implements
        LocationListener {

    private final String TAG = this.getClass().getSimpleName();

    public static double LAT_USUARIO = 0.0;
    public static double LGN_USUARIO = 0.0;
    private List<Hospital> hospitais;
    private LocationManager serviceLocation;
    private String provider;

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

        serviceLocation = (LocationManager) getActivity().getSystemService(
                Context.LOCATION_SERVICE);

        provider = LocationManager.GPS_PROVIDER;

        boolean enabled = serviceLocation.isProviderEnabled(provider);

		/*
         * Verifica se o GPS está habilitado no dispositivo.
		 */
        if (!enabled) {

            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

            startActivity(intent);

        }

        Log.i(TAG, provider);

        serviceLocation.requestLocationUpdates(provider, 5000, 10, this);

        List<Coordenada> coordenadas = new ArrayList<>();

        coordenadas.add(new Coordenada(-23.5669748, -46.6421046));
        coordenadas.add(new Coordenada(-23.569984, -46.6456397));
        coordenadas.add(new Coordenada(-23.5562345, -46.6396883));

        DownloadJson dJson = new DownloadJson(getActivity(), coordenadas);

        dJson.execute();

        return view;

    }

    @Override
    public void onResume() {

        super.onResume();

        LAT_USUARIO = 0.0;
        LGN_USUARIO = 0.0;

        serviceLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                5000, 10, this);

    }

    @Override
    public void onPause() {

        super.onPause();

        serviceLocation.removeUpdates(this);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        Hospital hosp = (Hospital) getListAdapter().getItem(position);

        String urlCoord = "http://maps.google.com/maps?saddr=" + LAT_USUARIO
                + "," + LGN_USUARIO + "&daddr="
                + hosp.getLocalizacao().getLat() + ","
                + hosp.getLocalizacao().getLgn();

        Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(urlCoord));

        startActivity(i);

    }

    @Override
    public void onLocationChanged(Location location) {

        LAT_USUARIO = location.getLatitude();
        LGN_USUARIO = location.getLongitude();

        Log.i(TAG, LAT_USUARIO + "," + LGN_USUARIO);

        Toast.makeText(getActivity(), LAT_USUARIO + "," + LGN_USUARIO,
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

        Toast.makeText(getActivity(), "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {

        Toast.makeText(getActivity(), "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    private class DownloadJson extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialogInternet;
        private List<Coordenada> destinos;
        private Context context;

        public DownloadJson(Context context, List<Coordenada> coordenadas) {

            this.dialogInternet = new ProgressDialog(context);
            this.destinos = coordenadas;
            this.context = context;

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
        protected void onPostExecute(Void result) {

            ArrayAdapter<Hospital> adapter = new ArrayAdapter<>(
                    context, R.layout.rowlayout, R.id.textItem, hospitais);

            setListAdapter(adapter);

            dialogInternet.dismiss();

            serviceLocation.removeUpdates(LocalizacaoFragment.this);

        }

        @Override
        protected Void doInBackground(Void... params) {

            String url = "";
            String json = "";

            hospitais = new ArrayList<Hospital>();

            hospitais.add(new Hospital("Hospital Beneficência Portuguesa",
                    "2,5 km", "6 minutos", 341, destinos.get(0), 2));
            hospitais.add(new Hospital("Hospital Santa Catarina", "2,3 km",
                    "5 minutos", 284, destinos.get(1), 1.5));
            hospitais.add(new Hospital("Hospital Pérola Byington", "2,6 km",
                    "6 minutos", 370, destinos.get(2), 0.5));

            while (LAT_USUARIO == 0.0 && LGN_USUARIO == 0.0) {

            }

            try {

                url = "http://maps.googleapis.com/maps/api/distancematrix/json?origins="
                        + URLEncoder.encode(LocalizacaoFragment.LAT_USUARIO
                        + ",", "UTF-8")
                        + URLEncoder.encode(LocalizacaoFragment.LGN_USUARIO
                        + "", "UTF-8")
                        + "&destinations="
                        + URLEncoder.encode(destinos.get(0).getLat() + ",",
                        "UTF-8")
                        + URLEncoder.encode(destinos.get(0).getLgn() + "|",
                        "UTF-8")
                        + URLEncoder.encode(destinos.get(1).getLat() + ",",
                        "UTF-8")
                        + URLEncoder.encode(destinos.get(1).getLgn() + "|",
                        "UTF-8")
                        + URLEncoder.encode(destinos.get(2).getLat() + ",",
                        "UTF-8")
                        + URLEncoder.encode(destinos.get(2).getLgn() + "",
                        "UTF-8")
                        + "&mode=driving&language=pt-BR&sensor=false";

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();

            }

            Log.i(TAG, url);

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);

            try {

                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();

                if (entity != null) {

                    InputStream instream = entity.getContent();
                    json = toString(instream);

                    instream.close();

                }

            } catch (IOException ioE) {

                ioE.printStackTrace();

            }

            Log.i(TAG, json);

            converterJson(json);

            if (hospitais.size() != 0) {

                Collections.sort(hospitais);

            }

            return null;

        }

        private String toString(InputStream is) throws IOException {

            byte[] bytes = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int lidos;

            while ((lidos = is.read(bytes)) > 0) {

                baos.write(bytes, 0, lidos);

            }

            return new String(baos.toByteArray());

        }

        private void converterJson(String jsonConteudo) {

            List<Elementos> elementos;

            Gson conteudos = new Gson();

            JsonResponse respostas = conteudos.fromJson(jsonConteudo,
                    JsonResponse.class);

            if (respostas != null)
                elementos = respostas.getLinhas().get(0).getElementos();
            else
                elementos = new ArrayList<Elementos>();

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