package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.new_shop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mrswimmer.coffeeteaadmin.R;
import com.mrswimmer.coffeeteaadmin.data.model.Shop;
import com.mrswimmer.coffeeteaadmin.data.settings.Settings;
import com.mrswimmer.coffeeteaadmin.presentation.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class NewShopFragment extends BaseFragment implements NewShopFragmentView {

    Uri selectedImage = null;

    @InjectPresenter
    NewShopFragmentPresenter presenter;

    @ProvidePresenter
    public NewShopFragmentPresenter presenter() {
        return new NewShopFragmentPresenter();
    }

    @BindView(R.id.new_shop_adress)
    EditText adress;
    @BindView(R.id.new_shop_begin_work)
    EditText beginWork;
    @BindView(R.id.new_shop_end_work)
    EditText endWork;
    @BindView(R.id.new_shop_city_spinner)
    Spinner spinner;
    @BindView(R.id.new_shop_image)
    ImageView imageView;
    @BindView(R.id.new_shop_choose_photo)
    Button choosePhoto;
    @BindView(R.id.new_shop_create)
    Button create;

    String sAdress;
    int sBeginWork, sEndWork;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SpinnerAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.sign_up_spinner_item, Settings.cities);
        presenter.setKindsSpinner(spinner, adapter);
    }

    @OnClick(R.id.new_shop_choose_photo)
    void onChoosePhoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, Settings.GALLERY_REQUEST);
    }

    @OnClick(R.id.new_shop_create)
    void onCreateShop() {
        sAdress = adress.getText().toString();
        sBeginWork = Integer.parseInt(beginWork.getText().toString());
        sEndWork = Integer.parseInt(endWork.getText().toString());
        if (checkOnFillingFields()) {
            presenter.createShop(sAdress, sBeginWork, sEndWork, spinner.getSelectedItemPosition(), selectedImage);
        } else {
            showToast("Заполните все поля и выберите изображение");
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_new_shop;
    }

    boolean checkOnFillingFields() {
        if (sAdress.equals("") || sBeginWork == 0 || sEndWork == 0 ||selectedImage.equals(null))
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
