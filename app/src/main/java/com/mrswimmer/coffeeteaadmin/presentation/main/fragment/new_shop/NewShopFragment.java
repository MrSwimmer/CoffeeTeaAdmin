package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.new_shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mrswimmer.coffeeteaadmin.R;
import com.mrswimmer.coffeeteaadmin.data.model.Product;
import com.mrswimmer.coffeeteaadmin.data.settings.Settings;
import com.mrswimmer.coffeeteaadmin.presentation.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class NewShopFragment extends BaseFragment implements NewShopFragmentView {

    @InjectPresenter
    NewShopFragmentPresenter presenter;

    @ProvidePresenter
    public NewShopFragmentPresenter presenter() {
        return new NewShopFragmentPresenter();
    }

    @BindView(R.id.new_prod_name)
    TextView name;
    @BindView(R.id.new_prod_cost)
    TextView cost;
    @BindView(R.id.new_prod_new_cost)
    TextView newCost;
    @BindView(R.id.new_prod_weight)
    TextView weight;
    @BindView(R.id.new_prod_decription)
    TextView description;
    @BindView(R.id.radioCoffee)
    RadioButton radioCoffee;
    @BindView(R.id.radioTea)
    RadioButton radioTea;
    @BindView(R.id.new_prod_spinner)
    Spinner spinner;
    @BindView(R.id.new_prod_add_shop_button)
    Button addShop;

    String sName, sDecription;
    int sCost, sNewCost, sWeight, sTypeId, sKind;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SpinnerAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.sign_up_spinner_item, Settings.coffeKinds);
        presenter.setKindsSpinner(spinner, adapter);
    }

    @OnCheckedChanged(R.id.radioCoffee)
    void onRadioChanged() {
        String[] mas;
        if (radioCoffee.isChecked())
            mas = Settings.coffeKinds;
        else
            mas = Settings.teaKinds;
        SpinnerAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.sign_up_spinner_item, mas);
        presenter.setKindsSpinner(spinner, adapter);
    }

    @OnClick(R.id.new_prod_add_shop_button)
    void onAddShopButtonClick() {
        sName = name.getText().toString();
        sCost = Integer.parseInt(cost.getText().toString());
        sNewCost = Integer.parseInt(newCost.getText().toString());
        sWeight = Integer.parseInt(weight.getText().toString());
        sDecription = description.getText().toString();
        sTypeId = radioCoffee.isChecked() ? 0 : 1;
        sKind = spinner.getSelectedItemPosition();
        if (checkOnFillingFields()) {
            String url = "http://provitaminki.com/wp-content/uploads/2015/05/81_20052-300x240.jpg";
            ArrayList<String> images = new ArrayList<>();
            images.add(url);
            Product product = new Product(sNewCost, sWeight, sDecription, sName, sCost, sTypeId, images, sKind);
            presenter.createProduct(product);
        } else {
            showToast("Заполните все поля");
        }

    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_new_prod;
    }

    boolean checkOnFillingFields() {
        if (sName.equals("") || sCost == 0 || sWeight == 0 || sNewCost == 0 || sDecription.equals(""))
            return false;
        return true;
    }
}
