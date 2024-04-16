package com.cgm.hello_android_app_k15pm06.Cart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import com.cgm.hello_android_app_k15pm06.CartCheckout.CartItemInfo;
import com.cgm.hello_android_app_k15pm06.R;
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
            values.put("title", product.getTitle());
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
                String title = cursor.getString(cursor.getColumnIndex("title"));
                int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                String image = cursor.getString(cursor.getColumnIndex("image"));

                CartItem cartItem = new CartItem(id, productId, title, quantity, price, image);
                cartItemList.add(cartItem);
            } while (cursor.moveToNext());
        }

        // Đóng kết nối CSDL và trả về danh sách mục trong giỏ hàng
        cursor.close();
        db.close();
        return cartItemList;
    }

    // xóa sản phẩm ra khỏi giỏ hàng sau khi click vào thùng rác
    public void removeFromCart(int itemId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("cart", "id=?", new String[]{String.valueOf(itemId)});
        db.close();
    }

    // tăng và giảm số lượng sản phẩm trong giỏ hàng
    public void updateQuantity(int itemId, int newQuantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", newQuantity);
        db.update("cart", values, "id=?", new String[]{String.valueOf(itemId)});
        db.close();
    }


    //tính tổng tiền số sản phẩm có trong giỏ hàng
    public double getTotalPrice() {
        double totalPrice = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn tổng tiền của các mục trong giỏ hàng
        String selectQuery = "SELECT SUM(quantity * price) AS total FROM cart";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Lấy tổng tiền từ kết quả truy vấn
        if (cursor.moveToFirst()) {
            totalPrice = cursor.getDouble(cursor.getColumnIndex("total"));
        }

        // Đóng kết nối CSDL
        cursor.close();
        db.close();

        return totalPrice;
    }


    // Thêm phương thức để xóa tất cả các mục trong giỏ hàng
    public void clearCart() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("cart", null, null); // Xóa tất cả các hàng từ bảng giỏ hàng
        db.close();
    }


    //tra ve danh sach doi tuong cart khi checkout
    public List<CartItemInfo> getCartItemsInfo() {
        List<CartItemInfo> cartItemsInfo = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn danh sách các mục trong giỏ hàng
        String selectQuery = "SELECT title, quantity, price FROM cart";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Duyệt qua kết quả của truy vấn và thêm các mục vào danh sách
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));

                CartItemInfo cartItemInfo = new CartItemInfo(title, quantity, price);
                cartItemsInfo.add(cartItemInfo);
            } while (cursor.moveToNext());
        }

        // Đóng kết nối CSDL và trả về danh sách mục trong giỏ hàng
        cursor.close();
        db.close();
        return cartItemsInfo;
    }





}
