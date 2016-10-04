package br.com.fgr.emergencia.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.events.LoadListEvent;
import br.com.fgr.emergencia.models.general.ConversorDeCoordenadas;
import br.com.fgr.emergencia.ui.custom_views.RadarView;
import de.greenrobot.event.EventBus;

public class RadarFragment extends Fragment {

    private RadarView radarView;

    public RadarFragment() {
        // Required empty public constructor
    }

    public static RadarFragment newInstance() {

        RadarFragment fragment = new RadarFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_radar, container, false);
        radarView = (RadarView) v.findViewById(R.id.radarView);

        return v;
    }

    @Override public void onResume() {

        super.onResume();

        EventBus.getDefault().register(this);
    }

    @Override public void onPause() {

        super.onPause();

        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    @SuppressWarnings("unused") public void onEvent(LoadListEvent event) {

        ConversorDeCoordenadas conversor = new ConversorDeCoordenadas(30, event);

        radarView.setList(conversor.getCoordenadasPadronizadas());
    }
}