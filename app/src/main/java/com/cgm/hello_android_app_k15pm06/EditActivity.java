package com.cgm.hello_android_app_k15pm06;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cgm.hello_android_app_k15pm06.entities.Product;
import com.cgm.hello_android_app_k15pm06.service.ProductService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EditActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextPrice;
    private EditText editTextDescription;
    private EditText editTextCategory;
    private EditText editTextImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextCategory = findViewById(R.id.editTextCategory);
        editTextImage = findViewById(R.id.editTextImage);

        EditText editTextId = findViewById(R.id.editTextId);
        Button buttonLoadProduct = findViewById(R.id.buttonLoadProduct);
        Button buttonBackToList = findViewById(R.id.buttonBackToList);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonLoadProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productIdStr = editTextId.getText().toString();
                if (!productIdStr.isEmpty()) {
                    int productId = Integer.parseInt(productIdStr);
                    // Gọi phương thức để load sản phẩm theo ID
                    loadProduct(productId);
                } else {
                    Toast.makeText(EditActivity.this, "Please enter product ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonBackToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Quay lại MainActivity
            }
        });
        // Thêm sự kiện click cho nút buttonUpdate
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin sản phẩm từ các trường EditText
                int productId = Integer.parseInt(editTextId.getText().toString());
                String title = editTextTitle.getText().toString();
                double price = Double.parseDouble(editTextPrice.getText().toString());
                String description = editTextDescription.getText().toString();
                String category = editTextCategory.getText().toString();
                String image = editTextImage.getText().toString();

                // Tạo đối tượng Product mới với thông tin cập nhật
                Product updatedProduct = new Product(productId, title, price, description, category, image);

                // Gọi phương thức để cập nhật sản phẩm
                updateProduct(updatedProduct);
            }
        });


    }
    // Phương thức cập nhật sản phẩm
    private void updateProduct(Product product) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.5:8080/hello-web-app/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);

        Call<Void> call = productService.updateProduct(product.getId(), product);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditActivity.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditActivity.this, "Failed to update product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProduct(int productId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.5:8080/hello-web-app/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);


        // call api bang getProduct
        Call<Product> call = productService.getProduct(productId);

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product product = response.body();
                    if (product != null) {
                        // Hiển thị thông tin sản phẩm
                        displayProductDetails(product);
                    } else {
                        Toast.makeText(EditActivity.this, "Product not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditActivity.this, "Failed to load product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(EditActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProductDetails(Product product) {
        EditText editTextTitle = findViewById(R.id.editTextTitle);
        EditText editTextPrice = findViewById(R.id.editTextPrice);
        EditText editTextDescription = findViewById(R.id.editTextDescription);
        EditText editTextCategory = findViewById(R.id.editTextCategory);
        EditText editTextImage = findViewById(R.id.editTextImage);

        // Hiển thị thông tin sản phẩm trên EditText
        editTextTitle.setText(product.getTitle());
        editTextPrice.setText(String.valueOf(product.getPrice()));
        editTextDescription.setText(product.getDescription());
        editTextCategory.setText(product.getCategory());
        editTextImage.setText(product.getImage());
    }
}
