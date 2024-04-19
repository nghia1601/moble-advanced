package com.cgm.hello_android_app_k15pm06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.cgm.hello_android_app_k15pm06.Cart.CartManager;
import com.cgm.hello_android_app_k15pm06.entities.Product;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_layout);

        // Nhận dữ liệu sản phẩm từ Intent
        Intent intent = getIntent();
        Product product = intent.getParcelableExtra("PRODUCT");

        // Hiển thị dữ liệu sản phẩm lên giao diện
        TextView titleTextView = findViewById(R.id.titleDetail);
        TextView priceTextView = findViewById(R.id.priceDetail);
        TextView descriptionTextView = findViewById(R.id.descriptionDetail);
        TextView categoryTextView = findViewById(R.id.categoryDetail); // Đặt biến categoryTextView

        // Hiển thị thông tin sản phẩm
        titleTextView.setText(product.getTitle());
        priceTextView.setText("Price: " + product.getPrice());
        descriptionTextView.setText("Description: " + product.getDescription());
        categoryTextView.setText("Category: " + product.getCategory()); // Hiển thị category


        // Tải ảnh vào ImageView bằng Picasso
        String baseUrl = "http://192.168.100.8:8080/hello-web-app/img/";
        String imageUrl = baseUrl + product.getImage();
        ImageView imageView = findViewById(R.id.imageDetail);
        Picasso.get().load(imageUrl).into(imageView);

        // Xử lý sự kiện khi bấm vào nút "Add to Cart"
        Button addToCartButton = findViewById(R.id.btnAddToCart);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thêm sản phẩm vào giỏ hàng
                CartManager cartManager = new CartManager(ProductDetail.this);
                cartManager.addToCart(product, 1); // 1 là số lượng, bạn có thể thay đổi tùy thuộc vào yêu cầu
                Toast.makeText(ProductDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
