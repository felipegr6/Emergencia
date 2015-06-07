package br.com.fgr.emergencia.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Toast;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.general.Configuracao;
import br.com.fgr.emergencia.utils.Helper;

public class ConfiguracaoActivity extends BaseActivity {

    private SeekBar seekRaio;
    private SeekBar seekHospitais;
    private AppCompatEditText textRaio;
    private AppCompatEditText textHospitais;
    private AppCompatSpinner spinnerTransporte;
    private AppCompatButton buttonGravar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Configuracao configuracao = Helper.getConfiguracoes(getApplicationContext());

        seekRaio = (SeekBar) findViewById(R.id.seek_raio);
        seekHospitais = (SeekBar) findViewById(R.id.seek_hospitais);
        textRaio = (AppCompatEditText) findViewById(R.id.text_mostra_raio);
        textHospitais = (AppCompatEditText) findViewById(R.id.text_mostra_hospitais);
        spinnerTransporte = (AppCompatSpinner) findViewById(R.id.spinner_transporte);
        buttonGravar = (AppCompatButton) findViewById(R.id.button_gravar);

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

                if (Helper.setConfiguracoes(getApplicationContext(), config)) {

                    Toast.makeText(getApplicationContext(), "Salvo com sucesso.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();

                } else
                    Toast.makeText(getApplicationContext(), "Houve problemas, tente novamente", Toast.LENGTH_SHORT).show();

            }

        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.transportes_array, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spinnerTransporte.setAdapter(adapter);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_configuracao;
    }

}