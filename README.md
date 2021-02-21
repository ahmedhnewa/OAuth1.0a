# oAuth1
a library to add OAuth 1.0  with retrofit

Release :
[![](https://jitpack.io/v/AhmedRiyadh441/oAuth1.svg)](https://jitpack.io/#AhmedRiyadh441/oAuth1)

to add the library to your porject :

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
	        implementation 'com.github.AhmedRiyadh441:oAuth1:release-here'
	}

Usage :
it very easy

import com.ahmedriyadh.oauth1.OAuthInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// if you do not want add the token
            // just leave it empty
            // for the token secret make it null
            
	    
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
