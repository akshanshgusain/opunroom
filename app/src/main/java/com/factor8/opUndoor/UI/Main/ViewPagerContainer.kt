package com.factor8.opUndoor.UI.Main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.factor8.opUndoor.R
import com.factor8.opUndoor.SwipableViews.SectionPagerAdapter
import com.factor8.opUndoor.UI.Main.Camera.CameraFragment
import com.factor8.opUndoor.UI.Main.Chat.ChatFragment
import com.factor8.opUndoor.UI.Main.Feed.FeedFragment
import com.factor8.opUndoor.Util.Constants
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_view_pager_container.*
import javax.inject.Inject


class ViewPagerContainer : DaggerFragment() {




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_pager_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
    }

    fun setUpViewPager(){
        viewPager_main.offscreenPageLimit = 3;
        val sectionPagerAdapter = SectionPagerAdapterK(requireActivity().supportFragmentManager, lifecycle)
        sectionPagerAdapter.addFragments(ChatFragment(), "ChatFragment")
        sectionPagerAdapter.addFragments(CameraFragment(), "CameraFragment")
        sectionPagerAdapter.addFragments(FeedFragment(), "FeedFragment")
        viewPager_main.adapter = sectionPagerAdapter
        viewPager_main.currentItem = Constants.CAMERA_FRAGMENT_POSITION

    }

    fun setViewPagerPosition(position: Int){
        viewPager_main.currentItem = position
    }
}