package com.factor8.opUndoor.Network.Responses;

import java.util.List;

public class SearchResult {

    /**
     * status : 1
     * user : [{"id":17,"f_name":"Ashwani","l_name":"K","username":"Ashwani","email":"ashwanikv009@gmail.com","network":"Opundoor","picture":"https://dass.io/oppo/app/profile/image/ProfilePicture_1596548284.jpeg","profession":"Computers and Technology","experience":"1-2 Years","current_company":"Don't Want to share","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-16T06:13:22.000000Z","updated_at":"2020-08-04T13:38:04.000000Z"},{"id":18,"f_name":"Akshansh","l_name":"Gusain","username":"akshanshNovo","email":"akshansh.gusain@novostack.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/ProfilePicture_1594971486.jpeg","profession":"Computers and Technology","experience":"2-5 Years","current_company":"Novostack","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-17T07:38:06.000000Z","updated_at":"2020-07-17T07:38:44.000000Z"},{"id":20,"f_name":"Jhanvi","l_name":"Rawat","username":"jrawat","email":"akshanshssj5@gmail.com","network":"novostack","picture":"https://dass.io/oppo/app/profile/image/woman-wearing-pink-collared-half-sleeved-top-1036623_1594984925.jpg","profession":"Management, Business, and Finance","experience":"2-5","current_company":"Novostack","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-17T11:22:05.000000Z","updated_at":"2020-07-17T11:26:25.000000Z"},{"id":21,"f_name":"Sarthak","l_name":"Gill","username":"sgill","email":"akshanshgusain@gmail.com","network":"factor8","picture":"https://dass.io/oppo/app/profile/image/man-wearing-a-jacket-sitting-on-brown-wooden-crate-594610_1594985985.jpg","profession":"Arts and Communications","experience":"2-5","current_company":"Factor8","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-17T11:39:45.000000Z","updated_at":"2020-07-17T11:43:59.000000Z"},{"id":22,"f_name":"Ravisha","l_name":"Nautiyal","username":"rnautiyal","email":"ravishanautiyal@opundoor.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/woman-wearing-black-crop-top-and-orange-skirt-standing-1152994_1594987401.jpg","profession":"Management, Business, and Finance","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-17T12:03:21.000000Z","updated_at":"2020-07-17T12:03:21.000000Z"},{"id":23,"f_name":"Nikita","l_name":"Singh","username":"nsingh","email":"nikitasingh@opundoor.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/woman-wearing-black-long-sleeved-shirt-1832959_1594987548.jpg","profession":"Arts and Communications","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-17T12:05:48.000000Z","updated_at":"2020-07-17T12:05:48.000000Z"},{"id":24,"f_name":"Apoorva","l_name":"Dwivedi","username":"adwivedi","email":"apoorvadwivedi@opundoor.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/photo-of-woman-wearing-sunglasses-1485031_1594987755.jpg","profession":"Arts and Communications","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-17T12:09:15.000000Z","updated_at":"2020-07-17T12:09:15.000000Z"},{"id":25,"f_name":"Priyanka","l_name":"Khurana","username":"pkhurana","email":"priyankakhurana@opundoor.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/side-view-photo-of-woman-2010877_1594987836.jpg","profession":"Computers and Technology","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-17T12:10:36.000000Z","updated_at":"2020-07-17T12:10:36.000000Z"},{"id":26,"f_name":"Navneet","l_name":"Shergill","username":"nshergill","email":"navneetshergill@opundoor.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/business-businessman-contemporary-corporate-532220_1594987919.jpg","profession":"Computers and Technology","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-17T12:11:59.000000Z","updated_at":"2020-07-17T12:11:59.000000Z"},{"id":27,"f_name":"Ranvijay","l_name":"Rana","username":"rrana","email":"ranvijayrana@opundoor.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/two-men-standing-to-each-other-2005831_1594988003.jpg","profession":"Computers and Technology","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-17T12:13:23.000000Z","updated_at":"2020-07-17T12:13:23.000000Z"},{"id":28,"f_name":"Ishan","l_name":"Jain","username":"ijain","email":"ishanjain@opundoor.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/face-facial-hair-fine-looking-guy-614810-min_1594988229.jpg","profession":"Computers and Technology","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-17T12:17:09.000000Z","updated_at":"2020-07-17T12:17:09.000000Z"},{"id":29,"f_name":"john","l_name":"doe","username":"John123","email":"playstorecnx13@gmail.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/ProfilePicture_1595254071.jpeg","profession":"Computers and Technology","experience":"1-2 Years","current_company":"Don't Want to share","privacy":"2","coverpic":"","is_verified":"0","created_at":"2020-07-20T14:07:51.000000Z","updated_at":"2020-07-20T14:07:51.000000Z"},{"id":30,"f_name":"Abhishek","l_name":"Jain","username":"Aby","email":"jabhishek1002@gmail.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/ProfilePicture_1595438509.jpeg","profession":"Computers and Technology","experience":"10+ Years","current_company":"Accenture","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-22T17:21:49.000000Z","updated_at":"2020-07-22T17:29:16.000000Z"},{"id":31,"f_name":"Pallavi","l_name":"Khare","username":"pallavikhare","email":"pallavikhare.iitd@gmail.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/ProfilePicture_1595559745.jpeg","profession":"Other","experience":"5-10 Years","current_company":"Don't Want to share","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-24T03:02:25.000000Z","updated_at":"2020-07-24T07:33:51.000000Z"},{"id":32,"f_name":"Abhishek","l_name":"Jain","username":"Abhishek","email":"84abhis@gmail.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/ProfilePicture_1595741758.jpeg","profession":"Management, Business, and Finance","experience":"1-2 Years","current_company":"Accenture","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-26T05:35:58.000000Z","updated_at":"2020-07-26T05:41:12.000000Z"},{"id":33,"f_name":"Yash","l_name":"Parashar","username":"lookforyash","email":"parashar.yash13@gmail.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/ProfilePicture_1596199700.jpeg","profession":"Computers and Technology","experience":"1-2 Years","current_company":"Novostack","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-31T12:48:20.000000Z","updated_at":"2020-07-31T13:00:12.000000Z"},{"id":34,"f_name":"Danish","l_name":"saifi","username":"Stark","email":"danish.saifi@novostack.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/ProfilePicture_1596199774.jpeg","profession":"Computers and Technology","experience":"1-2 Years","current_company":"Novostack","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-07-31T12:49:34.000000Z","updated_at":"2020-07-31T12:50:21.000000Z"},{"id":40,"f_name":"Ayushi","l_name":"Goel","username":"agoel","email":"ayushigoel@novostack.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/unnamed_1596518786.png","profession":"Computers and Technology","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-08-04T05:26:26.000000Z","updated_at":"2020-08-04T05:26:26.000000Z"},{"id":41,"f_name":"Prachi","l_name":"Gupta","username":"pgupta","email":"prachigupta@novostack.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/unnamed_1596518890.png","profession":"Computers and Technology","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-08-04T05:28:10.000000Z","updated_at":"2020-08-04T05:28:10.000000Z"},{"id":42,"f_name":"Abhishek","l_name":"Gupta","username":"agupta","email":"abhishekgupta@novostack.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/unnamed_1596518942.png","profession":"Computers and Technology","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-08-04T05:29:02.000000Z","updated_at":"2020-08-04T05:29:02.000000Z"},{"id":43,"f_name":"Akshay","l_name":"Srivasttava","username":"asrivastava","email":"akshaysrivastava@novostack.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/unnamed_1596519017.png","profession":"Computers and Technology","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-08-04T05:30:17.000000Z","updated_at":"2020-08-04T05:30:17.000000Z"},{"id":44,"f_name":"Anant","l_name":"Mishra","username":"amishra","email":"anantmishra@novostack.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/unnamed_1596519062.png","profession":"Computers and Technology","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-08-04T05:31:02.000000Z","updated_at":"2020-08-04T05:31:02.000000Z"},{"id":45,"f_name":"Kartik","l_name":"Srivastava","username":"ksrivastava","email":"kartiksrivastava@novostack.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/unnamed_1596519132.png","profession":"Computers and Technology","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-08-04T05:32:12.000000Z","updated_at":"2020-08-04T05:32:12.000000Z"},{"id":46,"f_name":"Durgesh","l_name":"Yadav","username":"dyadav","email":"durgeshyadav@novostack.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/unnamed_1596519218.png","profession":"Computers and Technology","experience":"1-2","current_company":"opUndoor","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-08-04T05:33:38.000000Z","updated_at":"2020-08-04T05:33:38.000000Z"},{"id":47,"f_name":"Mohana","l_name":"Gupta","username":"mgupta","email":"mohanagupta@novostack.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/unnamed_1596519325.png","profession":"Computers and Technology","experience":"1-2","current_company":"Novostack","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-08-04T05:35:25.000000Z","updated_at":"2020-08-04T05:35:25.000000Z"},{"id":48,"f_name":"Omkar","l_name":"Gupta","username":"ogupta","email":"omkargupta@novostack.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/unnamed_1596519374.png","profession":"Computers and Technology","experience":"1-2","current_company":"Novostack","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-08-04T05:36:14.000000Z","updated_at":"2020-08-04T05:36:14.000000Z"},{"id":49,"f_name":"Vikramjit","l_name":"Singh","username":"vsingh","email":"vikramjitsingh@novostack.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/unnamed_1596519459.png","profession":"Computers and Technology","experience":"1-2","current_company":"Novostack","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-08-04T05:37:39.000000Z","updated_at":"2020-08-04T05:37:39.000000Z"},{"id":50,"f_name":"Vinay","l_name":"Kumar","username":"vkumar","email":"vineykumar@novostack.com","network":"OpunDoor","picture":"https://dass.io/oppo/app/profile/image/ProfilePicture_1596523991.jpeg","profession":"Computers and Technology","experience":"1-2","current_company":"Novostack","privacy":"2","coverpic":"","is_verified":"1","created_at":"2020-08-04T05:38:32.000000Z","updated_at":"2020-08-04T06:53:11.000000Z"}]
     * company : [{"id":1,"name":"novostack pvt ltd","network":"novostack","display_picture":"https://dass.io/oppo/app/story/company/novostack_logo.png","profile_picture":"https://dass.io/oppo/app/story/company/novostack_cover.jpg","website":"https://novostack.com/","created_at":null,"updated_at":null},{"id":2,"name":"factor8 pvt ltd","network":"factor8","display_picture":"https://dass.io/oppo/app/story/company/facor8.png","profile_picture":"http://dass.io/oppo/app/story/company/factor8_cover.jpeg","website":"factor8.com","created_at":null,"updated_at":null},{"id":3,"name":"OpunDoor","network":"OpunDoor","display_picture":"http://dass.io/oppo/app/story/company/Frame1.png","profile_picture":"https://dass.io/oppo/app/story/company/coverCompany.jpg","website":"opundoor.com","created_at":null,"updated_at":null}]
     */

    private String status;
    private List<UserBean> user;
    private List<CompanyBean> company;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public List<CompanyBean> getCompany() {
        return company;
    }

    public void setCompany(List<CompanyBean> company) {
        this.company = company;
    }

    public static class UserBean {
        /**
         * id : 17
         * f_name : Ashwani
         * l_name : K
         * username : Ashwani
         * email : ashwanikv009@gmail.com
         * network : Opundoor
         * picture : https://dass.io/oppo/app/profile/image/ProfilePicture_1596548284.jpeg
         * profession : Computers and Technology
         * experience : 1-2 Years
         * current_company : Don't Want to share
         * privacy : 2
         * coverpic :
         * is_verified : 1
         * created_at : 2020-07-16T06:13:22.000000Z
         * updated_at : 2020-08-04T13:38:04.000000Z
         */

        private String id;
        private String f_name;
        private String l_name;
        private String username;
        private String email;
        private String network;
        private String picture;
        private String profession;
        private String experience;
        private String current_company;
        private String privacy;
        private String coverpic;
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

    public static class CompanyBean {
        /**
         * id : 1
         * name : novostack pvt ltd
         * network : novostack
         * display_picture : https://dass.io/oppo/app/story/company/novostack_logo.png
         * profile_picture : https://dass.io/oppo/app/story/company/novostack_cover.jpg
         * website : https://novostack.com/
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
