package com.factor8.opUndoor.Network.Responses;

import java.util.List;

public class GroupMembers {

    /**
     * status : 1
     * groups : [{"id":"20","groupadminid":"10","grouptitle":"IT Testing","groupuserid":"9","created_at":"2020-05-22 09:15:17","updated_at":"2020-05-22 09:15:17","adminname":"Akshansh","adminpicture":"http://dass.io/oppo/app/profile/image/ProfilePicture_1590070864.jpeg","member":[{"id":9,"f_name":"Abhishek","l_name":"Jain","username":"abhishek","email":"abhishek@factor8.com","network":"factor8","picture":"http://dass.io/oppo/app/profile/image/ProfilePicture_1590510160.jpeg","profession":"develoer","experience":"0","current_company":null,"created_at":"2020-05-21T14:14:59.000000Z","updated_at":"2020-05-26T16:22:40.000000Z"}],"admin":[{"id":10,"f_name":"Akshansh","l_name":"Gusain","username":"akshanshf8","email":"akshansh@factor8.com","network":"factor8","picture":"http://dass.io/oppo/app/profile/image/ProfilePicture_1590070864.jpeg","profession":"develoer","experience":"0","current_company":null,"created_at":"2020-05-21T14:21:04.000000Z","updated_at":"2020-05-21T14:21:04.000000Z"}]}]
     */

    private int status;
    private List<GroupsBean> groups;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<GroupsBean> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupsBean> groups) {
        this.groups = groups;
    }

    public static class GroupsBean {
        /**
         * id : 20
         * groupadminid : 10
         * grouptitle : IT Testing
         * groupuserid : 9
         * created_at : 2020-05-22 09:15:17
         * updated_at : 2020-05-22 09:15:17
         * adminname : Akshansh
         * adminpicture : http://dass.io/oppo/app/profile/image/ProfilePicture_1590070864.jpeg
         * member : [{"id":9,"f_name":"Abhishek","l_name":"Jain","username":"abhishek","email":"abhishek@factor8.com","network":"factor8","picture":"http://dass.io/oppo/app/profile/image/ProfilePicture_1590510160.jpeg","profession":"develoer","experience":"0","current_company":null,"created_at":"2020-05-21T14:14:59.000000Z","updated_at":"2020-05-26T16:22:40.000000Z"}]
         * admin : [{"id":10,"f_name":"Akshansh","l_name":"Gusain","username":"akshanshf8","email":"akshansh@factor8.com","network":"factor8","picture":"http://dass.io/oppo/app/profile/image/ProfilePicture_1590070864.jpeg","profession":"develoer","experience":"0","current_company":null,"created_at":"2020-05-21T14:21:04.000000Z","updated_at":"2020-05-21T14:21:04.000000Z"}]
         */

        private String id;
        private String groupadminid;
        private String grouptitle;
        private String groupuserid;
        private String created_at;
        private String updated_at;
        private String adminname;
        private String adminpicture;
        private List<MemberBean> member;
        private List<AdminBean> admin;

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

        public String getAdminname() {
            return adminname;
        }

        public void setAdminname(String adminname) {
            this.adminname = adminname;
        }

        public String getAdminpicture() {
            return adminpicture;
        }

        public void setAdminpicture(String adminpicture) {
            this.adminpicture = adminpicture;
        }

        public List<MemberBean> getMember() {
            return member;
        }

        public void setMember(List<MemberBean> member) {
            this.member = member;
        }

        public List<AdminBean> getAdmin() {
            return admin;
        }

        public void setAdmin(List<AdminBean> admin) {
            this.admin = admin;
        }

        public static class MemberBean {
            /**
             * id : 9
             * f_name : Abhishek
             * l_name : Jain
             * username : abhishek
             * email : abhishek@factor8.com
             * network : factor8
             * picture : http://dass.io/oppo/app/profile/image/ProfilePicture_1590510160.jpeg
             * profession : develoer
             * experience : 0
             * current_company : null
             * created_at : 2020-05-21T14:14:59.000000Z
             * updated_at : 2020-05-26T16:22:40.000000Z
             */

            private int id;
            private String f_name;
            private String l_name;
            private String username;
            private String email;
            private String network;
            private String picture;
            private String profession;
            private String experience;
            private Object current_company;
            private String created_at;
            private String updated_at;
            private boolean isSelected;

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
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

            public Object getCurrent_company() {
                return current_company;
            }

            public void setCurrent_company(Object current_company) {
                this.current_company = current_company;
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

        public static class AdminBean {
            /**
             * id : 10
             * f_name : Akshansh
             * l_name : Gusain
             * username : akshanshf8
             * email : akshansh@factor8.com
             * network : factor8
             * picture : http://dass.io/oppo/app/profile/image/ProfilePicture_1590070864.jpeg
             * profession : develoer
             * experience : 0
             * current_company : null
             * created_at : 2020-05-21T14:21:04.000000Z
             * updated_at : 2020-05-21T14:21:04.000000Z
             */

            private int id;
            private String f_name;
            private String l_name;
            private String username;
            private String email;
            private String network;
            private String picture;
            private String profession;
            private String experience;
            private Object current_company;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
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

            public Object getCurrent_company() {
                return current_company;
            }

            public void setCurrent_company(Object current_company) {
                this.current_company = current_company;
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
    }
}
