package com.prm391.allslidesdemo.features.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.prm391.allslidesdemo.R;
import com.prm391.allslidesdemo.dialog.Dialog;
import com.prm391.allslidesdemo.dialog.DialogParameters;
import com.prm391.allslidesdemo.dialog.InvalidCredentialsOnClickListener;
import com.prm391.allslidesdemo.dialog.LoginSuccessfulOnClickListener;
import com.prm391.allslidesdemo.dto.LoggedInUser;
import com.prm391.allslidesdemo.features.facebook.FacebookFragment;
import com.prm391.allslidesdemo.features.productlist.activities.ProductListActivity;

public class InstagramFragment extends Fragment {
    private ImageButton toInstagramUIButton;
    private ViewGroup container;
    private View view;
    private Button loginButton;
    private LayoutInflater inflater;
    private ImageButton toFacebookUIButton;
    private EditText emailInput;
    private EditText passwordInput;
    @Override
    public void onPause() {
        super.onPause();
        emailInput.setText("");
        passwordInput.setText("");
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        this.inflater = inflater;
        InitializeChildFragments();
        return view;
    }
    private void InitializeChildFragments() {
        view = inflater.inflate(com.prm391.allslidesdemo.R.layout.fragment_instagram, container, false);
        emailInput = view.findViewById(R.id.email_input);
        passwordInput = view.findViewById(R.id.password_input);
        InitializeLoginButton();
        InitializeNavigationToFacebookUIButton();
    }

    private void InitializeLoginButton() {
        loginButton = view.findViewById(R.id.login_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateCredentials();
            }
        });
    }
    private void ValidateCredentials() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        if (!email.equals("admin") || !password.equals("123456")) {
            ShowInvalidCredentialsDialog();
            return;
        }
        ShowValidCredentialsDialog(new LoggedInUser(email,password));
        Intent intent = new Intent();
        intent.setClass(getActivity(), ProductListActivity.class);
        getActivity().startActivity(intent);

    }
    private void InitializeNavigationToFacebookUIButton(){
        toFacebookUIButton =  view.findViewById(R.id.toFacebookUIBtn);
        toFacebookUIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hello world!");
                NavigateToFbUI();

            }
        });
    }
    private void NavigateToFbUI(){
        FacebookFragment facebookFragment = new FacebookFragment();
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction
                .replace(R.id.nav_container,facebookFragment).commit();
    }
    private void ShowInvalidCredentialsDialog() {
        Dialog dialog = new Dialog(new DialogParameters(
                "The credentials are incorrect. Please try again."
                , "Ok", null,
                new InvalidCredentialsOnClickListener(), null));



        dialog.show(getParentFragmentManager(), "invalid_credentials_dialog");
    }

    private void ShowValidCredentialsDialog(LoggedInUser loggedInUser) {
        Dialog dialog = new Dialog(new DialogParameters(
                "Successfully logged in."
                , "Ok", null,
                new LoginSuccessfulOnClickListener(getContext(),loggedInUser.getEmail()),
                null));
        dialog.show(getParentFragmentManager(), "valid_credentials_dialog");
    }
}

