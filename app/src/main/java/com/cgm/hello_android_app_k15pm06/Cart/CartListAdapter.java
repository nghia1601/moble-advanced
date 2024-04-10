package com.cgm.hello_android_app_k15pm06.Cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cgm.hello_android_app_k15pm06.Cart.CartItem;
import com.cgm.hello_android_app_k15pm06.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartListAdapter extends ArrayAdapter<CartItem> {
    private Context context;
    private List<CartItem> cartItemList;
    private CartManager cartManager;

    public CartListAdapter(Context context, List<CartItem> cartItemList) {
        super(context, 0, cartItemList);
        this.context = context;
        this.cartItemList = cartItemList;

        // Khởi tạo CartManager
        this.cartManager = new CartManager(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cart_list_item, parent, false);
        }

        final CartItem cartItem = cartItemList.get(position);

        // Ánh xạ các thành phần của layout
        ImageView productImage = view.findViewById(R.id.productImage); // Thêm dòng này để ánh xạ ImageView
        TextView productName = view.findViewById(R.id.productName);
        TextView productPrice = view.findViewById(R.id.productPrice);
        TextView productQuantity = view.findViewById(R.id.productQuantity);
        ImageView removeFromCart = view.findViewById(R.id.removeFromCart);

        // Hiển thị thông tin sản phẩm trong giỏ hàng
        productName.setText(cartItem.getProductName());
        productPrice.setText("Price: $" + cartItem.getPrice());
        productQuantity.setText("Quantity: " + cartItem.getQuantity());

        // Load ảnh sản phẩm bằng Picasso
        String imageUrl = "http://192.168.100.3:8080/hello-web-app/img/" + cartItem.getImage();
        Picasso.get().load(imageUrl).into(productImage);

        // Xử lý sự kiện click để xóa sản phẩm khỏi giỏ hàng
        removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa sản phẩm khỏi giỏ hàng khi bấm vào hình hoặc TextView
                cartManager.removeFromCart(cartItem.getId());
                // Cập nhật lại danh sách sản phẩm
                cartItemList.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }

}
