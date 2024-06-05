package com.prm391.allslidesdemo.features.productlist.activities;

import android.os.Bundle;
import android.view.Menu;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prm391.allslidesdemo.R;

import com.prm391.allslidesdemo.constants.AssetsFolderConstants;
import com.prm391.allslidesdemo.constants.RecyclerViewConstants;
import com.prm391.allslidesdemo.constants.SqlLiteConstants;
import com.prm391.allslidesdemo.features.productlist.adapters.GridSpacingItemDecoration;
import com.prm391.allslidesdemo.features.productlist.adapters.ProductListAdapter;
import com.prm391.allslidesdemo.features.productlist.models.Product;
import com.prm391.allslidesdemo.utils.ImageUtils;
import com.prm391.allslidesdemo.utils.JsonUtils;
import com.prm391.allslidesdemo.repository.ProductsRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private List<Product> productList;
    private RecyclerView productsRecyclerView;
    private ProductsRepository productsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            Setup();
            InitializeClassVariables();
            if(productsRepository.isProductsTableEmpty())
                InsertProductsFromJsonToDb();
            LoadProductsToActivity();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        return true;
    }
    private void Setup(){
        SetupAndroid();
        SetupToolbar();
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
    private void SetupToolbar(){
        Toolbar toolbar
                = findViewById(R.id.productListToolbar);
        setSupportActionBar(toolbar);
    }
    private void InitializeClassVariables() {
        productList = new ArrayList<>();
        productsRepository = new ProductsRepository(this.getApplicationContext());
    }

    private void InsertProductsFromJsonToDb() throws JSONException, IOException {

        JSONObject jsonObject = new JSONObject(JsonUtils.loadJSONInAssetsFolder(this, "products/products.json"));
        JSONArray productJsonArray = jsonObject.getJSONArray("productList");

        for (int i = 0; i < productJsonArray.length(); i++) {
            JSONObject productJsonObject = productJsonArray.getJSONObject(i);
            Product product = ExtractProductFromJsonObject(productJsonObject);
            productsRepository.addNewProduct(product);
        }
    }
    private Product ExtractProductFromJsonObject(JSONObject productJSONObject) throws JSONException{
        Product product = new Product();
        product.setName(productJSONObject.getString(SqlLiteConstants.NAME_COL));
        product.setDescription(productJSONObject.getString(SqlLiteConstants.DESCRIPTION_COL));
        product.setPrice(Double.parseDouble(productJSONObject.getString(SqlLiteConstants.PRICE_COL)));
        return product;
    }
    private void LoadProductsToActivity() throws IOException{
        productList = productsRepository.loadAllProducts();
        SetProductImageByProductId();
        InitializeProductsRecyclerView();
    }
    private void SetProductImageByProductId()throws IOException {
        for(Product product:productList){
            product.setImage(ImageUtils.getBitmapFromAssets(this,
                    AssetsFolderConstants.PRODUCT_IMAGE_BY_ID(product.getId())));
        }
    }
    private void InitializeProductsRecyclerView(){
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        productsRecyclerView.setLayoutManager(gridLayoutManager);
        productsRecyclerView.addItemDecoration(new GridSpacingItemDecoration(
                RecyclerViewConstants.SPAN_COUNT,
                RecyclerViewConstants.SPACING,
                RecyclerViewConstants.INCLUDE_EDGE
        ));
        ProductListAdapter adapter = new ProductListAdapter(getApplicationContext(),productList);
        productsRecyclerView.setAdapter(adapter);
    }




}