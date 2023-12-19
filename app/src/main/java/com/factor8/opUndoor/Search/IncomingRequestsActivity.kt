package com.factor8.opUndoor.Search

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.factor8.opUndoor.Network.Responses.GetAllFriendRequest
import com.factor8.opUndoor.Network.RestCalls
import com.factor8.opUndoor.R
import kotlinx.android.synthetic.main.activity_incoming_requests.*

class IncomingRequestsActivity : AppCompatActivity()
        ,RestCalls.GetAllFriendRequestsI
        , IncomingRequestAdapter.Interaction, RestCalls.AcceptFriendRequestI, RestCalls.RejectFriendRequestI{

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapterIncomingRequest: IncomingRequestAdapter
    private  val TAG = "IncomingRequestsActivit"

    private lateinit var getAllFriendRequest:GetAllFriendRequest;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incoming_requests)
        sharedPreferences = applicationContext.getSharedPreferences("LoginPreference", Context.MODE_PRIVATE)

        progress.visibility = VISIBLE
        val restCalls = RestCalls(this)
        restCalls.getAllFriendRequests(sharedPreferences.getString(ProjectConstants.PREF_KEY_ID,""))
    }

    override fun requestRejected(position: Int, friend: GetAllFriendRequest.RequestBean.UserBean?) {
        //  Toast.makeText(this, "Reject : ${friend!!.f_name} at pos: $position", Toast.LENGTH_SHORT).show()
        val restCalls = RestCalls(this)
        restCalls.rejectFriendRequest(sharedPreferences.getString(ProjectConstants.PREF_KEY_ID,""), friend!!.id)
    }

    override fun requestAccepted(position: Int, friend: GetAllFriendRequest.RequestBean.UserBean?) {
//        Toast.makeText(this, "Accept : ${friend!!.f_name} at pos: $position", Toast.LENGTH_SHORT).show()
        val restCalls = RestCalls(this)
        restCalls.acceptFriendRequest(sharedPreferences.getString(ProjectConstants.PREF_KEY_ID,""), friend!!.id)
    }


    //--------------------   REST INTERFACE
    override fun GetAllFriendRequestsIResponse(response: GetAllFriendRequest?) {
        progress.visibility = GONE
         val status = response?.let {
             it.status
         }
        if(status.equals("1")){
            //SetUp Recycler View
            recyclerView_friend_request.apply {
                layoutManager = LinearLayoutManager(this@IncomingRequestsActivity)
                response?.let{
                    getAllFriendRequest = it
                }
                adapterIncomingRequest = IncomingRequestAdapter(this@IncomingRequestsActivity, response)
                adapter = adapterIncomingRequest
            }
        }

    }

    override fun GetAllFriendRequestsIErrorRequest(response: MutableMap<String, String>?) {
        progress.visibility = GONE
        Log.d(TAG, "SearchUserProfileIErrorRequest Exception : ${response!!.get("exception")}")
        Log.d(TAG, "SearchUserProfileIErrorRequest Volley Error : ${response.get("VolleyError")}")
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()

    }

    //Accepts Friend Request
    override fun acceptFriendRequestErrorRequest(errorResponse: MutableMap<String, String>?) {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
    }

    override fun acceptFriendRequestResponse(Response: MutableMap<String, String>?) {
        Response?.let { response->
            val status = response["status"]
            if(status.equals("1")){
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Reject Friend Request
    override fun rejectFriendRequestErrorRequest(errorResponse: MutableMap<String, String>?) {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
    }

    override fun rejectFriendRequestResponse(Response: MutableMap<String, String>?) {
        Response?.let { response->
            val status = response["status"]
            if(status.equals("1")){
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }
        }
    }


}