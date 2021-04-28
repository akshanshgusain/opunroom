package com.factor8.opUndoor.UI.Main.Feed

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.factor8.opUndoor.R
import com.factor8.opUndoor.UI.Main.ViewPagerChangeListener
import com.factor8.opUndoor.Util.Constants
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {

    val TAG: String = "AppDebug"

    lateinit var viewPagerChangerListener: ViewPagerChangeListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            viewPagerChangerListener = context as ViewPagerChangeListener
        }catch(e: ClassCastException){
            Log.e(TAG, "$context must implement UICommunicationListener" )
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView_cancel_button.setOnClickListener {
                navigateToCameraFragment()
        }

        imageView_dp.setOnClickListener{
            navigateToAccountFragment()
        }
    }

    private fun navigateToCameraFragment(){
        viewPagerChangerListener.changeViewPagerPositionTo(Constants.CAMERA_FRAGMENT_POSITION)
    }

    private fun navigateToAccountFragment(){
        findNavController().navigate(R.id.action_viewPagerContainer_to_accountFragment)
    }
}