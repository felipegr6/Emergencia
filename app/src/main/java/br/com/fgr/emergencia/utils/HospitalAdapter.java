package br.com.fgr.emergencia.utils;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.general.Hospital;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    private List<Hospital> hospitais;
    private int rowLayout;

    public HospitalAdapter(List<Hospital> hospitais, int rowLayout) {

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

        Hospital hospital = hospitais.get(i);

        viewHolder.getNome().setText(hospital.getNome());
        viewHolder.getDistancia().setText(hospital.getDistancia());
        viewHolder.getTempo().setText(hospital.getTempo());

    }

    @Override
    public int getItemCount() {
        return hospitais == null ? 0 : hospitais.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView nome;
        public AppCompatTextView distancia;
        public AppCompatTextView tempo;

        public ViewHolder(View itemView) {

            super(itemView);

            nome = (AppCompatTextView) itemView.findViewById(R.id.label_nome);
            distancia = (AppCompatTextView) itemView.findViewById(R.id.label_valor_distancia);
            tempo = (AppCompatTextView) itemView.findViewById(R.id.label_valor_tempo);
        }

        public AppCompatTextView getNome() {
            return nome;
        }

        public AppCompatTextView getDistancia(){
            return distancia;
        }

        public AppCompatTextView getTempo(){
            return tempo;
        }

    }

}