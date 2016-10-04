package br.com.fgr.emergencia.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.general.Configuration;
import br.com.fgr.emergencia.models.general.TransportEnum;
import br.com.fgr.emergencia.utils.Helper;
import butterknife.Bind;
import butterknife.OnClick;

public class ConfigurationActivity extends BaseActivity {

    @Bind(R.id.seek_radius) SeekBar seekRadius;
    @Bind(R.id.seek_hospitals) SeekBar seekHospitals;
    @Bind(R.id.lbl_info_radius) TextView lblRadius;
    @Bind(R.id.lbl_info_hospitals) TextView lblHospitals;
    @Bind(R.id.spinner_transport) Spinner spinnerTransport;

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Configuration configuration = Helper.getConfiguracoes(getApplicationContext());

        seekRadius.setProgress(configuration.getRadius());
        seekHospitals.setProgress(configuration.getHospitals());
        lblRadius.setText(
            Helper.formatarInformacao(Helper.CONST_RAIO, seekRadius.getProgress(), true));
        lblHospitals.setText(
            Helper.formatarInformacao(Helper.CONST_HOSPITAIS, seekHospitals.getProgress(), true));

        seekRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lblRadius.setText(Helper.formatarInformacao(Helper.CONST_RAIO, progress, true));
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekHospitals.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lblHospitals.setText(
                    Helper.formatarInformacao(Helper.CONST_HOSPITAIS, progress, true));
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ArrayAdapter<CharSequence> adapter =
            ArrayAdapter.createFromResource(this, R.array.transportes_array,
                R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spinnerTransport.setAdapter(adapter);

        spinnerTransport.setSelection(
            configuration.getMode().equals(TransportEnum.DRIVING.getModeTransport()) ? 0 : 1);
    }

    @Override public boolean isMainActivity() {
        return false;
    }

    @Override protected int getLayoutResource() {
        return R.layout.activity_configuracao;
    }

    @Override protected Toolbar getToolbar() {
        return toolbar;
    }

    @SuppressWarnings("unused") @OnClick(R.id.btn_save) public void savePreferences() {

        Configuration config =
            new Configuration(seekRadius.getProgress(), seekHospitals.getProgress(),
                Helper.MAP_MEIO_TRANSPORTE.get(spinnerTransport.getSelectedItemPosition()));

        Helper.setConfiguracoes(getApplicationContext(), config);

        Toast.makeText(getApplicationContext(), R.string.msg_success, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
}