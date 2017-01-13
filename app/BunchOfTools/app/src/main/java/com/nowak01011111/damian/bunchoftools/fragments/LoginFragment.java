package com.nowak01011111.damian.bunchoftools.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.nowak01011111.damian.bunchoftools.R;

public class LoginFragment extends Fragment {

    private EditText inputUsername;
    private EditText inputPassword;
    private Button loginButton;
    private Button signUpButton;
    private CheckBox asEmployeeCheckBox;

    private OnLoginFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        inputUsername = (EditText) view.findViewById(R.id.input_username);
        inputPassword = (EditText) view.findViewById(R.id.input_password);
        loginButton = (Button) view.findViewById(R.id.login_button);
        signUpButton = (Button) view.findViewById(R.id.sign_up_button);
        asEmployeeCheckBox = (CheckBox) view.findViewById(R.id.as_employee_checkBox);

        loginButton.setOnClickListener(view1 -> {
            loginOperation();
        });

        signUpButton.setOnClickListener(view1 -> {
            onSignUpButtonPressed();
        });

        return view;
    }

    private void loginOperation() {
        String login = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        boolean asEmployee = asEmployeeCheckBox.isChecked();


        if (mListener != null) {
            mListener.onLoginOperation(login, password, asEmployee);
        }
    }

    private void onSignUpButtonPressed() {
        if (mListener != null) {
            mListener.onSignUp();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentInteractionListener) {
            mListener = (OnLoginFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnLoginFragmentInteractionListener {
        void onLoginOperation(String login, String password, boolean asEmployee);
        void onSignUp();
    }
}
