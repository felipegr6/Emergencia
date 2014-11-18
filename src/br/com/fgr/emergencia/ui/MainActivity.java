package br.com.fgr.emergencia.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	ImageButton emergencia;
	Button meusHospitais;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		emergencia = (ImageButton) findViewById(R.id.btnEmergencia);
		meusHospitais = (Button) findViewById(R.id.btnMeusHospitais);

		chamarEmergencia(emergencia);
		cadastrarHospitais(meusHospitais);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

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

	protected void chamarEmergencia(ImageButton btn) {

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(MainActivity.this,
						LocalizacaoActivity.class);

				startActivityForResult(i, 1);

			}

		});

	}

	protected void cadastrarHospitais(Button btn) {

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(MainActivity.this,
						FormularioActivity.class);

				startActivityForResult(i, 2);

			}
		});

	}

}