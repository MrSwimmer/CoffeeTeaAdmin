package com.mrswimmer.coffeeteaadmin.domain.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.mrswimmer.coffeeteaadmin.data.settings.Screens;
import com.mrswimmer.coffeeteaadmin.presentation.auth.fragment.sign_in.SignInFragment;
import com.mrswimmer.coffeeteaadmin.presentation.auth.fragment.sign_up.SignUpFragment;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.catalog.CatalogFragment;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.give_prod.GiveProdFragment;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.new_prod.NewProdFragment;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.order.OrderFragment;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.settings.SettingsFragment;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.shop.ShopFragment;

import ru.terrakok.cicerone.android.SupportFragmentNavigator;

public class LocalNavigator extends SupportFragmentNavigator {

    int currentContainer;

    public LocalNavigator(FragmentManager fragmentManager, int containerId) {
        super(fragmentManager, containerId);
        currentContainer = containerId;
    }

    @Override
    protected Fragment createFragment(String screenKey, Object data) {
        switch (currentContainer) {
            case Screens.CONTAINER_MAIN:
                return mainFragments(screenKey, data);
            case Screens.CONTAINER_AUTH:
                return authFragments(screenKey);
            default:
                return authFragments(screenKey);
        }
    }

    private Fragment mainFragments(String screenKey, Object data) {
        switch (screenKey) {
            case Screens.CHOOSE_PROD:
                return new CatalogFragment();
            case Screens.CHOOSE_SHOP:
                return new ShopFragment();
            case Screens.NEW_PROD:
                return new NewProdFragment();
            case Screens.GIVE_PROD_SCREEN:
                return new GiveProdFragment();
            case Screens.BASKET_OF_ORDER_SCREEN:
                return new OrderFragment();
            case Screens.SETTINGS_SCREEN:
                return new SettingsFragment();
            default:
                return new CatalogFragment();
        }
    }

    private Fragment authFragments(String screenKey) {
        switch (screenKey) {
            case Screens.SIGN_IN_SCREEN:
                return new SignInFragment();
            case Screens.SIGN_UP_SCREEN:
                return new SignUpFragment();
            default:
                return new SignInFragment();
        }
    }

    @Override
    protected void showSystemMessage(String message) {
    }

    @Override
    protected void exit() {
    }
}


