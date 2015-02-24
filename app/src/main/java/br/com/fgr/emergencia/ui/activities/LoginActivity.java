package br.com.fgr.emergencia.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import br.com.fgr.emergencia.R;
import br.com.fgr.emergencia.utils.Helper;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    protected EditText editEmail;
    protected EditText editSenha;
    protected Button buttonLogar;
    protected LoginButton fbLoginButton;
    private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback = new Session.StatusCallback() {

        @Override
        public void call(Session session, SessionState sessionState, Exception e) {
            onSessionStateChange(session, sessionState, e);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.ic_action_back);

        editEmail = (EditText) findViewById(R.id.edit_email);
        editSenha = (EditText) findViewById(R.id.edit_senha);
        buttonLogar = (Button) findViewById(R.id.button_logar);

        fbLoginButton = (LoginButton) findViewById(R.id.authButton);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        fbLoginButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));

        buttonLogar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hideKeyboard();

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
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.info_valida), Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.info_invalida), Toast.LENGTH_SHORT).show();

                    } catch (ParseException e) {

                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.erro_comum), Toast.LENGTH_LONG).show();
                        Log.e("ParseException", e.toString());

                    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.erro_comum), Toast.LENGTH_LONG).show();
                        Log.e("SecurityException", e.toString());

                    }

                } else {

                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.formato_invalido), Toast.LENGTH_SHORT).show();

                }

            }

        });

    }

    @Override
    public void onResume() {

        super.onResume();

        uiHelper.onResume();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onPause() {

        super.onPause();

        uiHelper.onPause();

    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        uiHelper.onDestroy();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        uiHelper.onSaveInstanceState(outState);

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

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {

        if (state.isOpened())
            Log.i(TAG, "Logged in...");
        else if (state.isClosed())
            Log.i(TAG, "Logged out...");

    }

    private void hideKeyboard() {

        View view = this.getCurrentFocus();

        if (view != null) {

            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        }

    }

}