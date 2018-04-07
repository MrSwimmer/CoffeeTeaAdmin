package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.give_prod;

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
import com.mrswimmer.coffeeteaadmin.R;
import com.mrswimmer.coffeeteaadmin.presentation.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class GiveProdFragment extends BaseFragment implements GiveProdFragmentView {

    @InjectPresenter
    GiveProdFragmentPresenter presenter;

    @ProvidePresenter
    public GiveProdFragmentPresenter presenter() {
        return new GiveProdFragmentPresenter();
    }

    @BindView(R.id.give_prod_order_id)
    EditText id;

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

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_give_prod;
    }

}
