package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.new_prod;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

import static android.app.Activity.RESULT_OK;

public class NewProdFragment extends BaseFragment implements NewProdFragmentView {
    Uri selectedImage = null;
    @InjectPresenter
    NewProdFragmentPresenter presenter;

    @ProvidePresenter
    public NewProdFragmentPresenter presenter() {
        return new NewProdFragmentPresenter();
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
    @BindView(R.id.new_prod_image)
    ImageView imageView;
    @BindView(R.id.new_prod_choose_photo)
    Button choosePhoto;

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
            ArrayList<String> images = new ArrayList<>();
            Product product = new Product(sNewCost, sWeight, sDecription, sName, sCost, sTypeId, images, sKind);
            presenter.createProduct(product, selectedImage);
        } else {
            showToast("Заполните все поля");
        }

    }

    @OnClick(R.id.new_prod_choose_photo)
    void onChoosePhoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, Settings.GALLERY_REQUEST);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_new_prod;
    }

    boolean checkOnFillingFields() {
        if (sName.equals("") || sCost == 0 || sWeight == 0 || sNewCost == 0 || sDecription.equals("")||selectedImage.equals(null))
            return false;
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case Settings.GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    choosePhoto.setText("Изменить фото");
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageURI(selectedImage);
                }
        }

    }
}
