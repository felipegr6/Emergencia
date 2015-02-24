package br.com.fgr.emergencia.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        viewHolder.getCountryName().setText(hospital.getNome());
        viewHolder.getCountryImage().setBackgroundResource(R.drawable.cruz);

    }

    @Override
    public int getItemCount() {
        return hospitais == null ? 0 : hospitais.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView countryName;
        public ImageView countryImage;

        public ViewHolder(View itemView) {

            super(itemView);

            countryName = (TextView) itemView.findViewById(R.id.countryName);
            countryImage = (ImageView) itemView.findViewById(R.id.countryImage);

        }

        public TextView getCountryName() {
            return countryName;
        }

        public ImageView getCountryImage() {
            return countryImage;
        }

    }

}