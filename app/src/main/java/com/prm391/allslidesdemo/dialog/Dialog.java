package com.prm391.allslidesdemo.dialog;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class Dialog extends DialogFragment {
  private DialogParameters dialogParameters;

    public Dialog(DialogParameters dialogParameters) {
       this.dialogParameters = dialogParameters;
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(dialogParameters.getMessage())
                .setPositiveButton(dialogParameters.getPositiveButtonTxt(), dialogParameters.getPositiveButtonAction())
                .setNegativeButton(dialogParameters.getNegativeButtonTxt(), dialogParameters.getNegativeButtonAction());
        // Create the AlertDialog object and return it.
        return builder.create();
    }
}
