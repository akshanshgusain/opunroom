package com.factor8.opUndoor.Network;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.factor8.opUndoor.ProjectConstants;
import com.factor8.opUndoor.Utils;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.factor8.opUndoor.SwipableViews.CameraFragment.MEDIA_TYPE_VIDEO;

public class RestCalls {
    private static final String BASE_URL = "http://dass.io/oppo/api/";
    private static final String REGISTER_URL = "user/register";
    private static final String CHECK_NETWORK = "user/checknetwork";
    private static final String CHECK_USERNAME = "user/checkusername";
    private static final String CHECK_EMAIL = "user/checkemail";
    private static final String LOGIN = "user/login";
    private static final String UPDATE = "user/update";
    private static final String GET_FRIEND_LIST = "user/getuserfriend";
    private static final String CREATE_GROUP = "user/creategroup";
    private static final String FEED = "user/feed";
    private static final String CREATE_STORY = "user/createstory";
    private static final String DELETE_GROUP = "user/deletegroup";
    private static final String GET_GROUP_MEMBERS =  "user/getmembergroup";
    private static final String UPDATE_GROUP = "user/updategroup";
    private static final String FORGET_PASSWORD = "user/forgotpassword";


    private static final String TAG = "RestCalls";

    private Context mContext;

    public RestCalls(Context mContext) {
        this.mContext = mContext;
    }

    public void checkNetworkCall(final String networkName) {
        final CheckNetworkCallI checkNetworkCallI = (CheckNetworkCallI) mContext;
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + CHECK_NETWORK, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                try {
                    JSONObject obj = new JSONObject(stringResponse);
                    Log.d(TAG, "onResponse: " + stringResponse);
                    responseMap.put("message", obj.getString("message"));
                    responseMap.put("status", obj.getString("status"));
                    checkNetworkCallI.response(responseMap);
                } catch (JSONException e) {
                    errorMap.put("exception", e.getLocalizedMessage());
                    checkNetworkCallI.errorRequest(errorMap);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", error.getLocalizedMessage());
                checkNetworkCallI.errorRequest(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("network", networkName);
                return paramsMap;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    public interface CheckNetworkCallI {
        public void response(Map<String, String> response);

        public void errorRequest(Map<String, String> response);
    }

    //---Check UserName
    public void checkUserName(final String username) {
        final CheckUsernameI checkUsernameI = (CheckUsernameI) mContext;
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + CHECK_USERNAME, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                try {
                    JSONObject obj = new JSONObject(stringResponse);
                    Log.d(TAG, "onResponse: " + stringResponse);
                    responseMap.put("message", obj.getString("message"));
                    responseMap.put("status", obj.getString("status"));
                    checkUsernameI.response(responseMap);
                } catch (JSONException e) {
                    errorMap.put("exception", e.getLocalizedMessage());
                    checkUsernameI.errorRequest(errorMap);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", error.getLocalizedMessage());
                checkUsernameI.errorRequest(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("username", username);
                return paramsMap;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);


    }

    public interface CheckUsernameI {
        public void response(Map<String, String> response);

        public void errorRequest(Map<String, String> response);
    }


    //---Check Email availabliity
    public void checkEmail(final String email) {
        final CheckEmailI checkEmailI = (CheckEmailI) mContext;
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + CHECK_EMAIL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                try {
                    JSONObject obj = new JSONObject(stringResponse);
                    Log.d(TAG, "onResponse: " + stringResponse);
                    responseMap.put("message", obj.getString("message"));
                    responseMap.put("status", obj.getString("status"));
                    checkEmailI.responseEmailCheck(responseMap);
                } catch (JSONException e) {
                    errorMap.put("exception", e.getLocalizedMessage());
                    checkEmailI.errorRequestEmailCheck(errorMap);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", Utils.getVolleyErrorString(error));
                checkEmailI.errorRequestEmailCheck(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("email", email);
                return paramsMap;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);


    }

    public interface CheckEmailI {
        public void responseEmailCheck(Map<String, String> response);

        public void errorRequestEmailCheck(Map<String, String> response);
    }

    //--------Register API Call

    public void registerUser(final Bundle bundle) {

        final RegisterUserI registerUserI = (RegisterUserI) mContext;
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + REGISTER_URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                Log.d("RegisterApiCall", "Register API RESPONSE: " + stringResponse);
                try {
                    JSONObject obj = new JSONObject(stringResponse);
                    responseMap.put("status", obj.getString("status"));
                    responseMap.put("message", obj.getString("message"));
                    registerUserI.response(responseMap);
                } catch (JSONException e) {
                    errorMap.put("exception", e.getLocalizedMessage());
                    registerUserI.errorRequest(errorMap);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", Utils.getVolleyErrorString(error));
                Log.d(TAG, "onErrorResponse: " + Utils.getVolleyErrorString(error));
                registerUserI.errorRequest(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("f_name", Objects.requireNonNull(bundle.get(ProjectConstants.INTENT_FIRST_NAME_WORKSPACE)).toString());
                paramsMap.put("l_name", bundle.get(ProjectConstants.INTENT_LAST_NAME_WORKSPACE).toString());
                paramsMap.put("username", bundle.get(ProjectConstants.INTENT_USERNAME_WORKSPACE).toString());
                paramsMap.put("email", bundle.get(ProjectConstants.INTENT_EMAIL_WORKSPACE).toString());
                paramsMap.put("password", bundle.get(ProjectConstants.INTENT_PASSWORD_WORKSPACE).toString());
                paramsMap.put("network", bundle.get(ProjectConstants.INTENT_NAME_WORKSPACE).toString());
                paramsMap.put("profession", bundle.get(ProjectConstants.INTENT_PROFESSION_WORKSPACE).toString());
                paramsMap.put("experience", bundle.get(ProjectConstants.INTENT_EXP_WORKSPACE).toString());
                paramsMap.put("current_company", bundle.get(ProjectConstants.INTENT_CURRENT_COMPANY_WORKSPACE).toString());
                Log.d("RegisterApiCall", "getParams: Params Value: "+ paramsMap.toString());
                return paramsMap;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                params.put("picture", new DataPart("ProfilePicture.jpeg", bundle.getByteArray(ProjectConstants.INTENT_PICTURE_WORKSPACE)));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    public interface RegisterUserI {
        public void response(Map<String, String> response);

        public void errorRequest(Map<String, String> response);
    }

    //--------- Login Call
    public void loginCall(final String username, final String password) {
        final LoginUserI loginUserI = (LoginUserI) mContext;
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + LOGIN, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                Log.d(TAG, "Login API RESponse: " + stringResponse);

                try {
                    JSONObject obj = new JSONObject(stringResponse);
                    responseMap.put("id", obj.getString("id"));
                    responseMap.put("f_name", obj.getString("f_name"));
                    responseMap.put("l_name", obj.getString("l_name"));
                    responseMap.put("username", obj.getString("username"));
                    responseMap.put("email", obj.getString("email"));
                    responseMap.put("picture", obj.getString("picture"));
                    responseMap.put("network", obj.getString("network"));
                    responseMap.put("profession", obj.getString("profession"));
                    responseMap.put("is_verified", obj.getString("is_verified"));
                    responseMap.put("experience", obj.getString("experience"));
                    loginUserI.response(responseMap);
                } catch (JSONException e) {
                    errorMap.put("exception", e.toString());
                    loginUserI.errorRequest(errorMap);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", Utils.getVolleyErrorString(error));
                loginUserI.errorRequest(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("username", username);
                paramsMap.put("password", password);
                return paramsMap;
            }

        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    public interface LoginUserI {
        public void response(Map<String, String> response);

        public void errorRequest(Map<String, String> response);
    }

    //---------------- ProfileUpdate
    public void updateUserProfile(final Bundle bundle) {

        final UpdateUserProfileI loginUserI = (UpdateUserProfileI) mContext;
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + UPDATE, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                Log.d(TAG, "Update Profile API RESPONSE: " + stringResponse);
                try {
                    JSONObject obj = new JSONObject(stringResponse);
                    responseMap.put("id", obj.getString("id"));
                    responseMap.put("f_name", obj.getString("f_name"));
                    responseMap.put("l_name", obj.getString("l_name"));
                    responseMap.put("username", obj.getString("username"));
                    responseMap.put("email", obj.getString("email"));
                    responseMap.put("picture", obj.getString("picture"));
                    responseMap.put("network", obj.getString("network"));

                    loginUserI.response(responseMap);
                } catch (JSONException e) {
                    errorMap.put("exception", e.getLocalizedMessage());
                    loginUserI.errorRequest(errorMap);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", error.getLocalizedMessage());
                loginUserI.errorRequest(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("f_name", Objects.requireNonNull(bundle.getString("f_name")));
                paramsMap.put("l_name", Objects.requireNonNull(bundle.getString("l_name")));
                paramsMap.put("id", Objects.requireNonNull(bundle.getString("id")));
                paramsMap.put("username", Objects.requireNonNull(bundle.getString("username")));
                paramsMap.put("email", Objects.requireNonNull(bundle.getString("email")));
                paramsMap.put("network", Objects.requireNonNull(bundle.getString("network")));
                return paramsMap;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                params.put("picture", new DataPart("ProfilePicture.jpeg", bundle.getByteArray(ProjectConstants.INTENT_PICTURE_WORKSPACE)));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    public interface UpdateUserProfileI {
        public void response(Map<String, String> response);

        public void errorRequest(Map<String, String> response);
    }

    //------------------ Get Friend list
    public void getFriendsList(final String userId) {

        final GetFriendsListI getFriendsListI = (GetFriendsListI) mContext;
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();

        final List<Friends> responseFriendList = new ArrayList<>();
        final List<Groups> responseGroupList = new ArrayList<>();
        final List<Company> responseCompanyList = new ArrayList<>();


        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + GET_FRIEND_LIST, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                Log.d("getFriendsList", "Update Profile API RESPONSE: " + stringResponse);
                try {
                    JSONObject obj = new JSONObject(stringResponse);
                    responseMap.put("status", obj.getString("status"));
                    JSONArray friendsArray = obj.getJSONArray("friends");
                    JSONArray groupsArray = obj.getJSONArray("groups");
                    JSONArray companyArray = obj.getJSONArray("company");

                    if (obj.getString("status").equals("1")) {
                        //Fetch Friends
                        for (int i = 0; i < friendsArray.length(); i++) {
                            JSONObject friendJSON = friendsArray.getJSONObject(i);
                            Friends friend = new Friends(
                                    friendJSON.getString("id")
                                    , friendJSON.getString("f_name")
                                    , friendJSON.getString("l_name")
                                    , friendJSON.getString("username")
                                    , friendJSON.getString("email")
                                    , friendJSON.getString("password")
                                    , friendJSON.getString("network")
                                    , friendJSON.getString("picture"), null
                                    , friendJSON.getString("profession")
                            );
                            responseFriendList.add(friend);
                        }
                        //Fetch Groups
                        for (int j = 0; j < groupsArray.length(); j++) {
                            JSONObject groupJSON = groupsArray.getJSONObject(j);
                            Groups group = new Groups(
                                    groupJSON.getString("id")
                                    , groupJSON.getString("grouptitle")
                                    , groupJSON.getString("groupadminid")
                                    , null);
                            responseGroupList.add(group);
                        }
                        //Fetch Company
                        for (int k = 0; k < companyArray.length(); k++) {
                            JSONObject companyJSON = companyArray.getJSONObject(k);
                            Company company = new Company(
                                    companyJSON.getString("id")
                                    , companyJSON.getString("name")
                                    , companyJSON.getString("network")
                                    , companyJSON.getString("display_picture")
                                    , companyJSON.getString("profile_picture")
                                    , companyJSON.getString("website"),
                                    null
                            );
                            responseCompanyList.add(company);
                        }

                    }
                    getFriendsListI.responseList(responseFriendList);
                    getFriendsListI.responseListGroups(responseGroupList);
                    getFriendsListI.responseListCompany(responseCompanyList);
                    getFriendsListI.response(responseMap);
                } catch (JSONException e) {
                    errorMap.put("exception", e.getLocalizedMessage());
                    getFriendsListI.errorRequest(errorMap);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", error.getLocalizedMessage());
                getFriendsListI.errorRequest(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("userid", userId);
                return paramsMap;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    public interface GetFriendsListI {
        public void response(Map<String, String> response);

        public void errorRequest(Map<String, String> response);

        public void responseList(List<Friends> friendsList);

        public void responseListGroups(List<Groups> groupsList);

        public void responseListCompany(List<Company> companyList);
    }

    //------------------ Get Friend list 2
    public void getFriendsList2(final String userId) {

        final GetFriendsList2I getFriendsList2I = (GetFriendsList2I) mContext;
        final Map<String, String> errorMap = new HashMap<>();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + GET_FRIEND_LIST, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);

                    Friends2 friends2 = new Friends2();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    friends2 = gson.fromJson(stringResponse, Friends2.class);
                    getFriendsList2I.response(friends2);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", Utils.getVolleyErrorString(error));
                getFriendsList2I.errorRequest(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("userid", userId);
                return paramsMap;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }
    public interface GetFriendsList2I {
        public void response(Friends2 response);

        public void errorRequest(Map<String, String> response);

    }

    //------------------ Create Group---------
    public void createGroup(final Map<String, String> paramMap) {

        final CreateGroupI createGroupI = (CreateGroupI) mContext;
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + CREATE_GROUP, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                Log.d("createGroupResponse", "Update Profile API RESPONSE: " + stringResponse);
                try {
                    JSONObject obj = new JSONObject(stringResponse);
                    responseMap.put("status", obj.getString("status"));
                    createGroupI.response2(responseMap);
                } catch (JSONException e) {
                    errorMap.put("exception", e.getLocalizedMessage());
                    createGroupI.errorRequest2(errorMap);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", error.getLocalizedMessage());
                createGroupI.errorRequest2(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paramMap;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    public interface CreateGroupI {
        public void response2(Map<String, String> response);

        public void errorRequest2(Map<String, String> response);

    }

    //------------------ Get Feed---------
    public void getFeed(final String userid) {

        final FeedI feedI = (FeedI) mContext;
        final Map<String, String> errorMap = new HashMap<>();


        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + FEED, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                Log.d(TAG, "Feed API RESPONSE: " + stringResponse);

                Feed feed = new Feed();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                feed = gson.fromJson(stringResponse, Feed.class);
                feedI.feedResponse(feed);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", Utils.getVolleyErrorString(error));
                feedI.feedErrorResponse(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("userid", userid);
                return paramMap;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    public interface FeedI {
       public void feedResponse(Feed feed);
       public void feedErrorResponse(Map<String, String> errorMap);
    }

    //------------------ Create Story ------------
    public void createStory(final Bundle bundle, final String mediaType) {


        final CreateStoryI createGroupI = (CreateStoryI) mContext;
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + CREATE_STORY, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                Log.d("createStoryTagu", "Update Profile API RESPONSE: " + stringResponse);
                try {
                    JSONObject obj = new JSONObject(stringResponse);
                    responseMap.put("status", obj.getString("status"));
                    createGroupI.responseCS(responseMap);
                } catch (JSONException e) {
                    errorMap.put("exception", e.getLocalizedMessage());
                    createGroupI.errorRequestCS(errorMap);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", Utils.getVolleyErrorString(error));
                createGroupI.errorRequestCS(errorMap);
                Log.d("createStoryTagu", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("userid", Objects.requireNonNull(bundle.getString("userid")));
                paramsMap.put("selfid", Objects.requireNonNull(bundle.getString("selfid")));
                paramsMap.put("friends", Objects.requireNonNull(bundle.getString("friends")));
                paramsMap.put("groups", Objects.requireNonNull(bundle.getString("groups")));
                paramsMap.put("company", Objects.requireNonNull(bundle.getString("company")));
                paramsMap.put("duaration", Objects.requireNonNull(bundle.getString("duaration")));
                if (mediaType.equals(MEDIA_TYPE_VIDEO)) {
                    paramsMap.put("video", Objects.requireNonNull(bundle.getString("video")));
                }

                return paramsMap;
            }


            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                params.put("picture", new DataPart("StoryPicture.jpeg", bundle.getByteArray(ProjectConstants.INTENT_PICTURE_WORKSPACE)));
                if (mediaType.equals(MEDIA_TYPE_VIDEO)) {
                    return super.getByteData();
                } else {
                    return params;
                }

            }

        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    public interface CreateStoryI {
        public void responseCS(Map<String, String> response);

        public void errorRequestCS(Map<String, String> response);
    }


    //------------------ Get News Story ------------
    public void getNewsStory(final int type) {
        final String NEWS_API_BUSINESS = "http://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=b0fccdb021ef4610ad1bc3de8d21046b&pageSize=50";
        final String NEWS_API_SCIENCE = "http://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=b0fccdb021ef4610ad1bc3de8d21046b&pageSize=50";
        final String NEWS_API_TECHNOLOGY = "http://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=b0fccdb021ef4610ad1bc3de8d21046b&pageSize=50";

        String NEWS_API = "";

        switch (type) {
            case 0:
                NEWS_API = NEWS_API_SCIENCE;
                break;
            case 1:
                NEWS_API = NEWS_API_BUSINESS;
                break;
            case 2:
                NEWS_API = NEWS_API_TECHNOLOGY;
                break;
            default:
                NEWS_API = NEWS_API_BUSINESS;
        }

        final GetNewsStoryI getNewsStoryI = (GetNewsStoryI) mContext;
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();


        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.GET, NEWS_API, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                Log.d("getNewApi", "News Articles API RESPONSE: " + stringResponse);
                try {
                    JSONObject obj = new JSONObject(stringResponse);
                    NewsChannel responseArticle = new NewsChannel();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    responseArticle = gson.fromJson(stringResponse, NewsChannel.class);
                    responseArticle.setType(type);
                    getNewsStoryI.responseArticles(responseArticle);

                } catch (JSONException e) {
                    e.printStackTrace();
                    errorMap.put("exception", e.getLocalizedMessage());
                    getNewsStoryI.errorRequestCS(errorMap);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", error.getLocalizedMessage());

                getNewsStoryI.errorRequestCS(errorMap);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    public interface GetNewsStoryI {
        public void responseCS(Map<String, String> response);

        public void responseArticles(NewsChannel feed);

        public void errorRequestCS(Map<String, String> response);
    }

    //------------------ Delete Group ------------
    public void deleteGroup(final String groupId) {

        final DeleteGroupI deleteGroupI = (DeleteGroupI) mContext;
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();


        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL+DELETE_GROUP, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                Log.d("deleteGroup", "Delete GROUP API RESPONSE: " + stringResponse);
                try {
                    JSONObject obj = new JSONObject(stringResponse);
                   responseMap.put("status",obj.getString("status") ) ;
                    deleteGroupI.responseDeleteG(responseMap);

                } catch (JSONException e) {
                    e.printStackTrace();
                    errorMap.put("exception", e.getLocalizedMessage());
                    deleteGroupI.errorRequestDeleteG(errorMap);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", Utils.getVolleyErrorString(error));
                deleteGroupI.errorRequestDeleteG(errorMap);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("groupid", groupId);
                return paramMap;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    public interface DeleteGroupI {
        public void responseDeleteG(Map<String, String> response);
        public void errorRequestDeleteG(Map<String, String> response);
    }


    //------------------ Get Group Members
    public void getGroupMembers(final String groupId) {

        final GetGroupMembersI getGroupMembersI = (GetGroupMembersI) mContext;
        final Map<String, String> errorMap = new HashMap<>();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + GET_GROUP_MEMBERS, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                Log.d("Get_Group_members", "onResponse: "+stringResponse);
                GroupMembers groupMembers = new GroupMembers();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                groupMembers = gson.fromJson(stringResponse, GroupMembers.class);
                getGroupMembersI.responseGetGroupMembers(groupMembers);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", Utils.getVolleyErrorString(error));
                getGroupMembersI.errorRequestGetGroupMembers(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("groupid", groupId);
                return paramsMap;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(true);
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }
    public interface GetGroupMembersI {
        public void responseGetGroupMembers(GroupMembers response);

        public void errorRequestGetGroupMembers(Map<String, String> response);

    }



    //------------------ Update Group
    public void updateGroup(final Map<String, String> params) {

        final UpdateGroupI updateGroupI = (UpdateGroupI) mContext;
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + UPDATE_GROUP, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                Log.d("Get_Group_members", "onResponse: "+stringResponse);
                try {
                    JSONObject obj = new JSONObject(stringResponse);
                    responseMap.put("status", obj.getString("status"));
                    updateGroupI.responseUpdateGroup(responseMap);
                } catch (JSONException e) {
                    e.printStackTrace();
                    errorMap.put("exception", e.getMessage());
                    updateGroupI.errorUpdateGroup(errorMap);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", Utils.getVolleyErrorString(error));
                updateGroupI.errorUpdateGroup(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }
    public interface UpdateGroupI {

        public void responseUpdateGroup(Map<String, String> response);

        public void errorUpdateGroup(Map<String, String> error);

    }

    //------------------ ForgetPassword
    public void forgetPassword(final String email) {

        final ForgetPasswordI forgetPasswordI = (ForgetPasswordI) mContext;
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + FORGET_PASSWORD, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                Log.d("Get_Group_members", "onResponse: "+stringResponse);
                try {
                    JSONObject obj = new JSONObject(stringResponse);
                    responseMap.put("status", obj.getString("status"));
                    forgetPasswordI.responseForgetPass(responseMap);
                } catch (JSONException e) {
                    e.printStackTrace();
                    errorMap.put("exception", e.getMessage());
                    forgetPasswordI.errorForgetPass(errorMap);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", Utils.getVolleyErrorString(error));
                forgetPasswordI.errorForgetPass(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }
    public interface ForgetPasswordI {

        public void responseForgetPass(Map<String, String> response);

        public void errorForgetPass(Map<String, String> error);

    }




}
