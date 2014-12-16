package br.com.fgr.emergencia.ui.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.com.fgr.emergencia.bd.BDHospitalHelper;
import br.com.fgr.emergencia.bd.HospitalProvider;
import br.com.fgr.emergencia.ui.R;
import br.com.fgr.emergencia.ui.fragments.PrincipalFragment;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] navListValues;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Cursor c = getContentResolver().query(HospitalProvider.CONTENT_URI, BDHospitalHelper.PROJECAO, null, null, null);

        while (c.moveToNext()) {
            Log.w("Cursor", c.getString(BDHospitalHelper.NUM_COLUNA_NOME));
        }

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

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);

                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);

    }

    @Override
    protected int getLayoutResource() {

        return R.layout.activity_main;

    }

    private class AcaoNavDrawer implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            switch (position) {

                case 0:
                    Intent i0 = new Intent(MainActivity.this,
                            FormularioActivity.class);
                    startActivity(i0);
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;

            }

        }

    }

}