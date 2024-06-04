package com.prm391.allslidesdemo.features.productlist.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prm391.allslidesdemo.R;
import com.prm391.allslidesdemo.features.productdetails.activities.ProductDetailsActivity;
import com.prm391.allslidesdemo.features.productlist.models.Product;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private ArrayList<Product> products;
    private Context context;
    public ProductListAdapter(Context context,ArrayList<Product> products){
        this.context = context;
        this.products = products;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ProductListAdapter.ViewHolder viewHolder = new ProductListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.image.setImageBitmap(product.getImage());
        holder.productName.setText( product.getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView productName;

        public ViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.productImage);
            productName = (TextView) view.findViewById(R.id.productName);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Product product = products.get(position);

                        Intent intent = new Intent(context, ProductDetailsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("productId", product.getId()); // Pass product ID as intent extra
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}

