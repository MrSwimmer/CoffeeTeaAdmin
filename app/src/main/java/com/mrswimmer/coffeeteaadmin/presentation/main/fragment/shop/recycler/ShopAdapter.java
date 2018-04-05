package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.shop.recycler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrswimmer.coffeeteaadmin.App;
import com.mrswimmer.coffeeteaadmin.R;
import com.mrswimmer.coffeeteaadmin.data.model.Product;
import com.mrswimmer.coffeeteaadmin.data.model.ProductInBasket;
import com.mrswimmer.coffeeteaadmin.data.model.Shop;
import com.mrswimmer.coffeeteaadmin.data.settings.Screens;
import com.mrswimmer.coffeeteaadmin.data.settings.Settings;
import com.mrswimmer.coffeeteaadmin.di.qualifier.Local;
import com.mrswimmer.coffeeteaadmin.domain.service.FireService;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.choose_count.ChooseCountDialog;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.shop.recycler.ShopViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder> {
    private final Context context;
    private ArrayList<Shop> shops = new ArrayList<>();
    public static String shopId;
    @Inject
    @Local
    Router localRouter;

    @Inject
    FireService fireService;

    @Inject
    SharedPreferences settings;

    public ShopAdapter(ArrayList<Shop> shops, Context context) {
        this.shops = shops;
        this.context = context;
        App.getComponent().inject(this);
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop, parent, false);
        return new ShopViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        Shop shop = shops.get(position);
        holder.adress.setText(shop.getAdress());
        holder.city.setText(shop.getCity());
        holder.hours.setText(shop.getHours());
        SpannableString content = new SpannableString(shop.getReviewsString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        holder.reviews.setText(content);
        holder.ratingBar.setRating(shop.getRate());
        holder.reviews.setOnClickListener(v -> localRouter.navigateTo(Screens.REVIEWS_SCREEN_FOR_SHOP, shop.getId()));
        holder.itemView.setOnClickListener(v -> {
            Log.i("code", "touch");
            shopId = shop.getId();
            Intent intent = new Intent(context, ChooseCountDialog.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }
}
