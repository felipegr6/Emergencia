package br.com.fgr.emergencia.iu.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.iu.activities.LocalizacaoActivity;

public class PrincipalFragment extends Fragment {

    private ImageButton btnEmergencia;

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

        btnEmergencia = (ImageButton) view.findViewById(R.id.btnEmergencia);

        btnEmergencia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),
                        LocalizacaoActivity.class);
                startActivity(intent);

            }

        });

        return view;

    }

}