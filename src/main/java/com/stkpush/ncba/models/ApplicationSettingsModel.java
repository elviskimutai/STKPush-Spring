package com.stkpush.ncba.models;
import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name ="application_settings")
public class ApplicationSettingsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String value;
    private int hashed;
    private Date created_at;
    private int status;

    public ApplicationSettingsModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getHashed() {
        return hashed;
    }

    public void setHashed(int hashed) {
        this.hashed = hashed;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}