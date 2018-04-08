package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.new_shop;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mrswimmer.coffeeteaadmin.App;
import com.mrswimmer.coffeeteaadmin.data.model.Product;
import com.mrswimmer.coffeeteaadmin.data.settings.Screens;
import com.mrswimmer.coffeeteaadmin.di.qualifier.Local;
import com.mrswimmer.coffeeteaadmin.domain.service.FilterService;
import com.mrswimmer.coffeeteaadmin.domain.service.FireService;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class NewShopFragmentPresenter extends MvpPresenter<NewShopFragmentView> {

    @Inject
    @Local
    Router router;

    @Inject
    FireService fireService;

    @Inject
    FilterService filterService;

    @Inject
    SharedPreferences settings;

    public NewShopFragmentPresenter() {
        App.getComponent().inject(this);
    }

    public void setKindsSpinner(Spinner spinner, SpinnerAdapter adapter) {
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    public void createProduct(Product product) {
        fireService.createProduct(product);
        getViewState().showDialog("Готово!", "Товар создан, теперь вы можете добавить его наличие в магазинах");
        router.replaceScreen(Screens.ADD_PROD);
    }
}