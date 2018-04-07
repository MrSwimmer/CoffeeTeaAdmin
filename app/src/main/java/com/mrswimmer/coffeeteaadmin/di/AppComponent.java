package com.mrswimmer.coffeeteaadmin.di;

import com.mrswimmer.coffeeteaadmin.di.module.FireModule;
import com.mrswimmer.coffeeteaadmin.di.module.NavigatorModule;
import com.mrswimmer.coffeeteaadmin.di.module.SharedPreferencesModule;
import com.mrswimmer.coffeeteaadmin.presentation.auth.activity.AuthActivity;
import com.mrswimmer.coffeeteaadmin.presentation.auth.fragment.sign_in.SignInFragmentPresenter;
import com.mrswimmer.coffeeteaadmin.presentation.auth.fragment.sign_up.SignUpFragmentPresenter;
import com.mrswimmer.coffeeteaadmin.presentation.main.activity.MainActivity;
import com.mrswimmer.coffeeteaadmin.presentation.main.activity.MainActivityPresenter;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.catalog.CatalogFragmentPresenter;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.catalog.recycler.ProductsAdapter;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.choose_count.ChooseCountDialog;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.filter.FilterFragmentPresenter;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.give_prod.GiveProdFragmentPresenter;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.new_prod.NewProdFragmentPresenter;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.settings.SettingsFragmentPresenter;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.shop.ShopFragmentPresenter;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.shop.recycler.ShopAdapter;
import com.mrswimmer.coffeeteaadmin.presentation.splash.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SharedPreferencesModule.class, NavigatorModule.class, FireModule.class})
public interface AppComponent {
    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(AuthActivity authActivity);

    void inject(SignInFragmentPresenter signInFragmentPresenter);

    void inject(SignUpFragmentPresenter signUpFragmentPresenter);

    void inject(MainActivityPresenter mainActivityPresenter);

    void inject(ProductsAdapter productsAdapter);

    void inject(FilterFragmentPresenter filterFragmentPresenter);

    void inject(CatalogFragmentPresenter catalogFragmentPresenter);

    void inject(ShopAdapter shopAdapter);

    void inject(ShopFragmentPresenter shopFragmentPresenter);

    void inject(SettingsFragmentPresenter settingsFragmentPresenter);

    void inject(ChooseCountDialog chooseCountDialog);

    void inject(NewProdFragmentPresenter newProdFragmentPresenter);

    void inject(GiveProdFragmentPresenter giveProdFragmentPresenter);
}
