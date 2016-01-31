package br.com.fgr.emergencia.ui.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.parse.ParseObject;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.general.TipoBannerErro;
import br.com.fgr.emergencia.ui.fragments.MainFragment;
import butterknife.Bind;

public class MainActivity extends BaseActivity implements MoPubView.BannerAdListener {

    private static final String MOPUB_BANNER_AD_UNIT_ID = "cfb1d11b4942442582b5ed69cf94937f";

    @Bind(R.id.drawer)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_lista)
    ListView mDrawerList;
    @Bind(R.id.mopub_sample_ad)
    MoPubView moPubView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        String[] navListValues;

        moPubView = (MoPubView) findViewById(R.id.mopub_sample_ad);
        moPubView.setAdUnitId(MOPUB_BANNER_AD_UNIT_ID);
        moPubView.loadAd();

        navListValues = getResources().getStringArray(R.array.lista_nav_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, navListValues));

        mDrawerList.setOnItemClickListener(new AcaoNavDrawer());

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main_fragment_container, MainFragment.newInstance());
        ft.commit();

    }


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
    protected void onDestroy() {

        moPubView.destroy();
        super.onDestroy();

    }

    @Override
    public boolean isMainActivity() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onBannerLoaded(MoPubView moPubView) {
        saveBannerStats(TipoBannerErro.BANNER_LOADED);
    }

    @Override
    public void onBannerFailed(MoPubView moPubView, MoPubErrorCode moPubErrorCode) {
        saveBannerStats(TipoBannerErro.BANNER_FAILED);
    }

    @Override
    public void onBannerClicked(MoPubView moPubView) {
        saveBannerStats(TipoBannerErro.BANNER_CLICKED);
    }

    @Override
    public void onBannerExpanded(MoPubView moPubView) {
        saveBannerStats(TipoBannerErro.BANNER_EXPANDED);
    }

    @Override
    public void onBannerCollapsed(MoPubView moPubView) {
        saveBannerStats(TipoBannerErro.BANNER_COLLAPSED);
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

    private void saveBannerStats(TipoBannerErro tipoBannerErro) {

        ParseObject banner = new ParseObject("BannerExibhition");

        banner.put("tipoBanner", tipoBannerErro.getTipoBannerErro());
        banner.saveInBackground();

    }

}