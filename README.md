# oAuth1
A Library To Add OAuth 1.0a Easily With Retrofit

Release :
[![](https://jitpack.io/v/AhmedRiyadh441/oAuth1.svg)](https://jitpack.io/#AhmedRiyadh441/oAuth1)

To Add The Library to your Project :

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  
Step 2. Add the dependency


	dependencies {
	        implementation 'com.github.AhmedRiyadh441:oAuth1:1.0.3'
	}

Usage :
it Very Easy

import com.ahmedriyadh.oauth1.OAuthInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

	    
	    OAuthInterceptor oauth1WooCommerce = new OAuthInterceptor.Builder().consumerKey("your_consumer_key_here")
                    .consumerSecret("your_consumer_secret_here")
                    .token("your_token_here").tokenSecret("your_token_secret_here").build();
		    
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient().newBuilder().connectTimeout(3, TimeUnit.MINUTES).readTimeout(3, TimeUnit.MINUTES);
            builder.addInterceptor(interceptor);

            if (isShouldAddOauth1) {
                builder.addInterceptor(oauth1WooCommerce);
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl("your_base_url_here/")
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
		    
If You Dont Want To Add Token or TokenSecret

		    OAuthInterceptor oauth1WooCommerce = new OAuthInterceptor.Builder().consumerKey(CONSUMER_KEY)
                    .consumerSecret(CONSUMER_SECRET)
                    .token(null)
                    .tokenSecret(null)
                    .isShouldExcludeOAuthToken(true)
                    .build();
		    
if you are using this library it for 

https://wordpress.org/plugins/rest-api-oauth1/
WordPress REST API – OAuth 1.0a Server

You Should Make The isShouldExcludeOAuthToken to false (it false by default)

if you are using it for woocommerce rest api

https://woocommerce.github.io/woocommerce-rest-api-docs/#authentication-over-http
Woocommerce Plugin

You Should Make The isShouldExcludeOAuthToken to true
To Get Vaild oauth_signature (important)

In other cases, it is due to your choice and what is required, always check the instructions
