package br.com.fgr.emergencia.ui.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mopub.mobileads.MoPubView;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.ui.fragments.PrincipalFragment;

public class MainActivity extends BaseActivity {

    private static final String MOPUB_BANNER_AD_UNIT_ID = "cfb1d11b4942442582b5ed69cf94937f";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] navListValues;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private MoPubView moPubView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        moPubView = (MoPubView) findViewById(R.id.mopub_sample_ad);
        moPubView.setAdUnitId(MOPUB_BANNER_AD_UNIT_ID);
        moPubView.loadAd();

        mTitle = getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        mDrawerList = (ListView) findViewById(R.id.nav_lista);
        navListValues = getResources().getStringArray(R.array.lista_nav_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, navListValues));

        mDrawerList.setOnItemClickListener(new AcaoNavDrawer());

        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,
                mDrawerLayout, getToolbar(), R.string.drawer_open,
                R.string.drawer_close);

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main_fragment_container, new PrincipalFragment());
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
            case R.id.action_configuration:
                intent = new Intent(MainActivity.this, ConfiguracaoActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        mDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);

    }

    @Override
    protected int getLayoutResource() {

        return R.layout.activity_main;

    }

    @Override
    protected void onDestroy() {

        moPubView.destroy();
        super.onDestroy();

    }

    private class AcaoNavDrawer implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (position) {

                case 0:
                    mDrawerLayout.closeDrawers();
                    Intent i1 = new Intent(MainActivity.this, ConfiguracaoActivity.class);
                    startActivity(i1);
                    break;
                case 2:
                    break;
                default:
                    break;

            }

        }

    }

}