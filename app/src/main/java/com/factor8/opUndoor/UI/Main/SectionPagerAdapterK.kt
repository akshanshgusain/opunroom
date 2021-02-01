package com.factor8.opUndoor.UI.Main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapterK(fa: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fa, lifecycle){
    var fragmentList = ArrayList<Fragment>()
    var fragmentTitleList = ArrayList<String>()

    override fun getItemCount(): Int {
            return fragmentList.size
    }

    fun addFragments(fragment: Fragment, title: String?){
        fragmentList.add(fragment)
        title?.let {
            fragmentTitleList.add(it)
        }

    }

    override fun createFragment(position: Int): Fragment {
            return fragmentList.get(position)
    }

}