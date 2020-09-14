package com.factor8.opUndoor.Network.Responses;

import java.util.ArrayList;
import java.util.List;

public class Friends {
    String id, f_name, l_name, username, email, password, network, picture, profession;
    List<String> storyPictures = new ArrayList<>();

    public Friends(String id, String f_name, String l_name, String username, String email, String password, String network, String picture, ArrayList<String> storypictures, String profession) {
        this.id = id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.network = network;
        this.picture = picture;
        this.storyPictures = storypictures;
        this.profession = profession;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public List<String> getStoryPictures() {
        return storyPictures;
    }

    public void setStoryPictures(List<String> storyPictures) {
        this.storyPictures = storyPictures;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
