package com.prm391.allslidesdemo.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.prm391.allslidesdemo.constants.SqlLiteConstants.*;

import com.prm391.allslidesdemo.features.productlist.models.Product;
import com.prm391.allslidesdemo.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductsRepository extends SQLiteOpenHelper {

    public ProductsRepository(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public void addNewProduct(Product product) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, product.getName());
        values.put(DESCRIPTION_COL, product.getDescription());
        values.put(PRICE_COL, product.getPrice());

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }
    @SuppressLint("Range")
    public List<Product> loadAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NAME,
                null, // Columns (null for all)
                null, // Selection
                null, // SelectionArgs
                null, // GroupBy
                null, // Having
                null // OrderBy
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    // Create a new product object
                    Product product = new Product();
                    // Populate product object from cursor
                    product.setId(cursor.getInt(cursor.getColumnIndex(ID_COL)));
                    product.setName(cursor.getString(cursor.getColumnIndex(NAME_COL)));
                    product.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION_COL)));
                    product.setPrice(cursor.getDouble(cursor.getColumnIndex(PRICE_COL)));
                    // Add product to the list
                    productList.add(product);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return productList;
    }
    public boolean isProductsTableEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return count == 0;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + PRICE_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
