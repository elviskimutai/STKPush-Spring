package com.stkpush.ncba.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CallbackMetadata {
    @JsonProperty(value ="Item")
    private List<Item> Item;

    public List<com.stkpush.ncba.models.Item> getItem() {
        return Item;
    }

    public void setItem(List<com.stkpush.ncba.models.Item> item) {
        Item = item;
    }
}
