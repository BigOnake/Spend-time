package com.example.todourmat.data.local;

import com.example.todourmat.model.BoredAction;

import java.util.List;

public class BoredStorage implements IBoredStorage{

    private BoredDao dao;

    public BoredStorage(BoredDao dao) {
        this.dao = dao;
    }

    public void saveBoredAction(BoredAction boredAction) {
        dao.insert(boredAction);
    }

    public BoredAction getBoredAction(String key) {
        return dao.get(key);
    }

    public List<BoredAction> getAllActions() {
        return dao.getAll();
    }

    public void deleteBoredAction(BoredAction boredAction) {
        dao.delete(boredAction);
    }
}