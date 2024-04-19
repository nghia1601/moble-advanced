package com.cgm.hello_android_app_k15pm06.service;

import com.cgm.hello_android_app_k15pm06.CartCheckout.CheckoutData;
import com.cgm.hello_android_app_k15pm06.entities.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {

    @GET("products")
    Call<List<Product>>getAllProducts();//http://localhost:8080/hello-web-app/rest/products

    @POST("products/add")
    Call<Void> addProduct(@Body Product product);

    @PUT("products/update/{id}")
    Call<Void> updateProduct(@Path("id") int id, @Body Product updatedProduct);

    @DELETE("products/delete/{id}")
    Call<Void> deleteProduct(@Path("id") int id);


    @GET("products/get/{title}")
    Call<List<Product>> searchProductsByTitle(@Path("title") String title);


    @POST("checkout")
    Call<Void> createCheckout(@Body CheckoutData checkoutData);




}
