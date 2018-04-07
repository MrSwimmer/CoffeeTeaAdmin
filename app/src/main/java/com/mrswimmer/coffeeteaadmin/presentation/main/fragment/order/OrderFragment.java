package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mrswimmer.coffeeteaadmin.R;
import com.mrswimmer.coffeeteaadmin.data.model.ProductInBasket;
import com.mrswimmer.coffeeteaadmin.presentation.base.BaseFragment;
import com.mrswimmer.coffeeteaadmin.presentation.main.fragment.order.recycler.ProductsInOrderAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderFragment extends BaseFragment implements OrderFragmentView {
    @InjectPresenter
    OrderFragmentPresenter presenter;

    @BindView(R.id.order_recycler)
    RecyclerView recyclerView;

    @ProvidePresenter
    public OrderFragmentPresenter presenter() {
        return new OrderFragmentPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        presenter.setRecycler();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_order;
    }

    @Override
    public void initAdapter(ArrayList<ProductInBasket> products) {
        recyclerView.setAdapter(new ProductsInOrderAdapter(products, getActivity()));
    }
}
