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

    private EditText edtTitle, edtPrice, edtDescription, edtCategory, edtImage;
    private Button btnUpdate;
    private ProductService productService;
    private Product productToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product_layout);

        // Initialize views
        edtTitle = findViewById(R.id.edtTitle);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        edtCategory = findViewById(R.id.edtCategory);
        edtImage = findViewById(R.id.edtImage);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.25:8080/hello-web-app/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productService = retrofit.create(ProductService.class);

        // Get product data from intent
        productToUpdate = getIntent().getParcelableExtra("PRODUCT");
        if (productToUpdate != null) {
            // Set product data to views
            edtTitle.setText(productToUpdate.getTitle());
            edtPrice.setText(String.valueOf(productToUpdate.getPrice()));
            edtDescription.setText(productToUpdate.getDescription());
            edtCategory.setText(productToUpdate.getCategory());
            edtImage.setText(productToUpdate.getImage());
        }

        // Set click listener for update button
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update product data
                updateProduct();
            }
        });
    }

    private void updateProduct() {
        // Get updated product data from views
        String title = edtTitle.getText().toString().trim();
        double price = Double.parseDouble(edtPrice.getText().toString().trim());
        String description = edtDescription.getText().toString().trim();
        String category = edtCategory.getText().toString().trim();
        String image = edtImage.getText().toString().trim();

        // Update product object
        productToUpdate.setTitle(title);
        productToUpdate.setPrice(price);
        productToUpdate.setDescription(description);
        productToUpdate.setCategory(category);
        productToUpdate.setImage(image);

        // Call API to update product
        Call<Void> call = productService.updateProduct(productToUpdate.getId(), productToUpdate);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Product updated successfully
                    Toast.makeText(EditProductActivity.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                    // Set result code to indicate success
                    setResult(RESULT_OK);
                    // Finish activity
                    finish();
                } else {
                    // Failed to update product
                    Toast.makeText(EditProductActivity.this, "Failed to update product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Error occurred while updating product
                Toast.makeText(EditProductActivity.this, "Failed to update product", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
