package com.cgm.hello_android_app_k15pm06;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cgm.hello_android_app_k15pm06.service.ProductService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_layout); // Đảm bảo rằng layout đã được định nghĩa trong delete_layout.xml

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kết thúc hoạt động hiện tại và quay lại MainActivity
                finish();
            }
        });

        Button buttonDelete = findViewById(R.id.buttonDelete);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextDelete = findViewById(R.id.editTextDelete);
                int productId = Integer.parseInt(editTextDelete.getText().toString());

                // Gọi phương thức deleteProduct để xóa sản phẩm theo ID
                deleteProduct(productId);
            }
        });




    }

    private void deleteProduct(int productId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.5:8080/hello-web-app/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);

        Call<Void> call = productService.deleteProduct(productId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    // Xóa sản phẩm thành công
                    Toast.makeText(DeleteActivity.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Xử lý khi không thể xóa sản phẩm
                    Toast.makeText(DeleteActivity.this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý khi gặp lỗi
                Toast.makeText(DeleteActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
