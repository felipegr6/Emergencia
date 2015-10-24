package br.com.fgr.emergencia.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.ui.adapters.ViewPagerAdapter;
import br.com.fgr.emergencia.ui.fragments.ListaHospitaisFragment;
import br.com.fgr.emergencia.ui.fragments.RadarFragment;

public class LocalizadorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizador);

        Toolbar toolbar;
        TabLayout tabLayout;
        ViewPager viewPager;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hospitais");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(ListaHospitaisFragment.newInstance(-23.5489147, -46.6352202), "Lista");
        adapter.addFragment(RadarFragment.newInstance(), "Radar");
        viewPager.setAdapter(adapter);

    }

}