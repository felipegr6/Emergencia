package br.com.fgr.emergencia.ui.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.ui.activities.LocationActivity;
import br.com.fgr.emergencia.utils.Helper;
import butterknife.OnClick;

public class MainFragment extends BaseFragment {

    public static Fragment newInstance() {

        return new MainFragment();

    }

    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_emergencia)
    public void searchHospitals() {

        if (Helper.isOnline(getActivity())) {

            Intent intent = new Intent(getActivity(),
                    LocationActivity.class);
            startActivity(intent);

        } else
            Toast.makeText(getActivity(), getResources().getString(R.string.erro_sem_conexao),
                    Toast.LENGTH_SHORT).show();

    }


    @Override
    public int getLayout() {
        return R.layout.fragment_principal;
    }

}