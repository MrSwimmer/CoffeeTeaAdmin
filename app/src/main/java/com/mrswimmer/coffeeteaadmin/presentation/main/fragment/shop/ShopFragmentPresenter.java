package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.shop;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mrswimmer.coffeeteaadmin.App;
import com.mrswimmer.coffeeteaadmin.data.model.Shop;
import com.mrswimmer.coffeeteaadmin.domain.service.FireService;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class ShopFragmentPresenter extends MvpPresenter<ShopFragmentView> {

    @Inject
    FireService fireService;

    public ShopFragmentPresenter() {
        App.getComponent().inject(this);
    }

    public void setShopsForRecycler() {
        fireService.getShops(new FireService.ShopsCallback() {
            @Override
            public void onSuccess(List<Shop> shops) {
                ArrayList<Shop> array = (ArrayList<Shop>) shops;
                Log.i("code", "shops " + array.size());
                getViewState().initAdapter(array);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
