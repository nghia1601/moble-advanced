package com.cgm.hello_android_app_k15pm06;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.cgm.hello_android_app_k15pm06.entities.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private List<Product> data;

    private LayoutInflater layoutInflater; // ho tro đỗ dữ liệu từ đa ta vào adapter

    private Context context;



    public ProductAdapter(List<Product> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    // setdata để cập nhật dữ liệu của adapter:
    public void setData(List<Product> newData) {
        this.data = newData;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // getView lay tung doi tượng trong data để đổ vào từng ca text
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);

            convertView = inflater.inflate(R.layout.product_layout, null);
            holder = new ViewHolder();
            holder.titleTextView = convertView.findViewById(R.id.textViewTitle);
            holder.priceTextView = convertView.findViewById(R.id.textViewPrice);
            holder.descriptionTextView = convertView.findViewById(R.id.textViewDescription);
            holder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = data.get(position);
        holder.titleTextView.setText(product.getTitle());
        holder.priceTextView.setText("Price:" +" "+product.getPrice());
        holder.descriptionTextView.setText("Description:" +" "+product.getDescription());


        String baseUrl = "http://192.168.100.8:8080/hello-web-app/img/";
        String imageUrl = baseUrl + product.getImage();

        // Load ảnh từ URL vào ImageView bằng Picasso
        Picasso.get()
                .load(imageUrl)
                .into(holder.imageView);

        // Thêm sự kiện click vào item trong ListView
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang ProductDetailActivity
                Intent intent = new Intent(context, ProductDetail.class);
                // Đưa dữ liệu sản phẩm qua Intent
                intent.putExtra("PRODUCT", product);
                // Chuyển sang ProductDetailActivity
                context.startActivity(intent);
            }
        });




        return convertView;
    }


    static class ViewHolder {
        TextView titleTextView;
        TextView priceTextView;
        TextView descriptionTextView;
        ImageView imageView;
    }



}
