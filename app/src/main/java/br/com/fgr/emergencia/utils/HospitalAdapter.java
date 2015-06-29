package br.com.fgr.emergencia.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.general.Coordenada;
import br.com.fgr.emergencia.models.general.Hospital;
import br.com.fgr.emergencia.ui.activities.MapaActivity;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    private Context context;
    private Coordenada coordUsuario;
    private List<Hospital> hospitais;
    private int rowLayout;

    public HospitalAdapter(Context context, Coordenada coorUsuario,
                           List<Hospital> hospitais, int rowLayout) {

        this.context = context;
        this.coordUsuario = coorUsuario;
        this.hospitais = hospitais;
        this.rowLayout = rowLayout;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        final Hospital hospital = hospitais.get(i);

        viewHolder.getNome().setText(hospital.getNome());
        viewHolder.getDistancia().setText(hospital.getDistancia());
        viewHolder.getTempo().setText(hospital.getTempo());
        viewHolder.getBtnMapa().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MapaActivity.class);

                intent.putExtra(MapaActivity.LAT_ORIGEM, coordUsuario.getLat());
                intent.putExtra(MapaActivity.LGN_ORIGEM, coordUsuario.getLgn());
                intent.putExtra(MapaActivity.LAT_DESTINO, hospital.getLocalizacao().getLat());
                intent.putExtra(MapaActivity.LGN_DESTINO, hospital.getLocalizacao().getLgn());

                context.startActivity(intent);

            }

        });

        viewHolder.getBtnNavegacao().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {

                    String url = "waze://?ll=" + hospital.getLocalizacao().getLat() + ","
                            + hospital.getLocalizacao().getLgn() + "&navigate=yes";

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intent);

                } catch (ActivityNotFoundException ex) {

                    Intent intent =
                            new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                    context.startActivity(intent);

                }

            }

        });

    }

    @Override
    public int getItemCount() {
        return hospitais == null ? 0 : hospitais.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView nome;
        private AppCompatTextView distancia;
        private AppCompatTextView tempo;
        private AppCompatTextView btnMapa;
        private AppCompatTextView btnNavegacao;

        public ViewHolder(View itemView) {

            super(itemView);

            nome = (AppCompatTextView) itemView.findViewById(R.id.label_nome);
            distancia = (AppCompatTextView) itemView.findViewById(R.id.label_valor_distancia);
            tempo = (AppCompatTextView) itemView.findViewById(R.id.label_valor_tempo);
            btnMapa = (AppCompatTextView) itemView.findViewById(R.id.button_mapa);
            btnNavegacao = (AppCompatTextView) itemView.findViewById(R.id.button_navegacao);

        }

        public AppCompatTextView getNome() {
            return nome;
        }

        public AppCompatTextView getDistancia() {
            return distancia;
        }

        public AppCompatTextView getTempo() {
            return tempo;
        }

        public AppCompatTextView getBtnMapa() {
            return btnMapa;
        }

        public AppCompatTextView getBtnNavegacao() {
            return btnNavegacao;
        }

    }

}