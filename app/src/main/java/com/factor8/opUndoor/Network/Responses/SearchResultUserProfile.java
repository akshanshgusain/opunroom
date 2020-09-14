package com.factor8.opUndoor.Network.Responses;

import java.util.List;

public class SearchResultUserProfile {


    /**
     * status : 1
     * user : {"id":"18","f_name":"Akshansh","l_name":"Gusain","username":"akshanshNovo","email":"akshansh.gusain@novostack.com","password":"fcea920f7412b5da7be0cf42b8c93759","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/ProfilePicture_1594971486.jpeg","profession":"Computers and Technology","experience":"2-5 Years","current_company":"Novostack","privacy":"2","coverpic":"","remember_token":null,"is_verified":"1","created_at":"2020-07-17 07:38:06","updated_at":"2020-07-17 07:38:44"}
     * company : [{"id":"3","name":"OpunDoor","network":"OpunDoor","display_picture":"http://dass.io/oppo/app/story/company/Frame1.png","profile_picture":"https://dass.io/oppo/app/story/company/coverCompany.jpg","website":"opundoor.com","created_at":null,"updated_at":null}]
     * collegue : 0
     * is_subscribe : 0
     * subscriber : 0
     * subscriptions : 0
     */

    private String status;
    private User user;
    private int collegue;
    private int is_subscribe;
    private int subscriber;
    private int subscriptions;
    private int is_friend;


    public int getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(int is_friend) {
        this.is_friend = is_friend;
    }


    private List<Company> company;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCollegue() {
        return collegue;
    }

    public void setCollegue(int collegue) {
        this.collegue = collegue;
    }

    public int getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(int is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public int getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(int subscriber) {
        this.subscriber = subscriber;
    }

    public int getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(int subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Company> getCompany() {
        return company;
    }

    public void setCompany(List<Company> company) {
        this.company = company;
    }

    public static class User {
        /**
         * id : 18
         * f_name : Akshansh
         * l_name : Gusain
         * username : akshanshNovo
         * email : akshansh.gusain@novostack.com
         * password : fcea920f7412b5da7be0cf42b8c93759
         * network : OpunDoor
         * picture : https://dass.io/oppo/app/profile/image/ProfilePicture_1594971486.jpeg
         * profession : Computers and Technology
         * experience : 2-5 Years
         * current_company : Novostack
         * privacy : 2
         * coverpic :
         * remember_token : null
         * is_verified : 1
         * created_at : 2020-07-17 07:38:06
         * updated_at : 2020-07-17 07:38:44
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

    public static class Company {
        /**
         * id : 3
         * name : OpunDoor
         * network : OpunDoor
         * display_picture : http://dass.io/oppo/app/story/company/Frame1.png
         * profile_picture : https://dass.io/oppo/app/story/company/coverCompany.jpg
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
