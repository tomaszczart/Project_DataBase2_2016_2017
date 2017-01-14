package com.nowak01011111.damian.bunchoftools.activity;

import com.nowak01011111.damian.bunchoftools.R;
import com.nowak01011111.damian.bunchoftools.api_client.ApiConnectionFragment;
import com.nowak01011111.damian.bunchoftools.api_client.ApiTaskCallback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements ApiTaskCallback {

    private EditText inputUsername;
    private EditText inputName;
    private EditText inputAddress;
    private EditText inputPassword;
    private EditText inputEmail;
    private EditText inputPhone;
    private Button signUpButton;
    private CheckBox asEmployeeCheckBox;
    private TextView labelEmail;
    private TextView labelPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inputUsername = (EditText) findViewById(R.id.input_username);
        inputName = (EditText) findViewById(R.id.input_name);
        inputAddress = (EditText) findViewById(R.id.input_address);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPhone = (EditText) findViewById(R.id.input_phone);
        signUpButton = (Button) findViewById(R.id.sign_up_button);
        asEmployeeCheckBox = (CheckBox) findViewById(R.id.as_employee_checkBox);
        labelEmail = (TextView) findViewById(R.id.label_email);
        labelPhone = (TextView) findViewById(R.id.label_phone);

        signUpButton.setOnClickListener(view1 -> {
            onSignUpButtonPressed();
        });
        asEmployeeCheckBox.setOnClickListener(view1 -> {
            onCheckBoxPressed();
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mApiConnectionFragment = ApiConnectionFragment.getInstance(getSupportFragmentManager(),this);


    }

    private void onSignUpButtonPressed() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(MainActivity.LOADING_TITLE);
        progressDialog.setMessage(MainActivity.LOADING_MESSAGE);
        progressDialog.setCancelable(false);
        progressDialog.show();

        String username = inputUsername.getText().toString();
        String name = inputName.getText().toString();
        String address = inputAddress.getText().toString();
        String password = inputPassword.getText().toString();
        String email = inputEmail.getText().toString();
        String phone = inputPhone.getText().toString();

        boolean asEmployee = asEmployeeCheckBox.isChecked();

        if (!mLoginInInProgress && mApiConnectionFragment != null) {
            mApiConnectionFragment.signUp(username, name, address, password, asEmployee, email, phone);
            mLoginInInProgress = true;
        }
    }

    private void onCheckBoxPressed() {
        if (asEmployeeCheckBox.isChecked()) {
            inputPhone.setVisibility(View.GONE);
            labelPhone.setVisibility(View.GONE);
            inputEmail.setVisibility(View.GONE);
            labelEmail.setVisibility(View.GONE);

        } else {
            labelEmail.setVisibility(View.VISIBLE);
            inputEmail.setVisibility(View.VISIBLE);
            labelPhone.setVisibility(View.VISIBLE);
            inputPhone.setVisibility(View.VISIBLE);
        }
    }

    private ApiConnectionFragment mApiConnectionFragment;

    private boolean mLoginInInProgress = false;

    ProgressDialog progressDialog;

    @Override
    public void updateFromDownload(String result, String error) {
        if (error != null && !error.isEmpty()) {
            Intent resultIntent = new Intent();
            setResult(this.RESULT_CANCELED, resultIntent);
            finish();
        } else {
            Intent resultIntent = new Intent();
            setResult(this.RESULT_OK, resultIntent);
            finish();
        }
        progressDialog.dismiss();
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch (progressCode) {
            case Progress.ERROR:
                Log.d("LoginProgress", "ERROR");
                break;
            case Progress.CONNECT_SUCCESS:
                Log.d("LoginProgress", "CONNECT_SUCCESS");
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                Log.d("LoginProgress", "GET_INPUT_STREAM_SUCCESS");
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                Log.d("LoginProgress", "PROCESS_INPUT_STREAM_IN_PROGRESS");
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                Log.d("LoginProgress", "PROCESS_INPUT_STREAM_SUCCESS");
                break;
        }
        Log.d("LoginProgress", "percentComplete");
    }

    @Override
    public void finishDownloading() {
        mLoginInInProgress = false;
        if (mApiConnectionFragment != null) {
            mApiConnectionFragment.cancelDownload();
        }
    }
}
