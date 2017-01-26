package br.com.fgr.emergencia.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.ui.adapters.ViewPagerAdapter;
import br.com.fgr.emergencia.ui.fragments.ListaHospitaisFragment;
import br.com.fgr.emergencia.ui.fragments.RadarFragment;
import butterknife.BindView;

public class LocatorActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override public boolean isMainActivity() {
        return false;
    }

    @Override protected int getLayoutResource() {
        return R.layout.activity_localizador;
    }

    @Override protected Toolbar getToolbar() {
        return toolbar;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(ListaHospitaisFragment.newInstance(-23.5489147, -46.6352202), "Lista");
        adapter.addFragment(RadarFragment.newInstance(), "Radar");
        viewPager.setAdapter(adapter);
    }
}
