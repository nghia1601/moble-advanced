package com.cgm.hello_android_app_k15pm06.Crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cgm.hello_android_app_k15pm06.R;
import com.cgm.hello_android_app_k15pm06.entities.Product;
import com.cgm.hello_android_app_k15pm06.service.ProductService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProductActivity extends AppCompatActivity {

    // Khai báo các biến
    private EditText edtTitle, edtPrice, edtDescription, edtCategory, edtImage;
    private Button btnUpdate;
    private ProductService productService;
    private Product productToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product_layout);

        // Khởi tạo các view
        edtTitle = findViewById(R.id.edtTitle);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        edtCategory = findViewById(R.id.edtCategory);
        edtImage = findViewById(R.id.edtImage);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.5:8080/hello-web-app/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productService = retrofit.create(ProductService.class);

        // Lấy dữ liệu sản phẩm từ Intent
        productToUpdate = getIntent().getParcelableExtra("PRODUCT");
        if (productToUpdate != null) {
            // Hiển thị dữ liệu sản phẩm trên giao diện
            edtTitle.setText(productToUpdate.getTitle());
            edtPrice.setText(String.valueOf(productToUpdate.getPrice()));
            edtDescription.setText(productToUpdate.getDescription());
            edtCategory.setText(productToUpdate.getCategory());
            edtImage.setText(productToUpdate.getImage());
        }

        // Xử lý sự kiện khi nhấn nút "Cập nhật"
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật thông tin sản phẩm
                updateProduct();
            }
        });
    }

    // Phương thức để cập nhật sản phẩm
    private void updateProduct() {
        // Lấy thông tin sản phẩm được cập nhật từ các view
        String title = edtTitle.getText().toString().trim();
        double price = Double.parseDouble(edtPrice.getText().toString().trim());
        String description = edtDescription.getText().toString().trim();
        String category = edtCategory.getText().toString().trim();
        String image = edtImage.getText().toString().trim();

        // Cập nhật đối tượng sản phẩm
        productToUpdate.setTitle(title);
        productToUpdate.setPrice(price);
        productToUpdate.setDescription(description);
        productToUpdate.setCategory(category);
        productToUpdate.setImage(image);

        // Gọi API để cập nhật sản phẩm
        Call<Void> call = productService.updateProduct(productToUpdate.getId(), productToUpdate);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Cập nhật sản phẩm thành công
                    Toast.makeText(EditProductActivity.this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    // Đặt mã kết quả để chỉ định thành công
                    setResult(RESULT_OK);
                    // Kết thúc hoạt động
                    finish();
                } else {
                    // Không thể cập nhật sản phẩm
                    Toast.makeText(EditProductActivity.this, "Không thể cập nhật sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Lỗi xảy ra khi cập nhật sản phẩm
                Toast.makeText(EditProductActivity.this, "Lỗi khi cập nhật sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
