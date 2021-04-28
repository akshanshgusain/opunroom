package com.factor8.opUndoor.UI.Main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.factor8.opUndoor.R
import com.factor8.opUndoor.UI.Auth.AuthActivity
import com.factor8.opUndoor.UI.BaseActivity
import com.factor8.opUndoor.UI.UICommunicationListener
import kotlinx.android.synthetic.main.activity_main2.*


class MainActivity : BaseActivity(), ViewPagerChangeListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        subscribeObservers()
    }

    fun subscribeObservers(){

        Log.d(TAG, "Mainactivity : subscribe Observers ")

        sessionManager.cachedToken.observe(this, Observer { authToken ->
            if (authToken == null || authToken.account_id == -1 || authToken.id == null) {
                Log.d(TAG, "Deleting Token")
                navAuthActivity()
                finish()
            }
        })

    }

    private fun navAuthActivity(){
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun displayProgressBar(bool: Boolean) {
        if(bool){
            progress_bar.visibility = View.VISIBLE
        }
        else{
            progress_bar.visibility = View.GONE
        }
    }



    override fun changeViewPagerPositionTo(position: Int) {
        val navHostFragment = supportFragmentManager.findFragmentByTag("main_fragment_tag") as NavHostFragment
        val viewPagerContainerFragment = navHostFragment.childFragmentManager.fragments[0] as ViewPagerContainer
        viewPagerContainerFragment.setViewPagerPosition(position)

    }
}


