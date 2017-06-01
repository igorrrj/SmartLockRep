package com.example.igor.smartlock;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.igor.smartlock.Registration.RegisterActivity;

/**
 * Created by Igor on 28.05.2017.
 */

public class LoginActivity extends AccountAuthenticatorActivity {

    public static final String AUTHTOKEN_TYPE="authtokenType";
    public static final String USERNAME = "username";

    public static final String PARAM_LOGIN="USER_NAME";
    public static final String PARAM_PASSWORD="TOKEN";

    Button loginButton;
    EditText name,password;
    TextView registerText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton=(Button)findViewById(R.id.btn_login);
        name=(EditText)findViewById(R.id.input_user_name);
        password=(EditText)findViewById(R.id.input_password);

        SpannableString str = new SpannableString("Register");
        str.setSpan(new UnderlineSpan(), 0, str.length(), Spanned.SPAN_PARAGRAPH);
        registerText = (TextView) findViewById(R.id.link_signup);
        registerText.setText(str);

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_name=name.getText().toString().trim();
                final String user_password=password.getText().toString().trim();
                endAuth(user_name,user_password);
                openMain();
            }
        });



    }
    void endAuth(String user_name,String user_password)
    {
        AccountManager accountManager=AccountManager.get(this);
        final Account account=new Account(user_name,getString(R.string.accountType));

        Bundle data=new Bundle();
        data.putString(PARAM_LOGIN,user_name);
        data.putString(PARAM_PASSWORD,user_password);

        accountManager.addAccountExplicitly(account,user_password,data);
        accountManager.setPassword(account, user_password);

        Bundle result = new Bundle();
        result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
        result.putString(AccountManager.KEY_PASSWORD, user_password);

        setAccountAuthenticatorResult(result);
    }

    private void openMain() {
        Intent intent = new Intent(LoginActivity.this, LockActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("IntentLogin",intent.getData()+"");
    }

}
