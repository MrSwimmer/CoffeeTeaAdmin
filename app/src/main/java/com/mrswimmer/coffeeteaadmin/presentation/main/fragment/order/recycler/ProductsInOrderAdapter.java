package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.order.recycler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrswimmer.coffeeteaadmin.App;
import com.mrswimmer.coffeeteaadmin.R;
import com.mrswimmer.coffeeteaadmin.data.model.Order;
import com.mrswimmer.coffeeteaadmin.data.model.ProductInBasket;
import com.mrswimmer.coffeeteaadmin.data.settings.Screens;
import com.mrswimmer.coffeeteaadmin.data.settings.Settings;
import com.mrswimmer.coffeeteaadmin.di.qualifier.Local;
import com.mrswimmer.coffeeteaadmin.domain.service.FireService;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.give_prod.GiveProdFragment;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.give_prod.GiveProdFragmentPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class ProductsInOrderAdapter extends RecyclerView.Adapter<ProductsInOrderViewHolder> {
    private ArrayList<ProductInBasket> products = new ArrayList<>();

    @Inject
    @Local
    Router localRouter;
    @Inject
    FireService fireService;
    @Inject
    SharedPreferences settings;
    private Context context;

    public ProductsInOrderAdapter(ArrayList<ProductInBasket> products, Context context) {
        this.products = products;
        this.context = context;
        App.getComponent().inject(this);
    }

    @Override
    public ProductsInOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_in_order, parent, false);
        return new ProductsInOrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductsInOrderViewHolder holder, int position) {
        ProductInBasket product = products.get(position);
        holder.ratingBar.setRating(product.getProductRate());
        holder.adress.setText(product.getAdress());
        holder.city.setText(product.getCity());
        holder.name.setText(product.getName());
        holder.count.setText(product.getCount() + "");
        holder.delete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Выдача товара")
                    .setMessage("Выдать товар и убрать его из заказа?")
                    .setCancelable(false)
                    .setPositiveButton("Да",
                            (dialog, id) -> {
                                Order order = GiveProdFragmentPresenter.curOrder;
                                if(order.getProducts().size()==1) {
                                    fireService.delOrder(order);
                                    dialog.cancel();
                                    localRouter.navigateTo(Screens.GIVE_PROD_SCREEN);
                                } else {
                                    for (int i = 0; i < order.getProducts().size(); i++) {
                                        ProductInBasket productInBasket = order.getProducts().get(i);
                                        if(productInBasket.getId().equals(product.getId())) {
                                            order.getProducts().remove(i);
                                            fireService.delProductFromOrder(order);
                                            dialog.cancel();
                                            localRouter.navigateTo(Screens.GIVE_PROD_SCREEN);
                                        }
                                    }
                                }
                            })
                    .setNegativeButton("Нет", (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
