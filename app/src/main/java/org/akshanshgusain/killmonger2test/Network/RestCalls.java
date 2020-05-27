package org.akshanshgusain.killmonger2test.Network;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.akshanshgusain.killmonger2test.LoginRegister.LoginActivity.mHeader;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_EMAIL_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_FIRST_NAME_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_LAST_NAME_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_NAME_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_PASSWORD_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_PICTURE_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_USERNAME_WORKSPACE;

public class RestCalls {
    private static final String BASE_URL = "http://dass.io/oppo/api/";
    private static final String REGISTER_URL = "user/register";
    private static final String CHECK_NETWORK = "user/checknetwork";
    private static final String CHECK_USERNAME = "user/checkusername";
    private static final String LOGIN = "user/login";
    private static final String UPDATE = "user/update";
    private static final String GET_FRIEND_LIST = "user/getuserfriend";
    private static final String CREATE_GROUP = "user/creategroup";
    private static final String FEED= "user/feed";
    private static final String CREATE_STORY= "user/createstory";

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

    //--------Register API Call

    public void registerUser(final Bundle bundle) {

        final RegisterUserI registerUserI = (RegisterUserI) mContext;
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + REGISTER_URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                Log.d(TAG, "Register API RESPONSE: " + stringResponse);
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
                errorMap.put("VolleyError", error.getLocalizedMessage());
                registerUserI.errorRequest(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("f_name", bundle.get(INTENT_FIRST_NAME_WORKSPACE).toString());
                paramsMap.put("l_name", bundle.get(INTENT_LAST_NAME_WORKSPACE).toString());
                paramsMap.put("username", bundle.get(INTENT_USERNAME_WORKSPACE).toString());
                paramsMap.put("email", bundle.get(INTENT_EMAIL_WORKSPACE).toString());
                paramsMap.put("password", bundle.get(INTENT_PASSWORD_WORKSPACE).toString());
                paramsMap.put("network", bundle.get(INTENT_NAME_WORKSPACE).toString());
                return paramsMap;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                params.put("picture", new DataPart("ProfilePicture.jpeg", bundle.getByteArray(INTENT_PICTURE_WORKSPACE)));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
                Log.d(TAG, "Register API RESPONSE: " + stringResponse);
                mHeader.setText(stringResponse);
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
                    errorMap.put("exception", e.toString());
                    loginUserI.errorRequest(errorMap);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 if(error instanceof TimeoutError){
                     errorMap.put("VolleyError Timeout", error.getLocalizedMessage());
                     mHeader.setText("Error Response:  VolleyError Timeout" );
                 }else if(error instanceof NoConnectionError){
                     errorMap.put("VolleyError NO Connection", error.getLocalizedMessage());
                     mHeader.setText("Error Response:  NoConnectionError");
                 }
                 else if(error instanceof AuthFailureError){
                     errorMap.put("VolleyError AuthFailureError", error.getLocalizedMessage());
                     mHeader.setText("Error Response: AuthFailureError " );
                 }
                 else if(error instanceof ServerError) {
                     errorMap.put("VolleyError ServerError", error.getLocalizedMessage());
                     mHeader.setText("Error Response:  ServerError" );
                 } else if (error instanceof NetworkError) {
                     errorMap.put("VolleyError NetworkError", error.getLocalizedMessage());
                     mHeader.setText("Error Response:  NetworkError");
                 } else if (error instanceof ParseError) {
                     errorMap.put("VolleyError ParseError", error.getLocalizedMessage());
                     mHeader.setText("Error Response:  ParseError");
                 }


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
                params.put("picture", new DataPart("ProfilePicture.jpeg", bundle.getByteArray(INTENT_PICTURE_WORKSPACE)));
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
        final List<Friends> responseFriendList = new ArrayList<>();
        final List<Groups> responseGroupList = new ArrayList<>();
        final Map<String, String> errorMap = new HashMap<>();

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

                    if (obj.getString("status").equals("1")) {
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
                                    , friendJSON.getString("picture"),null
                            );
                            responseFriendList.add(friend);
                        }
                        for(int j=0;j<groupsArray.length();j++){
                            JSONObject groupJSON = groupsArray.getJSONObject(j);
                            Groups group = new Groups(
                                    groupJSON.getString("id")
                                    ,groupJSON.getString("grouptitle")
                                    ,groupJSON.getString("groupadminid")
                                    ,null);
                            responseGroupList.add(group);
                        }
                    }
                   getFriendsListI.responseList(responseFriendList);
                    getFriendsListI.responseList(responseFriendList);
                    getFriendsListI.responseListGroups(responseGroupList);
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
        final Map<String, String> responseMap = new HashMap<>();
        final Map<String, String> errorMap = new HashMap<>();
        final List<Groups> groups = new ArrayList<>();
        final List<Friends> storiesFriends = new ArrayList<>();

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, BASE_URL + FEED, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String stringResponse = new String(response.data);
                Log.d("getFeedResponse", "Update Profile API RESPONSE: " + stringResponse);
                try {
                    JSONObject obj = new JSONObject(stringResponse);
                    if(obj.getString("status").equals("1")){
                        //Parsing GROUPS--------------------------------------------------
                         JSONArray groupsJSONArray = obj.getJSONArray("groups");
                         for(int i=0; i<groupsJSONArray.length();i++){
                             JSONObject singleGroup = groupsJSONArray.getJSONObject(i);

                             List<String> stories=new ArrayList<>();

                             JSONArray storiesPicturesArray = singleGroup.getJSONArray("grouppictures");
                                   for(int k=0;k<storiesPicturesArray.length();k++){
                                        JSONObject story = storiesPicturesArray.getJSONObject(k);
                                         stories.add(story.getString("groupstory"));
                                   }
                             Groups group = new Groups(
                             singleGroup.getString("id")
                                     ,singleGroup.getString("grouptitle")
                                     ,singleGroup.getString("adminname")
                                     ,stories);

                             group.setGroupImage(singleGroup.getString("adminpicture"));
                             groups.add(group);
                         }
                        //Parsing GROUPS--------------------------------------------------
                        /*-------Parsing FRIENDS----------------------------------------*/
                        JSONArray storiesJSONArray = obj.getJSONArray("friends");
                        for(int j=0;j<storiesJSONArray.length();j++){
                            JSONObject singlesStory = storiesJSONArray.getJSONObject(j);
                            ArrayList<String> stories = new ArrayList<>();

                            JSONArray storiesPIctureArray = singlesStory.getJSONArray("storypicture");
                                    for(int k=0;k<storiesPIctureArray.length();k++){
                                        JSONObject story = storiesPIctureArray.getJSONObject(k);
                                        stories.add(story.getString("storypicture"));
                                    }
                            Friends friend = new Friends(
                                    singlesStory.getString("id"),
                                    singlesStory.getString("f_name"),
                                    singlesStory.getString("l_name"),
                                    singlesStory.getString("username"),
                                    singlesStory.getString("email"),
                                    singlesStory.getString("password"),
                                    singlesStory.getString("network"),
                                    singlesStory.getString("picture"),
                                    stories);
                                storiesFriends.add(friend);
                        }
                        /*-------Parsing FRIENDS----------------------------------------*/
                    }
                    feedI.feedResponse2(responseMap);
                    feedI.feedResponseGroups2(groups);
                    feedI.feedResponseStories(storiesFriends);
                } catch (JSONException e) {
                    errorMap.put("exception", e.toString());
                    feedI.feedErrorRequest2(errorMap);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMap.put("VolleyError", error.getLocalizedMessage());
                feedI.feedErrorRequest2(errorMap);
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
        public void feedResponse2(Map<String, String> response);
        public void feedResponseStories(List<Friends> stories);
         public void feedResponseGroups2(List<Groups> groups);
        public void feedErrorRequest2(Map<String, String> response);

    }

    //------------------ Create Story ------------
    public void createStory(final Bundle bundle) {

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
                errorMap.put("VolleyError", error.getLocalizedMessage());
                createGroupI.errorRequestCS(errorMap);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("userid", Objects.requireNonNull(bundle.getString("userid")));
                paramsMap.put("selfid", Objects.requireNonNull(bundle.getString("selfid")));
                paramsMap.put("friends", Objects.requireNonNull(bundle.getString("friends")));
                paramsMap.put("groups", Objects.requireNonNull(bundle.getString("groups")));
                return paramsMap;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                params.put("picture", new DataPart("StoryPicture.jpeg", bundle.getByteArray(INTENT_PICTURE_WORKSPACE)));
                return params;
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
}
