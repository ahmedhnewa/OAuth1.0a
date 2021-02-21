package com.ahmedriyadh.ouath1;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("example")
    Call<Example> exampleRequest(
            // your actions
    );
}
