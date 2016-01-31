package br.com.fgr.emergencia.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.general.Configuracao;
import br.com.fgr.emergencia.utils.Helper;
import butterknife.Bind;
import butterknife.OnClick;

public class ConfiguracaoActivity extends BaseActivity {

    @Bind(R.id.seek_raio)
    SeekBar seekRaio;
    @Bind(R.id.seek_hospitais)
    SeekBar seekHospitais;
    @Bind(R.id.text_mostra_raio)
    TextView textRaio;
    @Bind(R.id.text_mostra_hospitais)
    TextView textHospitais;
    @Bind(R.id.spinner_transporte)
    Spinner spinnerTransporte;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Configuracao configuracao = Helper.getConfiguracoes(getApplicationContext());

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.transportes_array, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spinnerTransporte.setAdapter(adapter);

        spinnerTransporte.setSelection(configuracao.getModo() == "driving" ? 0 : 1);

    }

    @Override
    public boolean isMainActivity() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_configuracao;
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_gravar)
    public void savePreferences() {

        Configuracao config = new Configuracao(seekRaio.getProgress(), seekHospitais.getProgress(),
                Helper.MAP_MEIO_TRANSPORTE.get(spinnerTransporte.getSelectedItemPosition()));

        Helper.setConfiguracoes(getApplicationContext(), config);

        Toast.makeText(getApplicationContext(), "Salvo com sucesso.", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();

    }

}