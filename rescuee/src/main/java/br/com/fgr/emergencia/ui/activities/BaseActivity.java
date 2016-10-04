package br.com.fgr.emergencia.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import br.com.fgr.emergencia.R;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        ButterKnife.bind(this);

        if (getToolbar() != null) {

            setSupportActionBar(getToolbar());

            if (!isMainActivity()) {

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                getToolbar().setNavigationOnClickListener(new View.OnClickListener() {

                    @Override public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = this.getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.vermelho_status));
        }
    }

    @Override protected void onDestroy() {

        ButterKnife.unbind(this);

        super.onDestroy();
    }

    public abstract boolean isMainActivity();

    protected abstract int getLayoutResource();

    protected abstract Toolbar getToolbar();
}
