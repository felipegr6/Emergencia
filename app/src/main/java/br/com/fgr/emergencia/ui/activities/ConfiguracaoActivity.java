package br.com.fgr.emergencia.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.general.Configuracao;
import br.com.fgr.emergencia.utils.Helper;

public class ConfiguracaoActivity extends BaseActivity {

    private SeekBar seekRaio;
    private SeekBar seekHospitais;
    private EditText textRaio;
    private EditText textHospitais;
    private Spinner spinnerTransporte;
    private Button buttonGravar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.ic_action_back);

        Configuracao configuracao = Helper.getConfiguracoes(getApplicationContext());

        seekRaio = (SeekBar) findViewById(R.id.seek_raio);
        seekHospitais = (SeekBar) findViewById(R.id.seek_hospitais);
        textRaio = (EditText) findViewById(R.id.text_mostra_raio);
        textHospitais = (EditText) findViewById(R.id.text_mostra_hospitais);
        spinnerTransporte = (Spinner) findViewById(R.id.spinner_transporte);
        buttonGravar = (Button) findViewById(R.id.button_gravar);

        seekRaio.setProgress(configuracao.getRaio());
        seekHospitais.setProgress(configuracao.getHospitais());
        textRaio.setText(Helper.formatarInformacao(Helper.CONST_RAIO, seekRaio.getProgress(), true));
        textHospitais.setText(Helper.formatarInformacao(Helper.CONST_HOSPITAIS, seekHospitais.getProgress(), true));

        seekRaio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textRaio.setText(Helper.formatarInformacao(Helper.CONST_RAIO, progress, true));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        seekHospitais.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textHospitais.setText(Helper.formatarInformacao(Helper.CONST_HOSPITAIS, progress, true));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        buttonGravar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Configuracao config = new Configuracao(seekRaio.getProgress(), seekHospitais.getProgress());

                if (Helper.setConfiguracoes(getApplicationContext(), config))
                    Toast.makeText(getApplicationContext(), "Salvo com sucesso.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Houve problemas, tente novamente", Toast.LENGTH_SHORT).show();

            }

        });

    }

    @Override
    protected int getLayoutResource() {

        return R.layout.activity_configuracao;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            return true;

        return super.onOptionsItemSelected(item);

    }

}