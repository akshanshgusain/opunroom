package com.factor8.opUndoor.SwipableViews;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;
// FragmentStateAdapter
public class SectionPagerAdapter extends  FragmentStateAdapter{
    private final List<Fragment> mFragmentList=new ArrayList<>();
    private final List<String> mFragmenTitleList=new ArrayList<>();

    public SectionPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmenTitleList.add(title);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }




}
