package br.com.fgr.emergencia.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Toast;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.general.Configuracao;
import br.com.fgr.emergencia.utils.Helper;

public class ConfiguracaoActivity extends BaseActivity {

    private SeekBar seekRaio;
    private SeekBar seekHospitais;
    private AppCompatTextView textRaio;
    private AppCompatTextView textHospitais;
    private AppCompatSpinner spinnerTransporte;
    private AppCompatButton buttonGravar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = this.getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.vermelho_status));

        }

        Configuracao configuracao = Helper.getConfiguracoes(getApplicationContext());

        seekRaio = (SeekBar) findViewById(R.id.seek_raio);
        seekHospitais = (SeekBar) findViewById(R.id.seek_hospitais);
        textRaio = (AppCompatTextView) findViewById(R.id.text_mostra_raio);
        textHospitais = (AppCompatTextView) findViewById(R.id.text_mostra_hospitais);
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

                Configuracao config = new Configuracao(seekRaio.getProgress(), seekHospitais.getProgress(),
                        Helper.MAP_MEIO_TRANSPORTE.get(spinnerTransporte.getSelectedItemPosition()));

                Helper.setConfiguracoes(getApplicationContext(), config);

                Toast.makeText(getApplicationContext(), "Salvo com sucesso.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();

            }

        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.transportes_array, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spinnerTransporte.setAdapter(adapter);

        spinnerTransporte.setSelection(configuracao.getModo() == "driving" ? 0 : 1);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_configuracao;
    }

}