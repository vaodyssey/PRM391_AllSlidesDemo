package com.prm391.allslidesdemo.features.productlist.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Adapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prm391.allslidesdemo.R;
import com.prm391.allslidesdemo.features.productlist.adapters.GridSpacingItemDecoration;
import com.prm391.allslidesdemo.features.productlist.adapters.ProductListAdapter;
import com.prm391.allslidesdemo.features.productlist.models.Product;
import com.prm391.allslidesdemo.utils.ImageUtils;
import com.prm391.allslidesdemo.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private ArrayList<Product> productList;
    private RecyclerView productsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            SetupAndroid();
            InitializeClassVariables();
            LoadProductsFromJsonFile();
            InitializeProductsRecyclerView();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void InitializeClassVariables() {
        productList = new ArrayList<>();
    }

    private void LoadProductsFromJsonFile() throws JSONException, IOException {

        JSONObject jsonObject = new JSONObject(JsonUtils.loadJSONInAssetsFolder(this, "products/products.json"));
        JSONArray productJsonArray = jsonObject.getJSONArray("productList");

        for (int i = 0; i < productJsonArray.length(); i++) {
            JSONObject product = productJsonArray.getJSONObject(i);
            String productName = product.getString("name");
            Bitmap productImage = ImageUtils.getBitmapFromAssets(this,"products/images/"+productName+".png");
            productList.add(new Product(productImage,productName));
        }
    }
    private void InitializeProductsRecyclerView(){
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        // Setting the layout as linear
        // layout for vertical orientation
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        productsRecyclerView.setLayoutManager(gridLayoutManager);

        int spanCount = 3; // 3 columns
        int spacing = 80; // 50px
        boolean includeEdge = true;
        productsRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        // Sending reference and data to Adapter
        ProductListAdapter adapter = new ProductListAdapter(productList);

        // Setting Adapter to RecyclerView
        productsRecyclerView.setAdapter(adapter);
    }



    private void SetupAndroid() {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}