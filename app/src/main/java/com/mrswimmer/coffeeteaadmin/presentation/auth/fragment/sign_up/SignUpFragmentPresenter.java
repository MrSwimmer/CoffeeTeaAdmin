package com.mrswimmer.coffeeteaadmin.presentation.auth.fragment.sign_up;

import android.content.SharedPreferences;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mrswimmer.coffeeteaadmin.App;
import com.mrswimmer.coffeeteaadmin.data.model.User;
import com.mrswimmer.coffeeteaadmin.data.settings.Screens;
import com.mrswimmer.coffeeteaadmin.data.settings.Settings;
import com.mrswimmer.coffeeteaadmin.di.qualifier.Global;
import com.mrswimmer.coffeeteaadmin.domain.service.FireService;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class SignUpFragmentPresenter extends MvpPresenter<SignUpFragmentView> {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Inject
    @Global
    Router globalRouter;

    @Inject
    SharedPreferences settings;

    @Inject
    FireService fireService;

    public SignUpFragmentPresenter() {
        App.getComponent().inject(this);
    }

    void signUp(String email, String pass) {

        fireService.signUp(email, pass, new FireService.AuthCallBack() {
            @Override
            public void onSuccess(boolean success) {
                getViewState().getUserData();
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showToast(e.getMessage());
            }
        });
    }

    void addUser(String username, String firstName, String lastName, String email, int city) {
        DatabaseReference newUser = reference.child("users").push();
        newUser.setValue(new User(firstName, lastName, city, username, email, newUser.getKey()));
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Settings.USER_ID, newUser.getKey());
        editor.apply();
        Log.i("code", "userid " + newUser.getKey());
        globalRouter.navigateTo(Screens.MAIN_ACTIVITY);
    }

}
