# OAuth 1.0a

A simple library to use OAuth 1.0a Authentication with Retrofit, This library is based
on [WoocommerceAndroidOAuth1](https://github.com/rameshvoltella/WoocommerceAndroidOAuth1)

[![](https://jitpack.io/v/ahmedhnewa/OAuth1.0a.svg)](https://jitpack.io/#ahmedhnewa/OAuth1.0a)

To add the Library to your project:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories, or to settings.gradle in the repositories block:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency to your module (build.gradle app module)

	dependencies {
	        implementation 'com.github.ahmedhnewa:OAuth1.0a:1.0.8'
	}

Usage:

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

If you don't want to add token or token secret, then use excludeOAuthToken() method

		    OAuthInterceptor oauth1WooCommerce = new OAuthInterceptor.Builder().consumerKey(CONSUMER_KEY)
                    .consumerSecret(CONSUMER_SECRET)
                    .token(null)
                    .tokenSecret(null)
                    .excludeOAuthToken()
                    .build();

If you are using this library it for
[WordPress REST API – OAuth 1.0a Server](https://wordpress.org/plugins/rest-api-oauth1/) (plugin in Wordpress)
Then don't use excludeOAuthToken() method in the builder class

If you are using it for [WoocommerceRestApi](https://woocommerce.github.io/woocommerce-rest-api-docs/#authentication-over-http) for authenticate the user over http (for admin user only) (Woocommerce is a
plugin in Wordpress)

You should use excludeOAuthToken() method
To get valid oauth_signature (important)

In other cases, it is due to your choice and what is required, always check the instructions
