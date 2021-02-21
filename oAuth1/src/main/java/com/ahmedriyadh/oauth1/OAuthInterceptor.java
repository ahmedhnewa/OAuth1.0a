package com.ahmedriyadh.oauth1;

import android.util.Log;


import com.ahmedriyadh.oauth1.utils.TimestampServiceImpl;
import com.ahmedriyadh.oauth1.utils.HMACSha1SignatureService;
import com.ahmedriyadh.oauth1.utils.ParameterList;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OAuthInterceptor implements Interceptor {
    private static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    private static final String OAUTH_NONCE = "oauth_nonce";
    private static final String OAUTH_SIGNATURE = "oauth_signature";
    private static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
    private static final String OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1";
    private static final String OAUTH_TIMESTAMP = "oauth_timestamp";
    private static final String OAUTH_VERSION = "oauth_version";
    private static final String OAUTH_TOKEN = "oauth_token";
    private static final String OAUTH_VERSION_VALUE = "1.0";

    private final String consumerKey;
    private final String consumerSecret;
    private final String token;
    private final String tokenSecret;

    private OAuthInterceptor(String consumerKey, String consumerSecret, String token, String tokenSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        final String nonce = new TimestampServiceImpl().getNonce();
        final String timestamp = new TimestampServiceImpl().getTimestampInSeconds();

        String dynamicStructureUrl = original.url().scheme() + "://" + original.url().host() + original.url().encodedPath();
        String firstBaseString = original.method() + "&" + urlEncoded(dynamicStructureUrl);
        String generatedBaseString = "";

        if (original.url().encodedQuery() != null) {
            generatedBaseString = original.url().encodedQuery() + "&oauth_consumer_key=" + consumerKey + "&oauth_nonce=" + nonce +
                    "&oauth_token=" + token + "&oauth_signature_method=HMAC-SHA1&oauth_timestamp=" + timestamp + "&oauth_version=1.0";
        } else {
            generatedBaseString = "oauth_consumer_key=" + consumerKey + "&oauth_nonce=" + nonce +
                    "&oauth_token=" + token + "&oauth_signature_method=HMAC-SHA1&oauth_timestamp=" + timestamp + "&oauth_version=1.0";
        }

        ParameterList result = new ParameterList();
        result.addQueryString(generatedBaseString);
        generatedBaseString = result.sort().asOauthBaseString();

        String secoundBaseString = "&" + generatedBaseString;
        if (firstBaseString.contains("%3F")) {
            secoundBaseString = "%26" + urlEncoded(generatedBaseString);
        }
        String baseString = firstBaseString + secoundBaseString;

        String signature = new HMACSha1SignatureService().getSignature(baseString, consumerSecret, tokenSecret);
        Log.d("Signature", signature);

        HttpUrl.Builder builder = originalHttpUrl.newBuilder()
                .addQueryParameter(OAUTH_SIGNATURE_METHOD, OAUTH_SIGNATURE_METHOD_VALUE)
                .addQueryParameter(OAUTH_CONSUMER_KEY, consumerKey)
                .addQueryParameter(OAUTH_VERSION, OAUTH_VERSION_VALUE)
                .addQueryParameter(OAUTH_TIMESTAMP, timestamp)
                .addQueryParameter(OAUTH_NONCE, nonce)
                .addQueryParameter(OAUTH_SIGNATURE, signature);

        if (token != null && !token.isEmpty()) {
            builder.addQueryParameter(OAUTH_TOKEN, token);
        }

        Request.Builder requestBuilder = original.newBuilder()
                .url(builder.build());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }


    public static final class Builder {
        private String consumerKey;
        private String consumerSecret;
        private String token;
        private String tokenSecret;
        private int type;

        public Builder consumerKey(String consumerKey) {
            if (consumerKey == null) throw new NullPointerException("consumerKey = null");
            this.consumerKey = consumerKey;
            return this;
        }

        public Builder consumerSecret(String consumerSecret) {
            if (consumerSecret == null) throw new NullPointerException("consumerSecret = null");
            this.consumerSecret = consumerSecret;
            return this;
        }

        public Builder token(String token) {
            if (token == null) throw new NullPointerException("token = null");
            this.token = token;
            return this;
        }

        public Builder tokenSecret(String tokenSecret) {
            //throw new NullPointerException("tokenSecret = null");
            if (tokenSecret != null) {
                this.tokenSecret = tokenSecret;
            }
            return this;
        }

        public OAuthInterceptor build() {

            if (consumerKey == null) throw new IllegalStateException("consumerKey not set");
            if (consumerSecret == null) throw new IllegalStateException("consumerSecret not set");
            if (token == null) throw new IllegalStateException("token not set");
            if (tokenSecret == null) throw new IllegalStateException("tokenSecret not set");

            return new OAuthInterceptor(consumerKey, consumerSecret, token, tokenSecret);
        }
    }

    public String urlEncoded(String url) {
        String encodedurl = "";
        try {
            encodedurl = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedurl;
    }
}