package com.example.todourmat.data;

import android.util.Log;

import com.example.todourmat.model.BoredAction;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class BoredApiClient {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.boredapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    BoredApi client = retrofit.create(BoredApi.class);

    public void getAction(String key, Integer participants, String type, Float minPrice, Float maxPrice,
                          Float maxAccessibility, Float minAccessibility, BoredActionCallback callback) {

        Call<BoredAction> call = client.getAction(
                key,
                type,
                minPrice,
                maxPrice,
                participants,
                maxAccessibility,
                minAccessibility
        );

        call.enqueue(new CoreCallback<BoredAction>() {
            @Override
            void onSuccess(BoredAction result) {
                callback.onSuccess(result);
            }

            @Override
            void onFailure(Exception exception) {
                callback.onFailure(exception);
            }
        });
    }

    public interface BoredActionCallback extends BaseCallback<BoredAction> { }

    public interface BaseCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception exception);

    }

    private interface BoredApi {
        @GET("api/activity/")
        Call<BoredAction> getAction(
                @Query("key") String key,
                @Query("type") String type,
                @Query("minprice") Float minPrice,
                @Query("maxprice") Float maxPrice,
                @Query("participants") Integer participants,
                @Query("minaccessibility") Float minAccessibility,
                @Query("maxaccessibility") Float maxAccessibility
        );
    }
}