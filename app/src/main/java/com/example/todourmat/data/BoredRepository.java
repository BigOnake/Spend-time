package com.example.todourmat.data;

import com.example.todourmat.data.local.IBoredStorage;
import com.example.todourmat.data.remote.BoredApiClient;
import com.example.todourmat.model.BoredAction;

import java.util.List;

public class BoredRepository {
    public BoredAction lastAction = null;
    private IBoredStorage boredStorage;
    private BoredApiClient boredApiClient;

    public BoredRepository(IBoredStorage boredStorage, BoredApiClient boredApiClient) {
        this.boredStorage = boredStorage;
        this.boredApiClient = boredApiClient;
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
    }

    public void getAction(String key, Integer participants, String link, String type, Float minPrice, Float maxPrice,
                          Float minAccessibility, Float maxAccessibility, BoredApiClient.BoredActionCallback callback) {

        boredApiClient.getAction(key, participants, link, type, minPrice, maxPrice,
                minAccessibility, maxAccessibility, new BoredApiClient.BoredActionCallback() {
                    @Override
                    public void onSuccess(BoredAction result) {
                        lastAction = result;
                        callback.onSuccess(result);
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        callback.onFailure(exception);
                    }
                });
    }
}
