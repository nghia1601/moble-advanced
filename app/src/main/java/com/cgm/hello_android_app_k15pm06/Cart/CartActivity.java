package com.cgm.hello_android_app_k15pm06.Cart;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.cgm.hello_android_app_k15pm06.Cart.CartItem;
import com.cgm.hello_android_app_k15pm06.R;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ListView cartListView;
    private List<CartItem> cartItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cartListView);

        // Khởi tạo CartManager
        CartManager cartManager = new CartManager(this);

        // Lấy danh sách các mục trong giỏ hàng từ CSDL
        cartItemList = cartManager.getAllCartItems();

        // Tạo adapter tùy chỉnh và thiết lập cho ListView
        CartListAdapter adapter = new CartListAdapter(this, cartItemList);
        cartListView.setAdapter(adapter);
    }
}

