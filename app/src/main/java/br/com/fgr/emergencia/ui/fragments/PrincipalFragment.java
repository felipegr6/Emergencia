package br.com.fgr.emergencia.ui.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.ui.activities.LocalizacaoActivity;
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

                if (!Build.VERSION.CODENAME.equals("MNC")) {

                    if (Helper.isOnline(getActivity())) {

                        Intent intent = new Intent(getActivity(),
                                LocalizacaoActivity.class);
                        startActivity(intent);

                    } else
                        Toast.makeText(getActivity(), getResources().getString(R.string.erro_sem_conexao), Toast.LENGTH_SHORT).show();

                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 2222);
                }

            }

        });

        return view;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case 2222:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(getActivity(),
                            LocalizacaoActivity.class);
                    startActivity(intent);

                } else {

                }
                return;

        }

    }
}