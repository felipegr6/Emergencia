package br.com.fgr.emergencia.ui.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.ui.activities.LocalizadorActivity;
import br.com.fgr.emergencia.utils.Helper;

public class PrincipalFragment extends Fragment {

    private AppCompatButton btnEmergencia;

    public PrincipalFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_principal,
                container, false);

        btnEmergencia = (AppCompatButton) view.findViewById(R.id.button_emergencia);

        btnEmergencia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (Helper.isOnline(getActivity())) {

                    Intent intent = new Intent(getActivity(),
                            LocalizadorActivity.class);
                    startActivity(intent);

                } else
                    Toast.makeText(getActivity(), getResources().getString(R.string.erro_sem_conexao),
                            Toast.LENGTH_SHORT).show();

            }

        });

        return view;

    }

}