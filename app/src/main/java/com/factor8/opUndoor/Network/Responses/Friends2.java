package com.factor8.opUndoor.Network.Responses;

import java.util.List;

public class Friends2 {


    /**
     * status : 1
     * friends : [{"userid":"17","friendid":"18","id":"17","f_name":"Ashwani","l_name":"K","username":"Ashwani","email":"ashwanikv009@gmail.com","password":"bdb8d54d7ebec7d013bf02b336093dde","network":"Opundoor","picture":"ProfilePicture_1596548284.jpeg","profession":"Computers and Technology","experience":"1-2 Years","current_company":"Don't Want to share","privacy":"2","coverpic":"pexels-marc-mueller-380768-min.jpg\t","remember_token":null,"is_verified":"1","created_at":"2020-07-16 06:13:22","updated_at":"2020-08-04 13:38:04","storypicture":[{"id":"1","userid":"17","storypicture":"StoryPicture_1597158217.jpeg","duaration":"24","type":"1","created_at":"2020-08-11 15:03:37","updated_at":"2020-08-11 15:03:37","status":"1"}]},{"userid":"18","friendid":"20","id":"20","f_name":"Jhanvi","l_name":"Rawat","username":"jrawat","email":"akshanshssj5@gmail.com","password":"fcea920f7412b5da7be0cf42b8c93759","network":"novostack","picture":"ProfilePicture_1597166387.jpeg","profession":"Architecture and Civil Engineering","experience":"5-10 Years","current_company":"Don't Want to share","privacy":"2","coverpic":"CoverPicture_1597166387.jpeg","remember_token":null,"is_verified":"1","created_at":"2020-07-17 11:22:05","updated_at":"2020-08-11 17:19:47","storypicture":[{"id":"6","userid":"20","storypicture":"StoryPicture_1597414860.jpeg","duaration":"1","type":"1","created_at":"2020-08-14 14:21:00","updated_at":"2020-08-14 14:21:00","status":"1"}]}]
     * groups : [{"id":"4","groupadminid":"18","grouptitle":"APP DEV. TEAM","groupuserid":"17,18","created_at":"2020-07-17 07:46:42","updated_at":"2020-07-17 07:46:42"},{"id":"5","groupadminid":"23","grouptitle":"PX-SCRUM","groupuserid":"18,22,23,25,26,28","created_at":"2020-07-17 12:44:06","updated_at":"2020-07-17 12:44:06"},{"id":"7","groupadminid":"24","grouptitle":"APP UI/UX","groupuserid":"17,18,24,25,26,27,28","created_at":"2020-07-17 12:49:27","updated_at":"2020-07-17 12:49:27"},{"id":"8","groupadminid":"25","grouptitle":"X PICKNIC","groupuserid":"17,18,25,27","created_at":"2020-07-17 13:04:54","updated_at":"2020-07-17 13:04:54"},{"id":"10","groupadminid":"30","grouptitle":"COFOUNDER","groupuserid":"18,23,43","created_at":"2020-07-23 11:54:04","updated_at":"2020-08-08 04:07:30"},{"id":"12","groupadminid":"18","grouptitle":"NOVANS","groupuserid":"17,33,34,40,41,42,43,44,45,46,47,48,49,50,30,32","created_at":"2020-08-04 06:37:28","updated_at":"2020-08-08 10:47:08"}]
     * company : [{"id":"3","name":"OpunDoor","network":"OpunDoor","display_picture":"Frame1.png","profile_picture":"coverCompany.jpg","website":"opundoor.com","created_at":null,"updated_at":null}]
     */

    private int status;
    private List<FriendsBean> friends;
    private List<GroupsBean> groups;
    private List<CompanyBean> company;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<FriendsBean> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendsBean> friends) {
        this.friends = friends;
    }

    public List<GroupsBean> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupsBean> groups) {
        this.groups = groups;
    }

    public List<CompanyBean> getCompany() {
        return company;
    }

    public void setCompany(List<CompanyBean> company) {
        this.company = company;
    }

    public static class FriendsBean {
        /**
         * userid : 17
         * friendid : 18
         * id : 17
         * f_name : Ashwani
         * l_name : K
         * username : Ashwani
         * email : ashwanikv009@gmail.com
         * password : bdb8d54d7ebec7d013bf02b336093dde
         * network : Opundoor
         * picture : ProfilePicture_1596548284.jpeg
         * profession : Computers and Technology
         * experience : 1-2 Years
         * current_company : Don't Want to share
         * privacy : 2
         * coverpic : pexels-marc-mueller-380768-min.jpg
         * remember_token : null
         * is_verified : 1
         * created_at : 2020-07-16 06:13:22
         * updated_at : 2020-08-04 13:38:04
         * storypicture : [{"id":"1","userid":"17","storypicture":"StoryPicture_1597158217.jpeg","duaration":"24","type":"1","created_at":"2020-08-11 15:03:37","updated_at":"2020-08-11 15:03:37","status":"1"}]
         */

        private String userid;
        private String friendid;
        private String id;
        private String f_name;
        private String l_name;
        private String username;
        private String email;
        private String password;
        private String network;
        private String picture;
        private String profession;
        private String experience;
        private String current_company;
        private String privacy;
        private String coverpic;
        private Object remember_token;
        private String is_verified;
        private String created_at;
        private String updated_at;
        private List<StorypictureBean> storypicture;

        private boolean isSelected = false;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getFriendid() {
            return friendid;
        }

        public void setFriendid(String friendid) {
            this.friendid = friendid;
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

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getCurrent_company() {
            return current_company;
        }

        public void setCurrent_company(String current_company) {
            this.current_company = current_company;
        }

        public String getPrivacy() {
            return privacy;
        }

        public void setPrivacy(String privacy) {
            this.privacy = privacy;
        }

        public String getCoverpic() {
            return coverpic;
        }

        public void setCoverpic(String coverpic) {
            this.coverpic = coverpic;
        }

        public Object getRemember_token() {
            return remember_token;
        }

        public void setRemember_token(Object remember_token) {
            this.remember_token = remember_token;
        }

        public String getIs_verified() {
            return is_verified;
        }

        public void setIs_verified(String is_verified) {
            this.is_verified = is_verified;
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

        public List<StorypictureBean> getStorypicture() {
            return storypicture;
        }

        public void setStorypicture(List<StorypictureBean> storypicture) {
            this.storypicture = storypicture;
        }

        public static class StorypictureBean {
            /**
             * id : 1
             * userid : 17
             * storypicture : StoryPicture_1597158217.jpeg
             * duaration : 24
             * type : 1
             * created_at : 2020-08-11 15:03:37
             * updated_at : 2020-08-11 15:03:37
             * status : 1
             */

            private String id;
            private String userid;
            private String storypicture;
            private String duaration;
            private String type;
            private String created_at;
            private String updated_at;
            private String status;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getStorypicture() {
                return storypicture;
            }

            public void setStorypicture(String storypicture) {
                this.storypicture = storypicture;
            }

            public String getDuaration() {
                return duaration;
            }

            public void setDuaration(String duaration) {
                this.duaration = duaration;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }

    public static class GroupsBean {
        /**
         * id : 4
         * groupadminid : 18
         * grouptitle : APP DEV. TEAM
         * groupuserid : 17,18
         * created_at : 2020-07-17 07:46:42
         * updated_at : 2020-07-17 07:46:42
         */

        private String id;
        private String groupadminid;
        private String grouptitle;
        private String groupuserid;
        private String created_at;
        private String updated_at;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGroupadminid() {
            return groupadminid;
        }

        public void setGroupadminid(String groupadminid) {
            this.groupadminid = groupadminid;
        }

        public String getGrouptitle() {
            return grouptitle;
        }

        public void setGrouptitle(String grouptitle) {
            this.grouptitle = grouptitle;
        }

        public String getGroupuserid() {
            return groupuserid;
        }

        public void setGroupuserid(String groupuserid) {
            this.groupuserid = groupuserid;
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
    }

    public static class CompanyBean {
        /**
         * id : 3
         * name : OpunDoor
         * network : OpunDoor
         * display_picture : Frame1.png
         * profile_picture : coverCompany.jpg
         * website : opundoor.com
         * created_at : null
         * updated_at : null
         */

        private String id;
        private String name;
        private String network;
        private String display_picture;
        private String profile_picture;
        private String website;
        private Object created_at;
        private Object updated_at;

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

        public String getDisplay_picture() {
            return display_picture;
        }

        public void setDisplay_picture(String display_picture) {
            this.display_picture = display_picture;
        }

        public String getProfile_picture() {
            return profile_picture;
        }

        public void setProfile_picture(String profile_picture) {
            this.profile_picture = profile_picture;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public Object getCreated_at() {
            return created_at;
        }

        public void setCreated_at(Object created_at) {
            this.created_at = created_at;
        }

        public Object getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(Object updated_at) {
            this.updated_at = updated_at;
        }
    }
}
