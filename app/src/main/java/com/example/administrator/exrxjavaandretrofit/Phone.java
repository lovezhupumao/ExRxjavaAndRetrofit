package com.example.administrator.exrxjavaandretrofit;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/5/22 0022.
 */
public interface Phone {
    @Headers("Content-Type:application/json")
    @GET("mobile/get")
    Observable<PhoneNumInfo> getHaoMa(@Query("phone") String phone, @Query("key") String key);
}
