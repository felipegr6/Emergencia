package br.com.fgr.emergencia.ui.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.distancematrix.DistanceMatrixRequest;
import br.com.fgr.emergencia.models.distancematrix.DistanceMatrixResponse;
import br.com.fgr.emergencia.models.distancematrix.Elementos;
import br.com.fgr.emergencia.models.general.Configuracao;
import br.com.fgr.emergencia.models.general.Coordenada;
import br.com.fgr.emergencia.models.general.Hospital;
import br.com.fgr.emergencia.ui.activities.AjudaListaActivity;
import br.com.fgr.emergencia.ui.activities.MapaActivity;
import br.com.fgr.emergencia.utils.GoogleServices;
import br.com.fgr.emergencia.utils.Helper;
import br.com.fgr.emergencia.utils.HospitalAdapter;
import br.com.fgr.emergencia.utils.ServiceGenerator;
import retrofit.Callback;
import retrofit.RetrofitError;

public class ListaHospitaisFragment extends Fragment {

    public final static String TAG_LATITUDE = "LATITUDE";
    public final static String TAG_LONGITUDE = "LONGITUDE";

    public double latUsuario;
    public double lgnUsuario;
    protected RecyclerView recyclerView;
    protected GestureDetectorCompat detector;
    private List<Hospital> hospitais;
    private HospitalAdapter hospitalAdapter;
    private Configuracao config;

    public ListaHospitaisFragment() {

    }

    public static ListaHospitaisFragment newInstance(double latitude, double longitude) {

        Bundle bundle = new Bundle();
        ListaHospitaisFragment fragment = new ListaHospitaisFragment();

        bundle.putDouble(TAG_LATITUDE, latitude);
        bundle.putDouble(TAG_LONGITUDE, longitude);

        fragment.setArguments(bundle);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            latUsuario = getArguments().getDouble(TAG_LATITUDE);
            lgnUsuario = getArguments().getDouble(TAG_LONGITUDE);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_localizacao, container, false);

        if (isAdded())
            config = Helper.getConfiguracoes(getActivity().getApplicationContext());

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

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });

        if (isAdded())
            realizarChamada(getActivity(), latUsuario, lgnUsuario);

        return view;

    }

    private void realizarChamada(final Context context, final double latitude, final double longitude) {

        final ProgressDialog dialogInternet;

        dialogInternet = new ProgressDialog(context);

        String title = getResources().getString(R.string.titulo_dialog_hospitais);
        String message = getResources().getString(R.string.descricao_dialog_hospitais);

        dialogInternet.setTitle(title);
        dialogInternet.setMessage(message);
        dialogInternet.setCancelable(false);
        dialogInternet.setIndeterminate(true);

        dialogInternet.show();

        int qtdeHospitais = Integer.parseInt(Helper.formatarInformacao(Helper.CONST_HOSPITAIS, config.getHospitais(), false));
        float raioAlcance = Float.parseFloat(Helper.formatarInformacao(Helper.CONST_RAIO, config.getRaio(), false));

        hospitais = new ArrayList<>();

        final ParseQuery<ParseObject> hospitaisParse = ParseQuery.getQuery("Hospital");
        hospitaisParse.setLimit(qtdeHospitais);
        hospitaisParse.whereWithinKilometers("localizacao", new ParseGeoPoint(latUsuario, lgnUsuario), raioAlcance);

        hospitaisParse.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {

                ParseGeoPoint geoPoint = new ParseGeoPoint(latUsuario, lgnUsuario);
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

                    List<Coordenada> destinos = new ArrayList<>();

                    for (Hospital h : hospitais) {
                        destinos.add(h.getLocalizacao());
                    }

                    DistanceMatrixRequest request = new DistanceMatrixRequest(new Coordenada(latitude, longitude),
                            destinos, config.getModo());

                    GoogleServices repo = ServiceGenerator.createService(GoogleServices.class, Helper.URL_GOOGLE_BASE);

                    repo.matrix(request.getOrigem(), request.getDestinos(), request.getModo(),
                            "pt-BR", true, new Callback<DistanceMatrixResponse>() {

                                @Override
                                public void success(DistanceMatrixResponse dResp, retrofit.client.Response response) {

                                    if (isAdded() && Helper.getFirstTimeTutorial(getActivity()))
                                        startActivity(new Intent(getActivity(), AjudaListaActivity.class));

                                    popularHospitais(dResp);

                                    Collections.sort(hospitais);

                                    hospitalAdapter = new HospitalAdapter(hospitais, R.layout.row_hospital);
                                    recyclerView.setAdapter(hospitalAdapter);

                                    if (dialogInternet.isShowing())
                                        dialogInternet.dismiss();

                                }

                                @Override
                                public void failure(RetrofitError error) {

                                    if (dialogInternet.isShowing())
                                        dialogInternet.dismiss();

                                    Toast.makeText(getActivity(), getString(R.string.nao_encontrou_hospitais),
                                            Toast.LENGTH_SHORT).show();

                                    Log.e("ListaHospitais", error.getMessage());

                                }

                            });

                }

            }

        });

    }

    private boolean popularHospitais(DistanceMatrixResponse respostas) {

        List<Elementos> elementos;
        boolean resposta;

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

    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        public View view;

        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {

            view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            int position = recyclerView.getChildAdapterPosition(view);

            Hospital hosp = hospitais.get(position);

            Intent intent = new Intent(getActivity(), MapaActivity.class);

            intent.putExtra(MapaActivity.LAT_ORIGEM, latUsuario);
            intent.putExtra(MapaActivity.LGN_ORIGEM, lgnUsuario);
            intent.putExtra(MapaActivity.LAT_DESTINO, hosp.getLocalizacao().getLat());
            intent.putExtra(MapaActivity.LGN_DESTINO, hosp.getLocalizacao().getLgn());

            startActivity(intent);

            return super.onSingleTapConfirmed(motionEvent);

        }

        public void onLongPress(final MotionEvent motionEvent) {

            super.onLongPress(motionEvent);

            view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            int position = recyclerView.getChildAdapterPosition(view);

            Hospital hosp = hospitais.get(position);

            try {

                String url = "waze://?ll=" + hosp.getLocalizacao().getLat() + ","
                        + hosp.getLocalizacao().getLgn() + "&navigate=yes";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);

            } catch (ActivityNotFoundException ex) {

                Intent intent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                startActivity(intent);

            }

        }

    }

}