package br.com.fgr.emergencia.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.utils.Helper;

public class AjudaListaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda_lista);

        Button btnEntendi = (Button) findViewById(R.id.btn_entendi);

        btnEntendi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Helper.setFirstTimeTutorial(AjudaListaActivity.this);
                finish();

            }

        });

    }

}