package com.cgm.hello_android_app_k15pm06.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cgm.hello_android_app_k15pm06.CartCheckout.CheckoutActivity;
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

        // Hiển thị tổng tiền của các sản phẩm trong giỏ hàng
        TextView totalPriceTextView = findViewById(R.id.totalPriceTextView);
        double totalPrice = cartManager.getTotalPrice();
        totalPriceTextView.setText("Total Price: $" + totalPrice);


        // nút thanh toán
        Button checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang trang thanh toán
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });



    }




}