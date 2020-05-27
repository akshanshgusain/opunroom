package org.akshanshgusain.killmonger2test.SendPicture;

public class SingleContact {
    String image, userId, username;
    boolean isSelected;
    int type;

    public SingleContact(String image, String userId, String username, int type) {
        this.image = image;
        this.userId = userId;
        this.username = username;
        this.isSelected = false ;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

