package com.prm391.allslidesdemo.features.productdetails.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prm391.allslidesdemo.R;

public class ProductDetailsFragment extends Fragment {

    private LinearLayout mobilePhoneLinearLayout;
    private View view;
    public ProductDetailsFragment() {}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_product_details, container, false);
        InitializeMobilePhoneLinearLayout();
        return view;
    }
    private void InitializeMobilePhoneLinearLayout(){
        mobilePhoneLinearLayout = view.findViewById(R.id.mobilePhoneLinearLayout);
        mobilePhoneLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakePhoneCallToStore();
            }
        });
    }
    private void MakePhoneCallToStore(){
        String phone = "+84123456789";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

}