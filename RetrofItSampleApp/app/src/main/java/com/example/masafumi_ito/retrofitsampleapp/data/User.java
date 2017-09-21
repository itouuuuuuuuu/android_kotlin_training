package com.example.masafumi_ito.retrofitsampleapp.data;

/**
 * Created by Masafumi_Ito on 2017/09/13.
 */

public class User {
    private int id;
    private String uuid;
    private String created_at;
    private String updated_at;
    private String last_access;
    private String name;
    private String payjp_customer_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getLast_access() {
        return last_access;
    }

    public void setLast_access(String last_access) {
        this.last_access = last_access;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayjp_customer_id() {
        return payjp_customer_id;
    }

    public void setPayjp_customer_id(String payjp_customer_id) {
        this.payjp_customer_id = payjp_customer_id;
    }
}
