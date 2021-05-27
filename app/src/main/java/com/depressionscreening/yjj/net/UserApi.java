package com.depressionscreening.yjj.net;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApi {
    @FormUrlEncoded
    @POST("register.php")
    Observable<ResponseBody> register(
            @Field("uid") String uid,
            @Field("pwd") String pwd,
            @Field("name") String name,
            @Field("age") int age,
            @Field("sex") int sex
    );

    @FormUrlEncoded
    @POST("login.php")
    Observable<ResponseBody> login(
            @Field("uid") String uid,
            @Field("pwd") String pwd,
            @Field("identity") int identity
    );
}
