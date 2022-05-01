package com.ahmedriyadh.ouath1;

import com.ahmedriyadh.oauth1.OAuthInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;
    public static String BASE_URL = "";

    public static Retrofit getRetrofitInstance(boolean useOAuth1) {
        if (retrofit == null) {

            OAuthInterceptor oauth1WooCommerce = new OAuthInterceptor.Builder()
                    .consumerKey("your_consumer_key_here")
                    .consumerSecret("your_consumer_secret_here")
                    .token("your_token_here")
                    .tokenSecret("your_token_secret_here")
//                    .excludeOAuthToken()
                    .build();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient().newBuilder().connectTimeout(3, TimeUnit.MINUTES).readTimeout(3, TimeUnit.MINUTES);
            builder.addInterceptor(interceptor);

            if (useOAuth1) {
                builder.addInterceptor(oauth1WooCommerce);
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
