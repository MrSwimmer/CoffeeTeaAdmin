package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.give_prod;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mrswimmer.coffeeteaadmin.App;
import com.mrswimmer.coffeeteaadmin.data.model.Order;
import com.mrswimmer.coffeeteaadmin.data.settings.Screens;
import com.mrswimmer.coffeeteaadmin.di.qualifier.Local;
import com.mrswimmer.coffeeteaadmin.domain.service.FireService;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class GiveProdFragmentPresenter extends MvpPresenter<GiveProdFragmentView> {

    @Inject
    @Local
    Router router;

    @Inject
    FireService fireService;

    public static Order curOrder;

    public GiveProdFragmentPresenter() {
        App.getComponent().inject(this);
    }

    public void findOrder(String orderId) {

        fireService.getOrderById(orderId, new FireService.OrderCallback() {
            @Override
            public void onSuccess(Order order) {
                if (order.getProducts().size() > 0) {
                    curOrder = order;
                    router.navigateTo(Screens.BASKET_OF_ORDER_SCREEN);
                }
                else {
                    getViewState().showToast("Нет заказа с таким ID");
                }
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showToast("Нет заказа с таким ID");
            }
        });
    }
}