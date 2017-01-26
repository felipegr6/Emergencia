package br.com.fgr.emergencia.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    public abstract int getLayout();

    public BaseFragment() {

    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View v = inflater.inflate(getLayout(), container, false);

        unbinder = ButterKnife.bind(this, v);

        return v;
    }

    @Override public void onDestroyView() {
        unbinder.unbind();

        super.onDestroyView();
    }
}
