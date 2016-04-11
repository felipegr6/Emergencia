package br.com.fgr.emergencia.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import br.com.fgr.emergencia.models.events.LoadListEvent;
import br.com.fgr.emergencia.models.general.Configuration;
import br.com.fgr.emergencia.models.general.Coordenada;
import br.com.fgr.emergencia.models.general.Hospital;
import br.com.fgr.emergencia.ui.adapters.HospitalAdapter;
import br.com.fgr.emergencia.utils.GoogleServices;
import br.com.fgr.emergencia.utils.Helper;
import br.com.fgr.emergencia.utils.ServiceGenerator;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;

public class ListaHospitaisFragment extends Fragment {

    public final static String TAG_LATITUDE = "LATITUDE";
    public final static String TAG_LONGITUDE = "LONGITUDE";

    public double latUsuario;
    public double lgnUsuario;
    private RecyclerView recyclerView;
    private List<Hospital> hospitais;
    private HospitalAdapter hospitalAdapter;
    private Configuration config;

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

        int qtdeHospitais = Integer.parseInt(Helper.formatarInformacao(Helper.CONST_HOSPITAIS,
                config.getHospitals(), false));
        float raioAlcance = Float.parseFloat(Helper.formatarInformacao(Helper.CONST_RAIO,
                config.getRadius(), false));

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

                    for (Hospital h : hospitais)
                        destinos.add(h.getLocalizacao());

                    EventBus.getDefault()
                            .post(new LoadListEvent(new Coordenada(latUsuario, lgnUsuario),
                                    destinos));

                    DistanceMatrixRequest request = new DistanceMatrixRequest(new Coordenada(latitude, longitude),
                            destinos, config.getMode());

                    GoogleServices repo = ServiceGenerator.createService(GoogleServices.class,
                            Helper.URL_GOOGLE_BASE);

                    repo.matrix(request.getOrigem(), request.getDestinos(), request.getModo(), "pt-BR",
                            true, new Callback<DistanceMatrixResponse>() {

                                @Override
                                public void success(DistanceMatrixResponse dResp, retrofit.client.Response response) {

                                    popularHospitais(dResp);

                                    Collections.sort(hospitais);

                                    if (isAdded())
                                        hospitalAdapter = new HospitalAdapter(getActivity(),
                                                new Coordenada(latUsuario, lgnUsuario), hospitais,
                                                R.layout.row_hospital);

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

}