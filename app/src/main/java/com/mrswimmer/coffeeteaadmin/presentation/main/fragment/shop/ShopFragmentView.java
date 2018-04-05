package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.shop;

import com.mrswimmer.coffeeteaadmin.data.model.Shop;
import com.mrswimmer.coffeeteaadmin.presentation.base.BaseView;

import java.util.ArrayList;

interface ShopFragmentView extends BaseView {
    void initAdapter(ArrayList<Shop> shops);
}
