package com.cgm.hello_android_app_k15pm06.Crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import retrofit2.Call;


import androidx.appcompat.app.AppCompatActivity;

import com.cgm.hello_android_app_k15pm06.MainActivity;
import com.cgm.hello_android_app_k15pm06.ProductAdapter;
import com.cgm.hello_android_app_k15pm06.R;
import com.cgm.hello_android_app_k15pm06.entities.Product;
import com.cgm.hello_android_app_k15pm06.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddProductActivity extends AppCompatActivity {

    private EditText edtTitle, edtPrice, edtDescription, edtCategory, edtImage;
    private Button btnAddProduct;
    private ProductService productService;

    private List<Product> productList = new ArrayList<>();

    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_layout);

        // Khởi tạo Retrofit và ProductService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.9:8080/hello-web-app/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productService = retrofit.create(ProductService.class);

        // Ánh xạ các view từ layout
        edtTitle = findViewById(R.id.edtTitle);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        edtCategory = findViewById(R.id.edtCategory);
        edtImage = findViewById(R.id.edtImage);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        // Xử lý sự kiện khi nhấn nút thêm sản phẩm
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin sản phẩm từ các trường nhập liệu
                String title = edtTitle.getText().toString().trim();
                double price = Double.parseDouble(edtPrice.getText().toString().trim());
                String description = edtDescription.getText().toString().trim();
                String category = edtCategory.getText().toString().trim();
                String image = edtImage.getText().toString().trim();

                // Kiểm tra xem các trường nhập liệu có trống không
                if (title.isEmpty() || description.isEmpty() || category.isEmpty() || image.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Tạo đối tượng sản phẩm mới
                Product newProduct = new Product(0, title, price, description, category, image);

                // Gọi API để thêm sản phẩm mới
                Call<Void> call = productService.addProduct(newProduct);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Thêm sản phẩm thành công
                            Toast.makeText(AddProductActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();



                            // Cập nhật danh sách sản phẩm mới nhất
                            loadProductList();
                        } else {
                            // Thêm sản phẩm thất bại
                            Toast.makeText(AddProductActivity.this, "Failed to add product", Toast.LENGTH_SHORT).show();
                        }
                    }
                    private void loadProductList() {
                        Call<List<Product>> call = productService.getAllProducts();

                        call.enqueue(new Callback<List<Product>>() {
                            @Override
                            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                if (response.isSuccessful()) {
                                    productList.clear(); // Xóa dữ liệu hiện tại
                                    productList.addAll(response.body()); // Thêm dữ liệu mới
                                    // Cập nhật adapter để hiển thị danh sách sản phẩm mới
                                    productAdapter.setData(productList);
                                    productAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(AddProductActivity.this, "Failed to load product list", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Product>> call, Throwable t) {
                                // Xử lý lỗi khi không thể tải danh sách sản phẩm
                                Toast.makeText(AddProductActivity.this, "Failed to load product list", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Xử lý lỗi khi không thể gửi yêu cầu thêm sản phẩm
                        Toast.makeText(AddProductActivity.this, "Failed to add product", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }



}
