package com.factor8.opUndoor.Network;

import java.util.List;

public class Groups {
    private String id, grouptitle,admin;
    private List<Group_Stories> groupstories;
    private String groupImage;

    public Groups(String id, String grouptitle, String admin, List<Group_Stories> groupstories) {
        this.id = id;
        this.grouptitle = grouptitle;
        this.admin = admin;
        this.groupstories = groupstories;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrouptitle() {
        return grouptitle;
    }

    public void setGrouptitle(String grouptitle) {
        this.grouptitle = grouptitle;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public List<Group_Stories> getGroupstories() {
        return groupstories;
    }

    public void setGroupstories(List<Group_Stories> groupstories) {
        this.groupstories = groupstories;
    }
}
