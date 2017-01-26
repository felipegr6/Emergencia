package br.com.fgr.emergencia.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import br.com.fgr.emergencia.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.edit_email) EditText editEmail;
    @BindView(R.id.edit_senha) EditText editSenha;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
    }

    @SuppressWarnings("unused") @OnClick(R.id.button_logar) public void login() {
        hideKeyboard();
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