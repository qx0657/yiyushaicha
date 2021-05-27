package com.depressionscreening.yjj.model;

import com.depressionscreening.yjj.net.UserApi;
import com.depressionscreening.yjj.net.helper.ResponseBodyObserver;
import com.depressionscreening.yjj.net.manager.ApiServiceManager;

import org.json.JSONObject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserModel {
    public interface OnRegisterListener{
        /*
        注册成功回调 自行传参
         */
        void registerSuccess();
        void registerError(String e);
    }

    public enum Sex{
        Male(1), Female(0), UNKNOWN(-1);
        final int value;

        Sex(int value) {
            this.value = value;
        }
    }

    public void register(String uid, String pwd, String name, Sex sex, int age, OnRegisterListener listener){
        ApiServiceManager.getInstance().create(UserApi.class)
                .register(uid, pwd, name, age, sex.value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseBodyObserver() {
                    @Override
                    protected void onNext(JSONObject jsonObject) throws Exception {
                        // ……
                        listener.registerSuccess();
                    }

                    @Override
                    protected void onError(String e) {
                        listener.registerError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    enum IdentityType{
        Doctor(1), Patient(2);

        final int value;

        IdentityType(int value) {
            this.value = value;
        }
    }

    public void register(String uid, String pwd, IdentityType identityType, OnRegisterListener listener){
        ApiServiceManager.getInstance().create(UserApi.class)
                .login("123", "345", identityType.value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseBodyObserver() {
                    @Override
                    protected void onNext(JSONObject jsonObject) throws Exception {
                        // ……
                        listener.registerSuccess();
                    }

                    @Override
                    protected void onError(String e) {
                        listener.registerError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
