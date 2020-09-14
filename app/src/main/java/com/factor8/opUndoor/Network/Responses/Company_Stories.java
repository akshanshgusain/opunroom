package com.factor8.opUndoor.Network.Responses;

public class Company_Stories {
    private String id, networkId, userId, picture, date;

    public Company_Stories(String id, String networkId, String userId, String picture, String date) {
        this.id = id;
        this.networkId = networkId;
        this.userId = userId;
        this.picture = picture;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
