package br.com.fgr.emergencia.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;

import br.com.fgr.emergencia.R;

public class ConfiguracaoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setActionBarIcon(R.drawable.ic_action_back);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

}