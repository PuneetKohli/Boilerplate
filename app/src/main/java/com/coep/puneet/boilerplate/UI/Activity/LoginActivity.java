package com.coep.puneet.boilerplate.UI.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.coep.puneet.boilerplate.Global.AppConstants;
import com.coep.puneet.boilerplate.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Puneet on 30-10-2015.
 */
public class LoginActivity extends BaseActivity implements Validator.ValidationListener
{
    @Bind(R.id.loginButtonFrame) FrameLayout mLoginButtonFrame;
    @Bind(R.id.loginButton) Button mLoginButton;
    @NotEmpty @Bind(R.id.editText_phone_no) EditText mPhoneEditText;
    @NotEmpty @Bind(R.id.editText_password) EditText mPasswordEditText;
    @Bind(R.id.progress_login) ProgressBar mProgressLogin;
    @Bind(R.id.progress_validate_number) ProgressBar mProgressValidate;
    @Bind(R.id.imageview_validation) ImageView mImageValidate;

    private Validator loginValidator;
    private SharedPreferences settings;

    @OnClick(R.id.loginButton)
    void login()
    {
        mLoginButton.setText("");
        mProgressLogin.setVisibility(View.VISIBLE);
        loginValidator.validate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        loginValidator = new Validator(this);
        loginValidator.setValidationListener(this);

        settings = getSharedPreferences(AppConstants.PREFS_NAME, 0);

        if (settings.getBoolean(AppConstants.PREFS_IS_LOGGED_IN, false))
        {
            //openNextActivity();
        }

/*        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("+919819954448", null, "Your Message Text", null, null);*/

    }


    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_login;
    }

    @Override
    protected void setupToolbar()
    {

    }

    @Override
    protected void setupLayout()
    {
        mLoginButtonFrame.setVisibility(View.GONE);
        mProgressValidate.setVisibility(View.GONE);
        mImageValidate.setVisibility(View.GONE);
        mPasswordEditText.setVisibility(View.GONE);
        mPhoneEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() == 10)
                {
                    //Validate phone number
                    if (validatePhoneNumber())
                    {
                        sendOtpSMS();
                        showOtpField();
                    }
                }
            }
        });
    }

    boolean validatePhoneNumber()
    {
        return true;
    }

    void sendOtpSMS()
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("+919819954448", null, "OTP is 5490", null, null);
    }

    void showOtpField()
    {
        mImageValidate.setVisibility(View.VISIBLE);
        mPasswordEditText.setVisibility(View.VISIBLE);
        mLoginButtonFrame.setVisibility(View.VISIBLE);
        mProgressValidate.setVisibility(View.GONE);
    }


    void openNextActivity()
    {
        navigator.openNewActivity(LoginActivity.this, new HomeActivity());
        mProgressLogin.setVisibility(View.INVISIBLE);
        mLoginButton.setText("LOG IN");
    }

    @Override
    public void onValidationSucceeded()
    {
        settings.edit().putBoolean(AppConstants.PREFS_IS_LOGGED_IN, true).commit();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors)
    {

    }

    void resetButton()
    {
        mProgressLogin.setVisibility(View.INVISIBLE);
        mLoginButton.setText("LOG IN");
    }
}
