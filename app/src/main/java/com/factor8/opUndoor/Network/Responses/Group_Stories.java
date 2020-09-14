package com.factor8.opUndoor.Network.Responses;

public class Group_Stories {
    private String senderId, story, date,groupId;

    public Group_Stories(String senderId, String story, String date, String groupId) {
        this.senderId = senderId;
        this.story = story;
        this.date = date;
        this.groupId = groupId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
