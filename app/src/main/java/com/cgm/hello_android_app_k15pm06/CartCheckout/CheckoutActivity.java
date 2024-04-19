package com.cgm.hello_android_app_k15pm06.CartCheckout;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cgm.hello_android_app_k15pm06.Cart.CartItem;
import com.cgm.hello_android_app_k15pm06.Cart.CartManager;
import com.cgm.hello_android_app_k15pm06.MainActivity;
import com.cgm.hello_android_app_k15pm06.R;
import com.cgm.hello_android_app_k15pm06.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckoutActivity extends AppCompatActivity {

    private EditText fullNameEditText, phoneNumberEditText, addressEditText;
    private RadioGroup paymentMethodRadioGroup;
    private Button confirmPaymentButton;
    private Retrofit retrofit;



    private CartManager cartManager;
    private static final String BASE_URL = "http://192.168.100.8:8080/hello-web-app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Khởi tạo cartManager
        cartManager = new CartManager(this);


        // khởi tạo Retrofit
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();




        // Ánh xạ các thành phần trong layout
        fullNameEditText = findViewById(R.id.fullNameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        addressEditText = findViewById(R.id.addressEditText);
        paymentMethodRadioGroup = findViewById(R.id.paymentMethodRadioGroup);
        confirmPaymentButton = findViewById(R.id.confirmPaymentButton);

        // Xử lý sự kiện khi nhấn nút Xác nhận thanh toán
        confirmPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmPayment();

            }

        });
    }

    // Phương thức xác nhận thanh toán
    private void confirmPayment() {
        String fullName = fullNameEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        if (fullName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        String paymentMethod = "";

        // Xác định phương thức thanh toán mà người dùng đã chọn
        int selectedId = paymentMethodRadioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.bankTransferRadioButton) {
            paymentMethod = "Chuyển khoản ngân hàng";
        } else if (selectedId == R.id.cashOnDeliveryRadioButton) {
            paymentMethod = "Thanh toán khi nhận hàng";
        }

        if (paymentMethod.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
            return;
        }


        // Lấy danh sách các mục trong giỏ hàng từ cartManager
        List<CartItemInfo> cartItemsInfo = cartManager.getCartItemsInfo();




        // Tạo đối tượng CheckoutData với thông tin từ người dùng và giỏ hàng
        CheckoutData checkoutData = new CheckoutData(fullName, phoneNumber, address, paymentMethod, cartManager.getTotalPrice(), cartItemsInfo);

        // Gửi thông tin thanh toán đến server
        sendCheckoutDataToServer(checkoutData);
    }

    // Phương thức gửi thông tin thanh toán đến server
    private void sendCheckoutDataToServer(CheckoutData checkoutData) {
        ProductService service = retrofit.create(ProductService.class);
        Call<Void> call = service.createCheckout(checkoutData);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CheckoutActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                    cartManager.clearCart();
                    // Khởi tạo Intent để quay về MainActivity
                    Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Đóng CheckoutActivity
                } else {
                    Toast.makeText(CheckoutActivity.this, "Có lỗi xảy ra. Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Có lỗi xảy ra. Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
