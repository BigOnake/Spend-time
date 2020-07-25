package com.example.todourmat.data.local;

import com.example.todourmat.model.BoredAction;

import java.util.List;

public interface IBoredStorage {
    void saveBoredAction(BoredAction boredAction);

    BoredAction getBoredAction(String key);

    List<BoredAction> getAllActions();

    void deleteBoredAction(BoredAction boredAction);
}
