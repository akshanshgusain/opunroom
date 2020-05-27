package org.akshanshgusain.killmonger2test.Network;

import java.util.List;

public class Groups {
    private String id, grouptitle,admin;
    private List<String> groupstories;
    private String groupImage;

    public Groups(String id, String grouptitle, String admin, List<String> groupstories) {
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

    public List<String> getGroupstories() {
        return groupstories;
    }

    public void setGroupstories(List<String> groupstories) {
        this.groupstories = groupstories;
    }
}
