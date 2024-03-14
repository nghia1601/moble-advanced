package com.cgm.hello_android_app_k15pm06.service;

import com.cgm.hello_android_app_k15pm06.entities.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProductService {

    @GET("products")
    Call<List<Product>>getAllProducts();//http://localhost:8080/hello-web-app/rest/products

    @POST("products/add")
    Call<Void> addProduct(@Body Product product);

}
