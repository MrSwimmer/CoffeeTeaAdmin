package com.mrswimmer.coffeeteaadmin.presentation.main.activity;

import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mrswimmer.coffeeteaadmin.App;
import com.mrswimmer.coffeeteaadmin.R;
import com.mrswimmer.coffeeteaadmin.data.settings.Screens;
import com.mrswimmer.coffeeteaadmin.di.qualifier.Global;
import com.mrswimmer.coffeeteaadmin.di.qualifier.Local;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityView> {
    public MainActivityPresenter() {
        App.getComponent().inject(this);
    }
    @Inject
    @Local
    Router router;

    @Inject
    @Global
    Router globalRouter;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        router.newRootScreen(Screens.CATALOG_SCREEN);
    }

    public void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

    private void selectDrawerItem(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_add_prod:
                router.replaceScreen(Screens.CHOOSE_PROD);
                break;
            case R.id.nav_new_prod:
                router.replaceScreen(Screens.NEW_PROD);
                break;
            case R.id.nav_new_shop:
                router.replaceScreen(Screens.NEW_SHOP);
                break;
            case R.id.nav_give_prod:
                router.replaceScreen(Screens.GIVE_PROD_SCREEN);
                break;
            case R.id.nav_settings:
                router.replaceScreen(Screens.SETTINGS_SCREEN);
                break;
            default:
        }
        menuItem.setChecked(true);
        getViewState().checkDrawerItem(menuItem);
    }

    public void share() {
        globalRouter.navigateTo(Screens.SHARE);
    }
}
