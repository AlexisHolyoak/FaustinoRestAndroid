package com.alexisholyoak.faustinorest;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FaustinoService {
    @GET("k1")
    Call<List<Faustino>> getAll();
    @DELETE("k3/{id}")
    Call<ResponseBody> deleteById(@Path("id")String id);
    @PUT("k4/{id}")
    Call<ResponseBody> updateById(@Path("id")String id,@Body Faustino faustino);
    @POST("k2")
    Call<ResponseBody> postNew(@Body Faustino faustino);
}
