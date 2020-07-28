package com.example.todourmat.data;

import com.example.todourmat.data.local.IBoredStorage;
import com.example.todourmat.data.remote.BoredApiClient;
import com.example.todourmat.model.BoredAction;

import java.util.List;
import java.util.Objects;

public class BoredRepository {
    private BoredAction mLastAction = null;

    private IBoredStorage boredStorage;
    private BoredApiClient boredApiClient;

    public BoredRepository(IBoredStorage boredStorage, BoredApiClient boredApiClient) {
        this.boredStorage = boredStorage;
        this.boredApiClient = boredApiClient;
    }

    private void fetchAction(String key, Integer participants, String link, String type, Float minPrice,
                             Float maxPrice, Float minAccessibility, Float maxAccessibility,
                             BoredApiClient.BoredActionCallback callback) {
        boredApiClient.getAction(key, participants, link, type, minPrice, maxPrice,
                minAccessibility, maxAccessibility, new BoredApiClient.BoredActionCallback() {
                    @Override
                    public void onSuccess(BoredAction result) {
                        BoredAction savedAction = getBoredAction(result.getKey());
                        result.setSaved(savedAction != null);

                        mLastAction = result;
                        callback.onSuccess(result);
                    }

                    @Override
                    public void onFailure(Exception exception) {callback.onFailure(exception);}
                });
    }

    public void saveBoredAction(BoredAction boredAction) {
        boredStorage.saveBoredAction(boredAction);
    }

    public BoredAction getBoredAction(String key) {
        return boredStorage.getBoredAction(key);
    }

    public List<BoredAction> getAllActions() {
        return boredStorage.getAllActions();
    }

    public void deleteBoredAction(BoredAction boredAction) {
        boredStorage.deleteBoredAction(boredAction);

        if (Objects.equals(boredAction.getKey(), mLastAction.getKey())){
            mLastAction.setSaved(false);
        }
    }

    public void getAction(
            Boolean fromCache,
            String key,
            Integer participants,
            String link,
            String type,
            Float minPrice,
            Float maxPrice,
            Float minAccessibility,
            Float maxAccessibility,
            BoredApiClient.BoredActionCallback callback) {
        if (fromCache) {
            if (mLastAction == null) {
                fetchAction(key, participants, link, type, minPrice, maxPrice, minAccessibility, maxAccessibility, callback);
            } else {
                callback.onSuccess(mLastAction);
            }
        } else {
            fetchAction(key, participants, link, type, minPrice, maxPrice, minAccessibility, maxAccessibility, callback);
        }
    }


}
