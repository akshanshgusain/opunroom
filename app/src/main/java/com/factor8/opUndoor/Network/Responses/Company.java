package com.factor8.opUndoor.Network.Responses;

import java.util.List;

public class Company {
    private String id, name,network, displayPicture, coverPicture, website;
    private List<Company_Stories> company_stories_list;

    public Company(String id, String name, String network, String displayPicture, String coverPicture, String website, List<Company_Stories> company_stories_list) {
        this.id = id;
        this.name = name;
        this.network = network;
        this.displayPicture = displayPicture;
        this.coverPicture = coverPicture;
        this.website = website;
        this.company_stories_list = company_stories_list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getDisplayPicture() {
        return displayPicture;
    }

    public void setDisplayPicture(String displayPicture) {
        this.displayPicture = displayPicture;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Company_Stories> getCompany_stories_list() {
        return company_stories_list;
    }

    public void setCompany_stories_list(List<Company_Stories> company_stories_list) {
        this.company_stories_list = company_stories_list;
    }
}
