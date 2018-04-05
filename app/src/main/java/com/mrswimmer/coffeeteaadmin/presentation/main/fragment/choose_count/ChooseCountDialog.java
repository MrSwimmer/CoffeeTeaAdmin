package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.choose_count;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mrswimmer.coffeeteaadmin.App;
import com.mrswimmer.coffeeteaadmin.R;
import com.mrswimmer.coffeeteaadmin.data.settings.Screens;
import com.mrswimmer.coffeeteaadmin.di.qualifier.Local;
import com.mrswimmer.coffeeteaadmin.domain.service.FireService;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.catalog.CatalogFragmentPresenter;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.catalog.recycler.ProductsAdapter;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.shop.recycler.ShopAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

public class ChooseCountDialog extends AppCompatActivity {

    public static int count;
    public static boolean nextPressed;
    @BindView(R.id.dialog_choose_choose_count)
    TextView countText;
    @Inject
    @Local
    Router router;
    @Inject
    FireService fireService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        setContentView(R.layout.dialog_choose_count);
        count = 1;
        nextPressed = false;
        ButterKnife.bind(this);
    }

    @OnClick(R.id.dialog_choose_up)
    void onUpClick() {
        count++;
        countText.setText(count + "");
    }

    @OnClick(R.id.dialog_choose_down)
    void onDownClick() {
        if (count > 1) {
            count--;
            countText.setText(count + "");
        }
    }

    @OnClick(R.id.dialog_choose_next)
    void onNextClick() {
        nextPressed = true;
        String prodId = ProductsAdapter.prodId;
        int countProd = count;
        String shopId = ShopAdapter.shopId;
        fireService.addProduct(prodId, countProd, shopId);
        router.replaceScreen(Screens.ADD_PROD);
        finish();
    }

    @OnClick(R.id.dialog_choose_back)
    void onBackClick() {
        finish();
    }

}
