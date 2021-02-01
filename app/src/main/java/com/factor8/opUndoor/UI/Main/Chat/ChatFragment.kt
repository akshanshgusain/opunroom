package com.factor8.opUndoor.UI.Main.Chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.factor8.opUndoor.R

class ChatFragment : BaseChatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat2, container, false)
    }

    public fun print(){
        Log.e(TAG, "print: Chat Fragment Fired" )
    }

}