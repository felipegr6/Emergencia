package br.com.fgr.emergencia.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import br.com.fgr.emergencia.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.edit_email) EditText editEmail;
    @Bind(R.id.edit_senha) EditText editSenha;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
    }

    @SuppressWarnings("unused") @OnClick(R.id.button_logar) public void login() {

        hideKeyboard();

        //String usuario = editEmail.getText().toString();
        //String senha = editSenha.getText().toString();
        //String marujo;
        //String senhaUsual;
        //
        //if (Helper.validarEmail(usuario) && Helper.validarSenha(senha)) {
        //
        //    ParseObject registroUnico;
        //    ParseQuery<ParseObject> registroUsuario = ParseQuery.getQuery("Usuario");
        //    registroUsuario.setLimit(1);
        //    registroUsuario.whereEqualTo("email", usuario);
        //
        //    try {
        //
        //        registroUnico = registroUsuario.getFirst();
        //        marujo = registroUnico.getString("marujo");
        //        senhaUsual = registroUnico.getString("senha");
        //
        //        if (Helper.validatePassword(senha, marujo, senhaUsual)) {
        //            Toast.makeText(LoginActivity.this,
        //                getResources().getString(R.string.info_valida), Toast.LENGTH_SHORT).show();
        //        } else {
        //            Toast.makeText(LoginActivity.this,
        //                getResources().getString(R.string.info_invalida), Toast.LENGTH_SHORT)
        //                .show();
        //        }
        //    } catch (ParseException e) {
        //
        //        Toast.makeText(LoginActivity.this, getResources().getString(R.string.erro_comum),
        //            Toast.LENGTH_LONG).show();
        //        Log.e("ParseException", e.toString());
        //    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        //
        //        Toast.makeText(LoginActivity.this, getResources().getString(R.string.erro_comum),
        //            Toast.LENGTH_LONG).show();
        //        Log.e("SecurityException", e.toString());
        //    }
        //} else {
        //
        //    Toast.makeText(LoginActivity.this, getResources().getString(R.string.formato_invalido),
        //        Toast.LENGTH_SHORT).show();
        //}
    }

    @Override public boolean isMainActivity() {
        return false;
    }

    @Override protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override protected Toolbar getToolbar() {
        return toolbar;
    }

    private void hideKeyboard() {

        View view = this.getCurrentFocus();

        if (view != null) {

            InputMethodManager inputManager =
                (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}