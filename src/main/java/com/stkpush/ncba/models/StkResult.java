package com.stkpush.ncba.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StkResult {
    @JsonProperty(value ="Body")
   private Body Body;

    public com.stkpush.ncba.models.Body getBody() {
        return Body;
    }

    public void setBody(com.stkpush.ncba.models.Body body) {
        Body = body;
    }

    @Override
    public String toString() {
        return "StkResult{" +
                "Body=" + Body +
                '}';
    }
}

class Item{
    @JsonProperty(value ="Name")
    private String Name;
    @JsonProperty(value ="Value")
    private String Value;

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}