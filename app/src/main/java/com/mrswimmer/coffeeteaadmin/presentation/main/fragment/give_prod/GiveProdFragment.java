package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.give_prod;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mrswimmer.coffeeteaadmin.App;
import com.mrswimmer.coffeeteaadmin.R;
import com.mrswimmer.coffeeteaadmin.data.settings.Screens;
import com.mrswimmer.coffeeteaadmin.di.qualifier.Global;
import com.mrswimmer.coffeeteaadmin.presentation.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class GiveProdFragment extends BaseFragment implements GiveProdFragmentView {

    @InjectPresenter
    GiveProdFragmentPresenter presenter;

    @Inject
    @Global
    Router globalRouter;


    @ProvidePresenter
    public GiveProdFragmentPresenter presenter() {
        return new GiveProdFragmentPresenter();
    }

    @BindView(R.id.give_prod_order_id)
    EditText id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.give_prod_find)
    void onFindButtonClick() {
        String orderId = id.getText().toString();
        if (!orderId.equals(""))
            presenter.findOrder(orderId);
        else
            showToast("Введите id товара");
    }

    @OnClick(R.id.qrReadButton)
    void onQrReadButtonClick(){
//        globalRouter.navigateTo(Screens.QR_READ_SCREEN);
//        showToast("Скоро прикрутим");
        try {

            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

            startActivityForResult(intent, 0);

        } catch (Exception e) {

            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
            startActivity(marketIntent);

        }


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }
    @Override
    protected int getLayoutID() {
        return R.layout.fragment_give_prod;
    }

}
