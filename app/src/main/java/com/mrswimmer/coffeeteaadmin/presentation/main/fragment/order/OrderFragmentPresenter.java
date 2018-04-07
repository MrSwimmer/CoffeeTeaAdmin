package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.order;

import android.content.SharedPreferences;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mrswimmer.coffeeteaadmin.App;
import com.mrswimmer.coffeeteaadmin.data.model.Order;
import com.mrswimmer.coffeeteaadmin.data.model.ProductInBasket;
import com.mrswimmer.coffeeteaadmin.data.settings.Screens;
import com.mrswimmer.coffeeteaadmin.data.settings.Settings;
import com.mrswimmer.coffeeteaadmin.di.qualifier.Local;
import com.mrswimmer.coffeeteaadmin.domain.service.FireService;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.give_prod.GiveProdFragmentPresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class OrderFragmentPresenter extends MvpPresenter<OrderFragmentView> {
    ArrayList<ProductInBasket> arrayList;
    int sum = 0;
    @Inject
    FireService fireService;
    @Inject
    SharedPreferences settings;
    @Inject
    @Local
    Router localRouter;

    public OrderFragmentPresenter() {
        App.getComponent().inject(this);
    }

    public void setRecycler() {
        fireService.getOrderById(GiveProdFragmentPresenter.curOrder.getId(), new FireService.OrderCallback() {
            @Override
            public void onSuccess(Order order) {
                ArrayList<ProductInBasket> productInBaskets = order.getProducts();
                Log.i("code", "lenght " + productInBaskets.size());
                getViewState().initAdapter(productInBaskets);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("code", "error get order " + e.getMessage());
            }
        });
    }
}
