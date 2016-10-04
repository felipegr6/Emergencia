package br.com.fgr.emergencia.ui.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.events.PermissionEvent;
import br.com.fgr.emergencia.models.general.ErrorBannerEnum;
import br.com.fgr.emergencia.ui.fragments.MainFragment;
import butterknife.Bind;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.parse.ParseObject;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity implements MoPubView.BannerAdListener {

    private static final String MOPUB_BANNER_AD_UNIT_ID = "cfb1d11b4942442582b5ed69cf94937f";

    @Bind(R.id.mopub_sample_ad) MoPubView moPubView;
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        moPubView.setAdUnitId(MOPUB_BANNER_AD_UNIT_ID);
        moPubView.loadAd();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_fragment_container, MainFragment.newInstance());
        ft.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {

            case R.id.action_configuration:
                intent = new Intent(MainActivity.this, ConfigurationActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override protected void onDestroy() {

        moPubView.destroy();
        super.onDestroy();
    }

    @Override public boolean isMainActivity() {
        return true;
    }

    @Override protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override public void onBannerLoaded(MoPubView moPubView) {
        saveBannerStats(ErrorBannerEnum.BANNER_LOADED);
    }

    @Override public void onBannerFailed(MoPubView moPubView, MoPubErrorCode moPubErrorCode) {
        saveBannerStats(ErrorBannerEnum.BANNER_FAILED);
    }

    @Override public void onBannerClicked(MoPubView moPubView) {
        saveBannerStats(ErrorBannerEnum.BANNER_CLICKED);
    }

    @Override public void onBannerExpanded(MoPubView moPubView) {
        saveBannerStats(ErrorBannerEnum.BANNER_EXPANDED);
    }

    @Override public void onBannerCollapsed(MoPubView moPubView) {
        saveBannerStats(ErrorBannerEnum.BANNER_COLLAPSED);
    }

    private void saveBannerStats(ErrorBannerEnum tipoBannerErro) {

        ParseObject banner = new ParseObject("BannerExibhition");

        banner.put("tipoBanner", tipoBannerErro.getErrorBannerType());
        banner.saveInBackground();
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case 16:
                if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    EventBus.getDefault().postSticky(new PermissionEvent(permissions[0]));
                } else {
                    Toast.makeText(this, "Permissão não fornecida.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
