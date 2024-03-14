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

public class crudViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crud_view);

        Button buttonBackToList = findViewById(R.id.buttonBackToList);
        buttonBackToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextId = findViewById(R.id.editTextId);
                EditText editTextName = findViewById(R.id.editTextName);
                EditText editTextPrice = findViewById(R.id.editTextPrice);
                EditText editTextImage = findViewById(R.id.editTextImage);

                int id = Integer.parseInt(editTextId.getText().toString());
                String name = editTextName.getText().toString();
                double price = Double.parseDouble(editTextPrice.getText().toString());
                String image = editTextImage.getText().toString();

                // Tạo đối tượng Product từ dữ liệu nhập vào
                Product newProduct = new Product(id, name, price, image);


                // Gọi phương thức addProduct để thêm sản phẩm mới
                addProduct(newProduct);
            }
        });
    }

    private void addProduct(Product product) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.17.1:8080/hello-web-app/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);

        Call<Void> call = productService.addProduct(product);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    // Đã thêm sản phẩm thành công
                    Toast.makeText(crudViewActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Xử lý khi không thêm được sản phẩm
                    Toast.makeText(crudViewActivity.this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý khi gặp lỗi
                Toast.makeText(crudViewActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
