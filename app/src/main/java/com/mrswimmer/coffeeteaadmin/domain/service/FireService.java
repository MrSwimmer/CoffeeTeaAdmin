package com.mrswimmer.coffeeteaadmin.domain.service;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kelvinapps.rxfirebase.DataSnapshotMapper;
import com.kelvinapps.rxfirebase.RxFirebaseAuth;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.mrswimmer.coffeeteaadmin.data.model.Availability;
import com.mrswimmer.coffeeteaadmin.data.model.Order;
import com.mrswimmer.coffeeteaadmin.data.model.Product;
import com.mrswimmer.coffeeteaadmin.data.model.ProductInBasket;
import com.mrswimmer.coffeeteaadmin.data.model.Review;
import com.mrswimmer.coffeeteaadmin.data.model.Shop;
import com.mrswimmer.coffeeteaadmin.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FireService {
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private StorageReference storageReference;

    public FireService() {
        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void signIn(String email, String password, AuthCallBack callBack) {
        RxFirebaseAuth.signInWithEmailAndPassword(auth, email, password)
                .map(authResult -> authResult.getUser() != null)
                .take(1)
                .subscribe(callBack::onSuccess, callBack::onError);
    }

    public void signUp(String email, String password, AuthCallBack callBack) {
        RxFirebaseAuth.createUserWithEmailAndPassword(auth, email, password)
                .map(authResult -> authResult.getUser() != null)
                .take(1)
                .subscribe(callBack::onSuccess, callBack::onError);
    }

    public void getID(String email, UserCallBack callback) {
        RxFirebaseDatabase.observeSingleValueEvent(reference.child("users").orderByChild("mail").equalTo(email), User.class)
                .subscribe(callback::onSuccess, callback::onError);
    }

    public boolean checkLogIn() {
        return null != auth.getCurrentUser();
    }

    public void getShops(ShopsCallback callback) {
        RxFirebaseDatabase.observeSingleValueEvent(reference.child("shops"), DataSnapshotMapper.listOf(Shop.class))
                .subscribe(callback::onSuccess, callback::onError);
    }

    public void getProducts(boolean sale, ProductsCallback callback) {
        if (sale)
            RxFirebaseDatabase.observeSingleValueEvent(reference.child("products").orderByChild("newCost").startAt(1), DataSnapshotMapper.listOf(Product.class))
                    .subscribe(callback::onSuccess, callback::onError);
        else
            RxFirebaseDatabase.observeSingleValueEvent(reference.child("products"), DataSnapshotMapper.listOf(Product.class))
                    .subscribe(callback::onSuccess, callback::onError);
    }

    public void getAvals(String prodId, String shopId, AvailabilityCallback callback) {
        RxFirebaseDatabase.observeSingleValueEvent(reference.child("products").child(prodId).child("availabilities").orderByChild("shopId").equalTo(shopId), DataSnapshotMapper.listOf(Availability.class))
                .subscribe(callback::onSuccess, callback::onError);
    }

    public void deleteProd(String prodId, String shopId, int count) {
        Log.i("code", "del");
        getAvals(prodId, shopId, new AvailabilityCallback() {
            @Override
            public void onSuccess(List<Availability> availabilities) {
                Log.i("code", "del from " + availabilities.get(0).getQuantity());
                String id = availabilities.get(0).getId();
                DatabaseReference avail = reference.child("products").child(prodId).child("availabilities").child(id);
                if (availabilities.get(0).getQuantity() - count == 0) {
                    avail.removeValue();
                } else {
                    avail = avail.child("quantity");
                    avail.setValue(availabilities.get(0).getQuantity() - count);
                }

            }

            @Override
            public void onError(Throwable e) {
                Log.i("code", "del from error " + e.getMessage());
            }

        });
    }

    public void signOut() {
        auth.signOut();
    }

    public void addProduct(String prodId, int countProd, String shopId) {
        ProductInBasket productInBasket = new ProductInBasket();
        productInBasket.setProductId(prodId);
        productInBasket.setCount(countProd);
        productInBasket.setShopId(shopId);
        getAllAvails(prodId, new AvailabilityCallback() {
            @Override
            public void onError(Throwable e) {
                Log.i("code", "all avails error " + e.getMessage());
            }

            @Override
            public void onSuccess(List<Availability> availabilities) {
                Log.i("code", "all avails " + availabilities.size());
                boolean exist = false;
                for (int i = 0; i < availabilities.size(); i++) {
                    Availability availability = availabilities.get(i);
                    if(availability.getShopId().equals(shopId)) {
                        exist = true;
                        availabilities.get(i).setQuantity(availability.getQuantity()+countProd);
                        break;
                    }
                }
                if (!exist) {
                    availabilities.add(new Availability(countProd, shopId));
                }
                DatabaseReference avail = reference.child("products").child(prodId).child("availabilities");
                avail.setValue(availabilities);
            }
        });
    }

    public void getAllAvails(String prodId, AvailabilityCallback callback) {
        RxFirebaseDatabase.observeSingleValueEvent(reference.child("products").child(prodId).child("availabilities"), DataSnapshotMapper.listOf(Availability.class))
                .subscribe(callback::onSuccess, callback::onError);
    }

    public void createProduct(Product product) {
        DatabaseReference newProdref = reference.child("products").push();
        product.setId(newProdref.getKey());
        newProdref.setValue(product);
    }

    public void getOrderById(String orderId, OrderCallback callback) {
        RxFirebaseDatabase.observeSingleValueEvent(reference.child("orders").child(orderId), Order.class)
                .subscribe(callback::onSuccess, callback::onError);
    }

    public void delProductFromOrder(Order order) {
        DatabaseReference newOrder = reference.child("orders").child(order.getId());
        newOrder.setValue(order);
    }

    public void delOrder(Order order) {
        DatabaseReference newOrder = reference.child("orders").child(order.getId());
        newOrder.removeValue();
    }

    public void uploadShopImage(String sAdress, Uri selectedImage, UploadImageCallBack callBack) {
        StorageReference shopsImages = storageReference.child("shops/" + sAdress + ".jpg");
        shopsImages.putFile(selectedImage)
                .addOnSuccessListener(callBack::onSuccess)
                .addOnFailureListener(callBack::onError);
    }

    public void createShop(Shop shop) {
        DatabaseReference newShop = reference.child("shops").push();
        shop.setId(newShop.getKey());
        newShop.setValue(shop);
    }

    public void uploadProdImage(String name, Uri selectedImage, UploadImageCallBack callBack) {
        StorageReference shopsImages = storageReference.child("prods/" + name + ".jpg");
        shopsImages.putFile(selectedImage)
                .addOnSuccessListener(callBack::onSuccess)
                .addOnFailureListener(callBack::onError);
    }

    public interface UploadImageCallBack {
        void onSuccess(UploadTask.TaskSnapshot taskSnapshot);

        void onError(Throwable e);
    }

    public interface UserCallBack {
        void onSuccess(User user);

        void onError(Throwable e);
    }

    public interface AuthCallBack {
        void onSuccess(boolean success);

        void onError(Throwable e);
    }

    public interface ProductsCallback {
        void onSuccess(List<Product> products);

        void onError(Throwable e);
    }

    public interface ShopsCallback {
        void onSuccess(List<Shop> shops);

        void onError(Throwable e);
    }

    public interface AvailabilityCallback {
        void onError(Throwable e);

        void onSuccess(List<Availability> availabilities);
    }

    public interface OrderCallback {
        void onSuccess(Order order);

        void onError(Throwable e);
    }
}