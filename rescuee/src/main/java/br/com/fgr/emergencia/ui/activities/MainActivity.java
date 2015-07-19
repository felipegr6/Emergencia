package br.com.fgr.emergencia.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.parse.ParseObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.general.TipoBannerErro;
import br.com.fgr.emergencia.ui.fragments.PrincipalFragment;

@OptionsMenu(R.menu.main)
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements MoPubView.BannerAdListener {

    private static final String MOPUB_BANNER_AD_UNIT_ID = "cfb1d11b4942442582b5ed69cf94937f";

    @ViewById(R.id.drawer)
    DrawerLayout mDrawerLayout;
    @ViewById(R.id.nav_lista)
    ListView mDrawerList;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.mopub_sample_ad)
    MoPubView moPubView;

    @FragmentById(R.id.main_fragment_container)
    PrincipalFragment principalFragment;

    @AfterViews
    void afterViews() {

        String[] navListValues;
        ActionBarDrawerToggle mDrawerToggle;

        navListValues = getResources().getStringArray(R.array.lista_nav_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, navListValues));

        mDrawerList.setOnItemClickListener(new AcaoNavDrawer());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close);

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        moPubView.setAdUnitId(MOPUB_BANNER_AD_UNIT_ID);
        moPubView.loadAd();
        moPubView.setBannerAdListener(this);

        if (toolbar != null) {

            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

        }

    }

    @UiThread
    void statusBarColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = this.getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.vermelho_status));

        }

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////
////            Window window = this.getWindow();
////
////            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
////            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////            window.setStatusBarColor(this.getResources().getColor(R.color.vermelho_status));
////
////        }
//
////        moPubView = (MoPubView) findViewById(R.id.mopub_sample_ad);
////        moPubView.setAdUnitId(MOPUB_BANNER_AD_UNIT_ID);
////        moPubView.loadAd();
////        moPubView.setBannerAdListener(this);
//
////        mTitle = getTitle();
//
////        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
//
////        mDrawerList = (ListView) findViewById(R.id.nav_lista);
////        navListValues = getResources().getStringArray(R.array.lista_nav_drawer);
////
////        mDrawerList.setAdapter(new ArrayAdapter<>(this,
////                R.layout.drawer_list_item, navListValues));
////
////        mDrawerList.setOnItemClickListener(new AcaoNavDrawer());
////
////        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,
////                mDrawerLayout, toolbar, R.string.drawer_open,
////                R.string.drawer_close);
////
////        mDrawerToggle.setDrawerIndicatorEnabled(true);
////        mDrawerLayout.setDrawerListener(mDrawerToggle);
//
////        FragmentManager fm = getFragmentManager();
////        FragmentTransaction ft = fm.beginTransaction();
////        ft.replace(R.id.main_fragment_container, new PrincipalFragment());
////        ft.commit();
//
//    }


//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.main, menu);
//
//        return true;
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {

            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                else
                    mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_configuration:
                intent = new Intent(MainActivity.this, ConfiguracaoActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//
//        super.onPostCreate(savedInstanceState);
//
//
//    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//
//        super.onConfigurationChanged(newConfig);
//
//        mDrawerToggle.onConfigurationChanged(newConfig);
//
//    }

    @Override
    protected void onDestroy() {

        moPubView.destroy();
        super.onDestroy();

    }

    @Override
    public void onBannerLoaded(MoPubView moPubView) {
        gravarEstatisticaBanner(TipoBannerErro.BANNER_LOADED);
    }

    @Override
    public void onBannerFailed(MoPubView moPubView, MoPubErrorCode moPubErrorCode) {
        gravarEstatisticaBanner(TipoBannerErro.BANNER_FAILED);
    }

    @Override
    public void onBannerClicked(MoPubView moPubView) {
        gravarEstatisticaBanner(TipoBannerErro.BANNER_CLICKED);
    }

    @Override
    public void onBannerExpanded(MoPubView moPubView) {
        gravarEstatisticaBanner(TipoBannerErro.BANNER_EXPANDED);
    }

    @Override
    public void onBannerCollapsed(MoPubView moPubView) {
        gravarEstatisticaBanner(TipoBannerErro.BANNER_COLLAPSED);
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

    private void gravarEstatisticaBanner(TipoBannerErro tipoBannerErro) {

        ParseObject banner = new ParseObject("BannerExibhition");

        banner.put("tipoBanner", tipoBannerErro.getTipoBannerErro());
        banner.saveInBackground();

    }

}