package com.cgm.hello_android_app_k15pm06;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.cgm.hello_android_app_k15pm06.entities.Product;
import com.cgm.hello_android_app_k15pm06.service.ProductService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<Product> adapter;
    private List<Product> productListData;

    private Product selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.productListView);

        // Khởi tạo Retrofit và ProductService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.18.248:8080/hello-web-app/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);

        // Load danh sách sản phẩm
        loadProductList(productService);

        // Chuyển sang trang thêm sản phẩm
        Button buttonAddProduct = findViewById(R.id.buttonAddProduct);
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, crudViewActivity.class);
                startActivity(intent);
            }
        });

        // Chuyển sang trang xóa sản phẩm
        Button buttonDeleteProduct = findViewById(R.id.buttonDeleteProduct);
        buttonDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                startActivity(intent);
            }
        });

        // Chuyển sang trang sửa sản phẩm
        Button buttonEditProduct = findViewById(R.id.buttonEditProduct);
        buttonEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển từ MainActivity sang EditActivity
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        // Load lại danh sách sản phẩm khi nhấn vào nút "Load lại danh sách sản phẩm"
        Button buttonReloadProductList = findViewById(R.id.buttonReloadProductList);
        buttonReloadProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProductList(productService);
            }
        });
    }

    // Phương thức để load lại danh sách sản phẩm từ API
    private void loadProductList(ProductService productService) {
        Call<List<Product>> call = productService.getAllProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productListData = response.body();
                    adapter = new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_list_item_1, productListData);
                    listView.setAdapter(adapter);

                    // Sự kiện click cho mỗi sản phẩm trong danh sách
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            showMenuDialog(position);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Xử lý lỗi khi không thể tải danh sách sản phẩm
                Toast.makeText(MainActivity.this, "Failed to load product list", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức hiển thị AlertDialog với menu
    private void showMenuDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chức Năng");
        builder.setItems(R.array.menu_options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Sửa
                        // Xử lý sửa sản phẩm
                        break;
                    case 1: // Xóa
                        // Xử lý xóa sản phẩm
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
