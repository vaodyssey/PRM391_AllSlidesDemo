package com.prm391.allslidesdemo.features.productlist.models;

import android.graphics.Bitmap;
import android.widget.ImageView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Product {
    private Bitmap image;
    private String name;
}
