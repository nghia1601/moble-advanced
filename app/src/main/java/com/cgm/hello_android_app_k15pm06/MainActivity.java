package com.cgm.hello_android_app_k15pm06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cgm.hello_android_app_k15pm06.entities.Product;
import com.cgm.hello_android_app_k15pm06.service.ProductService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView listView = findViewById(R.id.productListView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.17.1:8080/hello-web-app/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);

        Call<List<Product>> call = productService.getAllProducts();//Call<List<Product>>

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    List<Product> productListData = response.body();
                    ArrayAdapter<Product> adapter =
                            new ArrayAdapter<Product>
                                    (MainActivity.this,
                                            android.R.layout.simple_list_item_1, productListData);
                    listView.setAdapter(adapter);
                }
            }


//        private void addProduct(Product product) {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl("http://192.168.1.25:8080/hello-web-app/rest/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            ProductService productService = retrofit.create(ProductService.class);
//
//            Call<Void> call = productService.addProduct(product);
//
//            call.enqueue(new Callback<Void>() {
//                @Override
//                public void onResponse(Call<Void> call, Response<Void> response) {
//                    if(response.isSuccessful()){
//                        // Đã thêm sản phẩm thành công
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Void> call, Throwable t) {
//                    // Xử lý khi gặp lỗi
//                }
//            });
//        }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

        // chuyen sang trang them san pham
        Button buttonAddProduct = findViewById(R.id.buttonAddProduct);
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, crudViewActivity.class);
                startActivity(intent);
            }
        });





    }
}