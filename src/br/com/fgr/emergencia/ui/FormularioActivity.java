package br.com.fgr.emergencia.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class FormularioActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setActionBarIcon(R.drawable.ic_action_back);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.formulario, menu);

		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.action_settings)
			return true;

		return super.onOptionsItemSelected(item);

	}

	@Override
	protected int getLayoutResource() {

		return R.layout.activity_formulario;

	}

}