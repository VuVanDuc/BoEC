package com.example.boec.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.boec.Interface.ItemClickListener;
import com.example.boec.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtProductName, txtProductPrice, txtProductQuantity;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductName= (TextView)itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = (TextView)itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = (TextView)itemView.findViewById(R.id.cart_product_quantity);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.itemClickListener = listener;
    }
    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);
    }
}
