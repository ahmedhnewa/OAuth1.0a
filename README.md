# oAuth1
A simple library to use OAuth 1.0a Authentication with Retrofit,
This library is based on [WoocommerceAndroidOAuth1](https://github.com/rameshvoltella/WoocommerceAndroidOAuth1)
 
[![](https://jitpack.io/v/AhmedRiyadh441/oAuth1.svg)](https://jitpack.io/#AhmedRiyadh441/oAuth1)

To add the Library to your project:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  
Step 2. Add the dependency to your module


	dependencies {
	        implementation 'com.github.AhmedRiyadh441:oAuth1:1.0.5'
	}

Usage:

import com.ahmedriyadh.oauth1.OAuthInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

	    
	    OAuthInterceptor oauth1 = new OAuthInterceptor.Builder().consumerKey("your_consumer_key_here")
                    .consumerSecret("your_consumer_secret_here")
                    .token("your_token_here").tokenSecret("your_token_secret_here").build();
		    
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient().newBuilder().connectTimeout(3, TimeUnit.MINUTES).readTimeout(3, TimeUnit.MINUTES);
            builder.addInterceptor(interceptor);
	    
                builder.addInterceptor(oauth1);

            retrofit = new Retrofit.Builder()
                    .baseUrl("your_base_url_here/")
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
		    
If you don't want to add token or token secret

		    OAuthInterceptor oauth1WooCommerce = new OAuthInterceptor.Builder().consumerKey(CONSUMER_KEY)
                    .consumerSecret(CONSUMER_SECRET)
                    .token(null)
                    .tokenSecret(null)
                    .isShouldExcludeOAuthToken(true)
                    .build();
		    
If you are using this library it for 
https://wordpress.org/plugins/rest-api-oauth1/
WordPress REST API – OAuth 1.0a Server (plugin in Wordpress)
Then you should pass false to isShouldExcludeOAuthToken() method (it false by default)

If you are using it for woocommerce rest api (for admin user only)
https://woocommerce.github.io/woocommerce-rest-api-docs/#authentication-over-http (Woocommerce is a plugin in Wordpress)

You should pass false to the isShouldExcludeOAuthToken() method (it false by default)
To get vaild oauth_signature (important)

In other cases, it is due to your choice and what is required, always check the instructions
