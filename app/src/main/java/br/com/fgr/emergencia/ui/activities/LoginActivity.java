package br.com.fgr.emergencia.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.utils.Helper;

public class LoginActivity extends BaseActivity {

    protected EditText editEmail;
    protected EditText editSenha;
    protected Button buttonLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.ic_action_back);

        editEmail = (EditText) findViewById(R.id.edit_email);
        editSenha = (EditText) findViewById(R.id.edit_senha);
        buttonLogar = (Button) findViewById(R.id.button_logar);

        buttonLogar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String usuario = editEmail.getText().toString();
                String senha = editSenha.getText().toString();
                String marujo;
                String senhaUsual;

                if (Helper.validarEmail(usuario) && Helper.validarSenha(senha)) {

                    ParseObject registroUnico;
                    ParseQuery<ParseObject> registroUsuario = ParseQuery.getQuery("Usuario");
                    registroUsuario.setLimit(1);
                    registroUsuario.whereEqualTo("email", usuario);

                    try {

                        registroUnico = registroUsuario.getFirst();
                        marujo = registroUnico.getString("marujo");
                        senhaUsual = registroUnico.getString("senha");

                        if (Helper.validatePassword(senha, marujo, senhaUsual))
                            Toast.makeText(LoginActivity.this, "Usuário e senha corretos.", Toast.LENGTH_SHORT).show();

                        else
                            Toast.makeText(LoginActivity.this, "Usuário e/ou senha incorretos.", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Não foi possível logar, tente novamente.", Toast.LENGTH_LONG).show();
                    }

                }

            }

        });

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