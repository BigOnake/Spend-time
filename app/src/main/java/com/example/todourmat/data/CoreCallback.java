package com.example.todourmat.data;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract public class CoreCallback<T> implements Callback<T> {
    private CoreCallback<T> callback;

    abstract void onSuccess(T result);
    abstract void onFailure(Exception exception);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                callback.onSuccess(response.body());
                Log.d("ololo", response.body().toString());
            } else {
                callback.onFailure(new Exception("Body is empty"));
            }
        } else {
            callback.onFailure(new Exception("Response code " + response.code()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        callback.onFailure(new Exception(t));
    }

}
