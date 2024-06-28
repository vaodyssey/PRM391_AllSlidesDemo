package com.prm391.allslidesdemo.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtils {
    public static void replace(int currentFragmentId, Fragment target, FragmentManager fragmentManager){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(currentFragmentId, target);
        fragmentTransaction.commit();
    }
}
