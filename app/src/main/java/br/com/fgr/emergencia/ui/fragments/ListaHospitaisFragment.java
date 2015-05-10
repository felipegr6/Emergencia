package br.com.fgr.emergencia.ui.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.distancematrix.DistanceMatrixResponse;
import br.com.fgr.emergencia.models.distancematrix.Elementos;
import br.com.fgr.emergencia.models.general.Configuracao;
import br.com.fgr.emergencia.models.general.Coordenada;
import br.com.fgr.emergencia.models.general.Hospital;
import br.com.fgr.emergencia.utils.Helper;
import br.com.fgr.emergencia.utils.HospitalAdapter;

public class ListaHospitaisFragment extends Fragment {

    public final static String TAG_LATITUDE = "LATITUDE";
    public final static String TAG_LONGITUDE = "LONGITUDE";
    public double latUsuario;
    public double lgnUsuario;
    protected RecyclerView recyclerView;
    protected GestureDetectorCompat detector;
    private List<Hospital> hospitais;

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

        new DownloadJson(getActivity(), latUsuario, lgnUsuario).execute();

        return view;

    }

    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        public View view;

        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {

            view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            int position = recyclerView.getChildPosition(view);

            Hospital hosp = hospitais.get(position);

            MapaFragment mCaminhoFragment = MapaFragment.newInstance(latUsuario,
                    lgnUsuario,
                    hosp.getLocalizacao().getLat(),
                    hosp.getLocalizacao().getLgn());

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.loc_fragment_container, mCaminhoFragment);
            fragmentTransaction.commit();

            return super.onSingleTapConfirmed(motionEvent);

        }

        public void onLongPress(final MotionEvent motionEvent) {

            view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            int position = recyclerView.getChildPosition(view);

            collapse(view.findViewById(R.id.infoDetalhe));
            view.findViewById(R.id.infoPrincipal).setBackgroundResource(0);
            Handler h = new Handler();

            h.postDelayed(new Runnable() {

                @Override
                public void run() {
                    expand(view.findViewById(R.id.infoDetalhe));
                    view.findViewById(R.id.infoPrincipal).setBackgroundResource(R.drawable.download);
                }

            }, 3000);

            super.onLongPress(motionEvent);

        }

        public void expand(final View v) {

            v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            final int targetHeight = v.getMeasuredHeight();

            v.getLayoutParams().height = 0;
            v.setVisibility(View.VISIBLE);

            Animation a = new Animation() {

                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {

                    v.getLayoutParams().height = interpolatedTime == 1
                            ? RelativeLayout.LayoutParams.WRAP_CONTENT
                            : (int) (targetHeight * interpolatedTime);
                    v.requestLayout();

                }

                @Override
                public boolean willChangeBounds() {

                    return true;

                }

            };

            // 1dp/ms
            a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);
        }

        public void collapse(final View v) {

            final int initialHeight = v.getMeasuredHeight();

            Animation a = new Animation() {

                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {

                    if (interpolatedTime == 1)
                        v.setVisibility(View.GONE);
                    else {

                        v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                        v.requestLayout();

                    }

                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }

            };

            // 1dp/ms
            a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);

        }

    }

    private class DownloadJson extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialogInternet;
        private Context context;
        private RequestQueue mRequestQueue;
        private StringRequest mStringRequest;
        private HospitalAdapter hospitalAdapter;
        private double latitude;
        private double longitude;

        public DownloadJson(Context context, double latitude, double longitude) {

            this.dialogInternet = new ProgressDialog(context);
            this.context = context;
            this.mRequestQueue = Volley.newRequestQueue(context);
            this.latitude = latitude;
            this.longitude = longitude;

            mRequestQueue.getCache().clear();

        }

        @Override
        protected void onPreExecute() {

            String title = getResources().getString(R.string.titulo_dialog_hospitais);
            String message = getResources().getString(R.string.descricao_dialog_hospitais);

            dialogInternet.setTitle(title);
            dialogInternet.setMessage(message);
            dialogInternet.setCancelable(false);
            dialogInternet.setIndeterminate(true);

            dialogInternet.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            Configuracao config = Helper.getConfiguracoes(getActivity().getApplicationContext());

            int qtdeHospitais = Integer.parseInt(Helper.formatarInformacao(Helper.CONST_HOSPITAIS, config.getHospitais(), false));
            float raioAlcance = Float.parseFloat(Helper.formatarInformacao(Helper.CONST_RAIO, config.getRaio(), false));

            while (true) {

                if (latUsuario != 0.0 && lgnUsuario != 0.0)
                    break;

            }

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
            stringBuilder.append(latitude);
            stringBuilder.append(",");
            stringBuilder.append(longitude);
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