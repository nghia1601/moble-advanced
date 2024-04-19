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
        // lay vi tri hien tai cua cartItemList
        final CartItem cartItem = cartItemList.get(position);

        // Ánh xạ các thành phần của layout
        ImageView productImage = view.findViewById(R.id.productImage); // Thêm dòng này để ánh xạ ImageView
        TextView productTitle = view.findViewById(R.id.productTitle);
        TextView productPrice = view.findViewById(R.id.productPrice);
        TextView productQuantity = view.findViewById(R.id.productQuantity);
        // nút xóa sản phẩm khỏi giỏ hàng
        ImageView removeFromCart = view.findViewById(R.id.removeFromCart);
        //nút tăng giảm sản phẩm trong giỏ hàng
        ImageView btPlus = view.findViewById(R.id.btPlus);
        ImageView btMinus = view.findViewById(R.id.btMinus);

        // Hiển thị thông tin sản phẩm trong giỏ hàng
        productTitle.setText(cartItem.getTitle());
        productPrice.setText("Price: $" + cartItem.getPrice());
        productQuantity.setText("Quantity: " + cartItem.getQuantity());

        // Load ảnh sản phẩm bằng Picasso
        String imageUrl = "http://192.168.100.8:8080/hello-web-app/img/" + cartItem.getImage();
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
        // Xử lý sự kiện click để tăng số lượng sản phẩm
        btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tăng số lượng sản phẩm
                int newQuantity = cartItem.getQuantity() + 1;
                cartManager.updateQuantity(cartItem.getId(), newQuantity);
                // Cập nhật lại số lượng hiển thị trong danh sách
                cartItem.setQuantity(newQuantity);
                notifyDataSetChanged();


            }
        });

        // Xử lý sự kiện click để giảm số lượng sản phẩm
        btMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItem.getQuantity() > 1) {
                    // Giảm số lượng sản phẩm
                    int newQuantity = cartItem.getQuantity() - 1;
                    cartManager.updateQuantity(cartItem.getId(), newQuantity);
                    // Cập nhật lại số lượng hiển thị trong danh sách
                    cartItem.setQuantity(newQuantity);
                    notifyDataSetChanged();


                } else {
                    // Nếu số lượng là 1, xóa sản phẩm khỏi giỏ hàng
                    cartManager.removeFromCart(cartItem.getId());
                    // Cập nhật lại danh sách sản phẩm
                    cartItemList.remove(position);
                    notifyDataSetChanged();


                }
            }
        });
        return view;
    }

}
