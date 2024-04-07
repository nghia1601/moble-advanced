package com.cgm.hello_android_app_k15pm06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cgm.hello_android_app_k15pm06.Crud.AddProductActivity;
import com.cgm.hello_android_app_k15pm06.Crud.EditProductActivity;
import com.cgm.hello_android_app_k15pm06.entities.Product;
import com.cgm.hello_android_app_k15pm06.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ListView productListView;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();

    private ProductService productService;

    // Request code for startActivityForResult
    private static final int EDIT_PRODUCT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productListView = findViewById(R.id.productListView);

        // Initialize Retrofit và ProductService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.25:8080/hello-web-app/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productService = retrofit.create(ProductService.class);

        // Gọi API để lấy danh sách sản phẩm
        loadProductList();

        // Đăng ký menu context cho ListView
        registerForContextMenu(productListView);
    }

    private void loadProductList() {
        Call<List<Product>> call = productService.getAllProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productList = response.body();
                    // Cập nhật dữ liệu vào ListView
                    updateListView();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load product list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Xử lý lỗi khi không thể tải danh sách sản phẩm
                Toast.makeText(MainActivity.this, "Failed to load product list", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateListView() {
        // Tạo adapter mới và gán cho ListView
        productAdapter = new ProductAdapter(productList, this);
        productListView.setAdapter(productAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.product_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        Product selectedProduct = productList.get(position);
        if (item.getItemId() == R.id.add_product) {
            // Chuyển sang màn hình thêm sản phẩm mới
            Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
            startActivityForResult(intent, EDIT_PRODUCT_REQUEST_CODE);
            return true;
        } else if (item.getItemId() == R.id.edit_product) {
            // Chuyển sang màn hình chỉnh sửa sản phẩm và gửi thông tin sản phẩm cần chỉnh sửa
            Intent intent = new Intent(MainActivity.this, EditProductActivity.class);
            intent.putExtra("PRODUCT", selectedProduct);
            startActivityForResult(intent, EDIT_PRODUCT_REQUEST_CODE);
            return true;
        } else if (item.getItemId() == R.id.delete_product) {
            // Gọi phương thức để xóa sản phẩm
            deleteProduct(selectedProduct.getId());
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    // Phương thức để xử lý kết quả trả về từ EditProductActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_PRODUCT_REQUEST_CODE && resultCode == RESULT_OK) {
            // Nếu resultCode là RESULT_OK, ta cập nhật lại danh sách sản phẩm
            loadProductList();
        }
    }

    // Phương thức để xóa sản phẩm
    private void deleteProduct(int productId) {
        Call<Void> call = productService.deleteProduct(productId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Xóa sản phẩm thành công
                    Toast.makeText(MainActivity.this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                    // Load lại danh sách sản phẩm sau khi xóa
                    loadProductList();
                } else {
                    // Xóa sản phẩm thất bại
                    Toast.makeText(MainActivity.this, "Failed to delete product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý lỗi khi không thể gửi yêu cầu xóa sản phẩm
                Toast.makeText(MainActivity.this, "Failed to delete product", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
