package com.factor8.opUndoor.Search

import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.factor8.opUndoor.Network.RestCalls
import com.factor8.opUndoor.Network.Responses.SearchResult
import com.factor8.opUndoor.Network.Responses.SearchResultUserProfile
import com.factor8.opUndoor.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_user_profile.*

class SearchUserProfileActivity : AppCompatActivity(), RestCalls.SearchUserProfileI, RestCalls.SendFriendRequestI,RestCalls.RejectFriendRequestI {

    private lateinit var userObj: SearchResult.UserBean
    private val gson = Gson()
    private lateinit var sharedPreferences: SharedPreferences

    private val TAG = "SearchUserProfileActivi"

    private var isFriendStatus = -1  //0 =not a colleague    1 = Colleague     2 = Request Pending

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_user_profile)
        sharedPreferences = applicationContext.getSharedPreferences("LoginPreference", Context.MODE_PRIVATE)
        userObj = gson.fromJson(intent.getStringExtra("USER"), SearchResult.UserBean()::class.java)


        imageView_back.setOnClickListener {
            onBackPressed()
        }


        button_colleague_request.setOnClickListener {

            when (isFriendStatus) {
                0 -> {
                    sendAColleagueRequest()
                }
                1 -> {
                    sendARemoveColleagueRequest()
                }
                2 -> {
                    Toast.makeText(this, "Request already sent", Toast.LENGTH_SHORT).show()
                }
            }
        }



        fillinfo()

    }

    private fun isUserIsSelf() {
        val currentUserId = sharedPreferences.getString(PREF_KEY_ID,"")
        if(currentUserId.equals(userObj.id)){
            //User is same hide all Buttons
            button_colleague_request.visibility = GONE
            button_subscribe_request.visibility = GONE
        }
    }


    private fun sendAColleagueRequest() {
        val restCalls = RestCalls(this)
        restCalls.sendFriendRequest(sharedPreferences.getString(PREF_KEY_ID,""), userObj.id)
    }

    private fun sendARemoveColleagueRequest() {
        val restCalls = RestCalls(this)
        restCalls.rejectFriendRequest(sharedPreferences.getString(PREF_KEY_ID,""), userObj.id)
    }

    private fun fillinfo() {
        val fullName = userObj.f_name+" "+userObj.l_name
        textView_full_name.setText(fullName)
        textView_username.setText(userObj.username)
        textView_profession.setText(userObj.profession)
        textView_experience.setText(userObj.experience)
        Glide.with(this).load(PROFILE_IMAGES +userObj.picture).into(imageView_dp)

        //Get Other info via rest call
        val restCalls = RestCalls(this)
        restCalls.getSearchUserProfile(sharedPreferences.getString(PREF_KEY_ID,""), userObj.id)
        progress.visibility = VISIBLE
    }


    override fun SearchUserProfileIErrorRequest(response: MutableMap<String, String>?) {
        progress.visibility = GONE
        Log.d(TAG, "SearchUserProfileIErrorRequest Exception : ${response!!.get("exception")}")
        Log.d(TAG, "SearchUserProfileIErrorRequest Volley Error : ${response.get("VolleyError")}")
    }

    override fun SearchUserProfileIResponse(response: SearchResultUserProfile?) {
        progress.visibility = GONE

        //SET COMPAMANY DETAILS
        response?.let {

            textView_colleague_count.text = it.collegue.toString()
            textView_subscribers_count.text = it.subscriber.toString()
            textView_subscription_count.text = it.subscriptions.toString()

            val company = it.company[0]
            Glide.with(this).load(COMPANY_IMAGES +company.profile_picture).into(imageView_banner_image)
            Glide.with(this).load(COMPANY_IMAGES +company.display_picture).into(imageView_company_dp)
            textView_comapny_full_name.text = company.name
            cardView_company.visibility = VISIBLE
            textView_company.visibility = VISIBLE
            button_colleague_request.visibility = VISIBLE

             //IF THE account is private hide follow button
            it.user?.let {user ->
                if(!user.privacy.equals("2")){
                    button_subscribe_request.visibility = VISIBLE
                }
            }

            //IF Already a colleague disable request button
            it.is_friend?.let { isFriend->
                 if(isFriend == 0){
                     //Not a Colleague
                     changeButtonToSendFriendRequest()
                     isFriendStatus = 0

                 }else if(isFriend == 1){
                     //Already a Colleague
                     changeButtonToUnFriend()
                     isFriendStatus = 1

                 }else if(isFriend == 2){
                     //Colleague request Pending
                     changeButtonToRequestPending()
                     isFriendStatus = 2
                 }
            }

            //If already subscribed disable subscribe button
            it.is_subscribe?.let {isSubscribed ->
                if(isSubscribed == 1){
                    //Already Subscribed
                    button_subscribe_request.setBackgroundColor(resources.getColor(R.color.colorGrey))
                    button_subscribe_request.setTextColor(resources.getColor(R.color.colorBlack))
                }else if(isSubscribed ==0){
                    //Not Subscribe
                    button_subscribe_request.setBackgroundColor(resources.getColor(R.color.colorAccent))
                    button_subscribe_request.setTextColor(resources.getColor(R.color.colorWhite))
                }
            }

        }

        //Ceck if currentuser is the searched user
        isUserIsSelf();

    }

    //SendFriend Request Responses
    override fun sendFriendRequestResponse(Response: MutableMap<String, String>?) {
            val status = Response?.let { response ->
                        response["status"]
                    }

            if(status.equals("1")){
                //Request Sent
                //Change Button to Request Sent Button
                changeButtonToRequestPending()
            }
    }

    override fun sendFriendRequestErrorRequest(errorResponse: MutableMap<String, String>?) {
        Log.d(TAG, "sendFriendRequestErrorRequest: Volley Error ${errorResponse?.let {
            it["VolleyError"]
        }
        }}")
        Log.d(TAG, "sendFriendRequestErrorRequest: Exception ${errorResponse?.let{
            it["exception"]
        }}")
        Toast.makeText(this, "Something Went wrong", Toast.LENGTH_SHORT).show()
    }


    fun changeButtonToRequestPending(){
        button_colleague_request.isClickable = false
        //button_colleague_request.setBackgroundColor(resources.getColor(R.color.colorGrey))
        button_colleague_request.background.setColorFilter(ContextCompat.getColor(this, R.color.colorGrey), PorterDuff.Mode.MULTIPLY);
        button_colleague_request.setTextColor(resources.getColor(R.color.colorBlack))
        button_colleague_request.text = "Colleague Request Pending"
    }

    fun changeButtonToUnFriend(){
        button_colleague_request.isClickable = true
        button_colleague_request.background.setColorFilter(ContextCompat.getColor(this, R.color.colorGrey), PorterDuff.Mode.MULTIPLY);
        button_colleague_request.setTextColor(resources.getColor(R.color.colorBlack))
        button_colleague_request.text = "Remove colleague"
    }

    fun changeButtonToSendFriendRequest(){
        button_colleague_request.isClickable = true
        button_colleague_request.background.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        button_colleague_request.setTextColor(resources.getColor(R.color.colorWhite))
        button_colleague_request.text = "Send colleague request"
    }

    fun changeButtonToSubscribe(){
        button_subscribe_request.isClickable = false
        button_colleague_request.background.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        button_subscribe_request.setTextColor(resources.getColor(R.color.colorWhite))
        button_subscribe_request.text = "Subscribe"
    }

    fun changeButtonToUnSubscribe(){
        button_subscribe_request.isClickable = false
        button_colleague_request.background.setColorFilter(ContextCompat.getColor(this, R.color.colorGrey), PorterDuff.Mode.MULTIPLY);
        button_subscribe_request.setTextColor(resources.getColor(R.color.colorBlack))
        button_subscribe_request.text = "UnSubscribe"
    }

    // UnFriend User API Responses
    override fun rejectFriendRequestErrorRequest(errorResponse: MutableMap<String, String>?) {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
    }

    override fun rejectFriendRequestResponse(Response: MutableMap<String, String>?) {
        Response?.let { response->
            val status = response["status"]
            if(status.equals("1")){
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                changeButtonToSendFriendRequest()
            }
        }
    }


}