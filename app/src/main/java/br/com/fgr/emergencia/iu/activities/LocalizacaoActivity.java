package br.com.fgr.emergencia.iu.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.iu.fragments.LocalizacaoFragment;

public class LocalizacaoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.loc_fragment_container, new LocalizacaoFragment());
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.localizacao, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings)
            return true;

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected int getLayoutResource() {

        return R.layout.activity_localizacao;

    }

}