package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.catalog;

import android.content.SharedPreferences;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mrswimmer.coffeeteaadmin.App;
import com.mrswimmer.coffeeteaadmin.data.model.Product;
import com.mrswimmer.coffeeteaadmin.data.settings.Screens;
import com.mrswimmer.coffeeteaadmin.data.settings.Settings;
import com.mrswimmer.coffeeteaadmin.di.qualifier.Local;
import com.mrswimmer.coffeeteaadmin.domain.service.FilterService;
import com.mrswimmer.coffeeteaadmin.domain.service.FireService;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.filter.FilterFragmentPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class CatalogFragmentPresenter extends MvpPresenter<CatalogFragmentView> {

    @Inject
    @Local
    Router router;
    @Inject
    FireService fireService;
    @Inject
    FilterService filterService;
    @Inject
    SharedPreferences settings;

    public CatalogFragmentPresenter() {
        App.getComponent().inject(this);
    }

    public void gotoFilters() {
        router.navigateTo(Screens.FILTERS_SCREEN);
    }

    public void setProductsForRecycler(boolean sale) {
        Log.i("code", "sort " + settings.getBoolean(Settings.SORT, false));
        if (sale) {
            setProductsWithoutFilters(true);
        } else if (settings.getBoolean(Settings.SORT, false)) {
            getViewState().initAdapter(FilterFragmentPresenter.readyList);
            getViewState().showDropButton();
        } else {
            setProductsWithoutFilters(false);
        }
    }

    public void setProductsWithoutFilters(boolean sale) {
        ArrayList<Product> productsForRecycler = new ArrayList<>();
        //productsForRecycler.clear();
        fireService.getProducts(sale, new FireService.ProductsCallback() {
            @Override
            public void onSuccess(List<Product> products) {
                Log.i("code", "set " + products.size());

                for (int i = 0; i < products.size(); i++) {
                    productsForRecycler.add(products.get(i));
                }
                getViewState().initAdapter(productsForRecycler);
                Log.i("code", "setProd " + productsForRecycler.size());
            }

            @Override
            public void onError(Throwable e) {
                Log.i("code", "error get prod " + e);
            }
        });
        getViewState().hideDropButton();
    }

    public void dropFilters() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Settings.SORT, false);
        editor.apply();
        setProductsWithoutFilters(false);
    }
}
