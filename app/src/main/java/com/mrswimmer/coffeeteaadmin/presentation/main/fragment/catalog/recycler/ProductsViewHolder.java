package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.catalog.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mrswimmer.coffeeteaadmin.R;

public class ProductsViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView type;
    TextView kind;
    TextView weight;
    TextView inStock;
    ImageView image;
    ImageView delProdImg;
    TextView cost;
    TextView newCost;
    RatingBar ratingBar;
    public ProductsViewHolder(View v) {
        super(v);
        name = v.findViewById(R.id.item_prod_name);
        type = v.findViewById(R.id.item_prod_type);
        kind = v.findViewById(R.id.item_prod_kind);
        weight = v.findViewById(R.id.item_prod_weight);
        inStock = v.findViewById(R.id.item_prod_in_stock);
        image = v.findViewById(R.id.item_prod_image);
        cost = v.findViewById(R.id.item_prod_cost);
        ratingBar = v.findViewById(R.id.item_prod_rate);
        newCost = v.findViewById(R.id.item_prod_cost_sale);
        delProdImg = v.findViewById(R.id.delProdImg);
    }
}
