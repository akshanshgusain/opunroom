package com.factor8.opUndoor.Network.Responses;

import java.util.List;

public class GetAllFriendRequest {

    /**
     * message : all friend request users list
     * userlist : [{"id":"5","userid":"22","friendid":"21","status":"0","created_at":"2020-08-13 13:56:31","updated_at":"2020-08-13 13:56:31","allfriendrequestuser":[{"id":"22","f_name":"Ravisha","l_name":"Nautiyal","username":"rnautiyal","email":"ravishanautiyal@opundoor.com","password":"fcea920f7412b5da7be0cf42b8c93759","network":"OpunDoor","picture":"woman-wearing-black-crop-top-and-orange-skirt-standing-1152994_1594987401.jpg","profession":"Management, Business, and Finance","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"pexels-marc-mueller-380768-min.jpg","remember_token":null,"is_verified":"1","created_at":"2020-07-17 12:03:21","updated_at":"2020-07-17 12:03:21"}]},{"id":"4","userid":"18","friendid":"21","status":"0","created_at":"2020-08-13 13:40:37","updated_at":"2020-08-13 13:40:37","allfriendrequestuser":[{"id":"18","f_name":"Akshansh","l_name":"Gusain","username":"akshanshNovo","email":"akshansh.gusain@novostack.com","password":"fcea920f7412b5da7be0cf42b8c93759","network":"OpunDoor","picture":"ProfilePicture_1597163875.jpeg","profession":"Law and Law Enforcement","experience":"10+ Years","current_company":"Don't Want to share","privacy":"2","coverpic":"CoverPicture_1597163875.jpeg","remember_token":null,"is_verified":"1","created_at":"2020-07-17 07:38:06","updated_at":"2020-08-11 16:37:55"}]}]
     * status : 1
     */

    private String message;
    private String status;
    private List<RequestBean> userlist;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RequestBean> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<RequestBean> userlist) {
        this.userlist = userlist;
    }

    public static class RequestBean {
        /**
         * id : 5
         * userid : 22
         * friendid : 21
         * status : 0
         * created_at : 2020-08-13 13:56:31
         * updated_at : 2020-08-13 13:56:31
         * allfriendrequestuser : [{"id":"22","f_name":"Ravisha","l_name":"Nautiyal","username":"rnautiyal","email":"ravishanautiyal@opundoor.com","password":"fcea920f7412b5da7be0cf42b8c93759","network":"OpunDoor","picture":"woman-wearing-black-crop-top-and-orange-skirt-standing-1152994_1594987401.jpg","profession":"Management, Business, and Finance","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"pexels-marc-mueller-380768-min.jpg","remember_token":null,"is_verified":"1","created_at":"2020-07-17 12:03:21","updated_at":"2020-07-17 12:03:21"}]
         */

        private String id;
        private String userid;
        private String friendid;
        private String status;
        private String created_at;
        private String updated_at;
        private List<UserBean> allfriendrequestuser;

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

        public String getFriendid() {
            return friendid;
        }

        public void setFriendid(String friendid) {
            this.friendid = friendid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public List<UserBean> getAllfriendrequestuser() {
            return allfriendrequestuser;
        }

        public void setAllfriendrequestuser(List<UserBean> allfriendrequestuser) {
            this.allfriendrequestuser = allfriendrequestuser;
        }

        public static class UserBean {
            /**
             * id : 22
             * f_name : Ravisha
             * l_name : Nautiyal
             * username : rnautiyal
             * email : ravishanautiyal@opundoor.com
             * password : fcea920f7412b5da7be0cf42b8c93759
             * network : OpunDoor
             * picture : woman-wearing-black-crop-top-and-orange-skirt-standing-1152994_1594987401.jpg
             * profession : Management, Business, and Finance
             * experience : 1-2
             * current_company : opUndoor
             * privacy : 2
             * coverpic : pexels-marc-mueller-380768-min.jpg
             * remember_token : null
             * is_verified : 1
             * created_at : 2020-07-17 12:03:21
             * updated_at : 2020-07-17 12:03:21
             */

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
        }
    }
}
