package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.order.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mrswimmer.coffeeteaadmin.R;

public class ProductsInOrderViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView city;
    TextView adress;
    TextView count;
    TextView cost;
    TextView newCost;
    ImageView delete;
    ImageView image;
    RatingBar ratingBar;
    public ProductsInOrderViewHolder(View v) {
        super(v);
        name = v.findViewById(R.id.item_basket_name);
        image = v.findViewById(R.id.item_basket_image);
        cost = v.findViewById(R.id.item_basket_cost);
        newCost = v.findViewById(R.id.item_basket_cost_sale);
        ratingBar = v.findViewById(R.id.item_basket_rate);
        city = v.findViewById(R.id.item_basket_city);
        adress = v.findViewById(R.id.item_basket_adress);
        count = v.findViewById(R.id.item_basket_count);
        delete = v.findViewById(R.id.item_basket_delete);

    }
}
