package com.prm391.allslidesdemo.features.productlist.models;

import android.graphics.Bitmap;
import android.widget.ImageView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int id;
    private String name;
    private String description;
    private Double price;
    private Bitmap image;
}
