package br.com.fgr.emergencia.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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

public class MainActivity extends BaseActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private String[] navListValues;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setActionBarIcon(R.drawable.ic_drawer);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				Gravity.START);

		mDrawerList = (ListView) findViewById(R.id.nav_lista);
		navListValues = getResources().getStringArray(R.array.lista_nav_drawer);

		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, navListValues));

		mDrawerList.setOnItemClickListener(new AcaoNavDrawer());

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		getToolbar(), /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {

				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

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