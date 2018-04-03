package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.catalog;

import com.arellomobile.mvp.MvpView;
import com.mrswimmer.coffeeteaadmin.data.model.Product;

import java.util.ArrayList;

interface CatalogFragmentView extends MvpView {
    void initAdapter(ArrayList<Product> products);

    void hideDropButton();

    void showDropButton();

}
