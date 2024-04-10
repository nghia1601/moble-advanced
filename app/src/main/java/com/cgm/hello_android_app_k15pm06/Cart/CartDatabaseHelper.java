package com.cgm.hello_android_app_k15pm06.Cart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CartDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cart.db";
    private static final int DATABASE_VERSION = 1;

    public CartDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng giỏ hàng
        String CREATE_CART_TABLE = "CREATE TABLE cart (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "productId INTEGER," +
                "productName TEXT," +
                "quantity INTEGER," +
                "price REAL," +
                "image TEXT)"; // Thêm cột hình ảnh
        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS cart");
        // Tạo lại bảng
        onCreate(db);
    }
}
