package com.prm391.allslidesdemo.features.facebook;

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

import com.prm391.allslidesdemo.dialog.Dialog;
import com.prm391.allslidesdemo.R;
import com.prm391.allslidesdemo.dialog.DialogParameters;
import com.prm391.allslidesdemo.dialog.InvalidCredentialsOnClickListener;
import com.prm391.allslidesdemo.dialog.LoginSuccessfulOnClickListener;
import com.prm391.allslidesdemo.dto.LoggedInUser;
import com.prm391.allslidesdemo.features.instagram.InstagramFragment;
import com.prm391.allslidesdemo.features.productdetails.activities.ProductDetailsActivity;
import com.prm391.allslidesdemo.features.productlist.activities.ProductListActivity;
import com.prm391.allslidesdemo.utils.FragmentUtils;

public class FacebookFragment extends Fragment {
    private ImageButton toInstagramUIButton;
    private ViewGroup container;
    private View view;
    private Button loginButton;
    private LayoutInflater inflater;
    private EditText emailInput;
    private EditText passwordInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        this.inflater = inflater;
        InitializeChildFragments();
        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
        emailInput.setText("");
        passwordInput.setText("");
    }
    private void InitializeChildFragments() {
        view = inflater.inflate(com.prm391.allslidesdemo.R.layout.fragment_facebook, container, false);
        emailInput = view.findViewById(R.id.email_input);
        passwordInput = view.findViewById(R.id.password_input);
        InitializeLoginButton();
        InitializeNavigateToInstagramUIButton();
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
        Intent intent = new Intent();
        intent.setClass(getActivity(), ProductListActivity.class);
        getActivity().startActivity(intent);
    }

    private void InitializeNavigateToInstagramUIButton() {
        toInstagramUIButton = view.findViewById(R.id.toInstagramUIBtn);
        toInstagramUIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigateToInstagramUI();
            }
        });
    }

    private void NavigateToInstagramUI() {
        InstagramFragment instagramFragment = new InstagramFragment();
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction
                .replace(R.id.nav_container, instagramFragment).commit();
    }

    private void ShowInvalidCredentialsDialog() {
        Dialog dialog = new Dialog
                (new DialogParameters(
                "The credentials are incorrect. Please try again."
                , "Ok", null,
                new InvalidCredentialsOnClickListener(), null));



        dialog.show(getParentFragmentManager(),"invalid_credentials_dialog");
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
