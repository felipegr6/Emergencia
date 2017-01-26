package br.com.fgr.emergencia.ui.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.general.Coordenada;
import br.com.fgr.emergencia.models.general.Hospital;
import br.com.fgr.emergencia.ui.activities.MapaActivity;
import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    private Context context;
    private Coordenada coordUsuario;
    private List<Hospital> hospitais;
    private int rowLayout;

    public HospitalAdapter(Context context, Coordenada coorUsuario, List<Hospital> hospitais,
        int rowLayout) {
        this.context = context;
        this.coordUsuario = coorUsuario;
        this.hospitais = hospitais;
        this.rowLayout = rowLayout;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Hospital hospital = hospitais.get(i);

        viewHolder.getNome().setText(hospital.getNome());
        viewHolder.getDistancia().setText(hospital.getDistancia());
        viewHolder.getTempo().setText(hospital.getTempo());
        viewHolder.getBtnMapa().setOnClickListener(v -> {
            Intent intent = new Intent(context, MapaActivity.class);

            intent.putExtra(MapaActivity.LAT_ORIGEM, coordUsuario.getLat());
            intent.putExtra(MapaActivity.LGN_ORIGEM, coordUsuario.getLng());
            intent.putExtra(MapaActivity.LAT_DESTINO, hospital.getLocalizacao().getLat());
            intent.putExtra(MapaActivity.LGN_DESTINO, hospital.getLocalizacao().getLng());

            context.startActivity(intent);
        });

        viewHolder.getBtnNavegacao().setOnClickListener(v -> {
            try {
                String url = "waze://?ll="
                    + hospital.getLocalizacao().getLat()
                    + ","
                    + hospital.getLocalizacao().getLng()
                    + "&navigate=yes";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                Intent intent =
                    new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                context.startActivity(intent);
            }
        });
    }

    @Override public int getItemCount() {
        return hospitais == null ? 0 : hospitais.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nome;
        private TextView distancia;
        private TextView tempo;
        private TextView btnMapa;
        private TextView btnNavegacao;

        public ViewHolder(View itemView) {
            super(itemView);

            nome = (TextView) itemView.findViewById(R.id.label_nome);
            distancia = (TextView) itemView.findViewById(R.id.label_valor_distancia);
            tempo = (TextView) itemView.findViewById(R.id.label_valor_tempo);
            btnMapa = (TextView) itemView.findViewById(R.id.button_mapa);
            btnNavegacao = (TextView) itemView.findViewById(R.id.button_navegacao);
        }

        public TextView getNome() {
            return nome;
        }

        public TextView getDistancia() {
            return distancia;
        }

        public TextView getTempo() {
            return tempo;
        }

        public TextView getBtnMapa() {
            return btnMapa;
        }

        public TextView getBtnNavegacao() {
            return btnNavegacao;
        }
    }
}
