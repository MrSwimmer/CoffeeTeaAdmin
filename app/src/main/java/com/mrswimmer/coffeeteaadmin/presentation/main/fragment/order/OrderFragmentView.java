package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.order;

import com.mrswimmer.coffeeteaadmin.data.model.ProductInBasket;
import com.mrswimmer.coffeeteaadmin.presentation.base.BaseView;

import java.util.ArrayList;

interface OrderFragmentView extends BaseView {
    void initAdapter(ArrayList<ProductInBasket> products);
}
