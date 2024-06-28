package com.prm391.allslidesdemo.dialog;

import android.content.DialogInterface;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DialogParameters {
    private String message;
    private String positiveButtonTxt;
    private String negativeButtonTxt;
    private DialogInterface.OnClickListener positiveButtonAction;
    private DialogInterface.OnClickListener negativeButtonAction;
}
