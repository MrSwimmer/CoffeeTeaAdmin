package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.catalog.recycler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mrswimmer.coffeeteaadmin.App;
import com.mrswimmer.coffeeteaadmin.R;
import com.mrswimmer.coffeeteaadmin.data.model.Availability;
import com.mrswimmer.coffeeteaadmin.data.model.Product;
import com.mrswimmer.coffeeteaadmin.data.settings.Screens;
import com.mrswimmer.coffeeteaadmin.di.qualifier.Local;
import com.mrswimmer.coffeeteaadmin.domain.service.FireService;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.catalog.CatalogFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsViewHolder> {
    private ArrayList<Product> products = new ArrayList<>();
    private Context context;
    public static String prodId;

    @Inject
    FireService fireService;

    @Inject
    @Local
    Router localRouter;


    public ProductsAdapter(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
        App.getComponent().inject(this);
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductsViewHolder(v);
    }

    public void showDelDialog(int position, String title, String message) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Да",
                        (dialog, id) -> {
                        fireService.deleteTypeProduct(products.get(position).getId());
                        fireService.getProducts(false, new FireService.ProductsCallback() {
                            @Override
                            public void onSuccess(List<Product> products) {
                                localRouter.replaceScreen(Screens.CATALOG_SCREEN);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                        })
        .setNegativeButton("Нет", (dialog, id) -> dialog.cancel());
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {
        Product product = products.get(position);
        holder.name.setText(product.getName());
        holder.type.setText(product.getType());
        holder.kind.setText(product.getKind());
        holder.inStock.setText(product.getInStock());
        holder.ratingBar.setRating(product.getRate());
        holder.cost.setText(product.getCostString());
        Picasso.with(context)
                .load(product.getImages().get(0))
                .into(holder.image);
        holder.itemView.setOnClickListener(v -> {
            prodId = product.getId();
            localRouter.navigateTo(Screens.CHOOSE_SHOP);
        });
        holder.delProdImg.setOnClickListener(v->showDelDialog(position,"Удаление товара", "Удалить товар?"));

        if (product.getNewCost() > 0) {
            holder.newCost.setText(product.getNewCostString());
            holder.cost.setPaintFlags(holder.cost.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        //holder.cost.setPaintFlags(holder.cost.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
