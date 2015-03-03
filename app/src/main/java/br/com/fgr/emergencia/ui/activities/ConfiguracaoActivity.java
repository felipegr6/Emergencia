package br.com.fgr.emergencia.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.models.general.Configuracao;
import br.com.fgr.emergencia.utils.Helper;

public class ConfiguracaoActivity extends BaseActivity {

    private SeekBar seekRaio;
    private SeekBar seekHospitais;
    private Button buttonGravar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.ic_action_back);

        Configuracao configuracao = Helper.getConfiguracoes(getApplicationContext());

        seekRaio = (SeekBar) findViewById(R.id.seek_raio);
        seekHospitais = (SeekBar) findViewById(R.id.seek_hospitais);
        buttonGravar = (Button) findViewById(R.id.button_gravar);

        seekRaio.setProgress(configuracao.getRaio());
        seekHospitais.setProgress(configuracao.getHospitais());

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