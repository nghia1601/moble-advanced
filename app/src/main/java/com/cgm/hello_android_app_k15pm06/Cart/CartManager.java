package com.cgm.hello_android_app_k15pm06.Cart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cgm.hello_android_app_k15pm06.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private CartDatabaseHelper dbHelper;

    public CartManager(Context context) {
        dbHelper = new CartDatabaseHelper(context);
    }

    public void addToCart(Product product, int quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        String selectQuery = "SELECT * FROM cart WHERE productId = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(product.getId())});

        if (cursor.moveToFirst()) {
            // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
            int currentQuantity = cursor.getInt(cursor.getColumnIndex("quantity"));
            int newQuantity = currentQuantity + quantity;

            ContentValues values = new ContentValues();
            values.put("quantity", newQuantity);

            // Cập nhật số lượng cho sản phẩm trong giỏ hàng
            db.update("cart", values, "productId = ?", new String[]{String.valueOf(product.getId())});
        } else {
            // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới vào
            ContentValues values = new ContentValues();
            values.put("productId", product.getId());
            values.put("productName", product.getTitle());
            values.put("quantity", quantity);
            values.put("price", product.getPrice());
            values.put("image", product.getImage()); // Thêm đường dẫn hình ảnh vào cơ sở dữ liệu
            // Insert dữ liệu vào bảng giỏ hàng
            db.insert("cart", null, values);
        }
        // Đóng kết nối CSDL
        cursor.close();
        db.close();
    }

    public List<CartItem> getAllCartItems() {
        List<CartItem> cartItemList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn danh sách các mục trong giỏ hàng
        String selectQuery = "SELECT * FROM cart";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Duyệt qua kết quả của truy vấn và thêm các mục vào danh sách
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                int productId = cursor.getInt(cursor.getColumnIndex("productId"));
                String productName = cursor.getString(cursor.getColumnIndex("productName"));
                int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                String image = cursor.getString(cursor.getColumnIndex("image"));

                CartItem cartItem = new CartItem(id, productId, productName, quantity, price, image);
                cartItemList.add(cartItem);
            } while (cursor.moveToNext());
        }

        // Đóng kết nối CSDL và trả về danh sách mục trong giỏ hàng
        cursor.close();
        db.close();
        return cartItemList;
    }

    public void removeFromCart(int itemId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("cart", "id=?", new String[]{String.valueOf(itemId)});
        db.close();
    }

}
