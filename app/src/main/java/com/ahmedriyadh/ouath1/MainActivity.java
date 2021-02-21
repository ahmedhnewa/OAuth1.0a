package com.ahmedriyadh.ouath1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ahmedriyadh.oauth1.utils.ParameterList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiClient.getApiInterface(true).exampleRequest()
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Example example = response.body();
                            String string = example.getExample();
                            // etc...
                        } else {
                            switch (response.code()) {
                                case 404:
                                    Log.d(TAG, "onResponse: " + "404");
                                    break;
                                case 401:
                                    Log.d(TAG, "onResponse: " + "401");
                                    break;
                                    // etc....
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        // etc
                    }
                });
    }
}