package org.akshanshgusain.killmonger2test.SwipableViews;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.akshanshgusain.killmonger2test.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SectionPagerAdapter extends FragmentStateAdapter {
    private final List<Fragment> mFragmentList=new ArrayList<>();
    private final List<String> mFragmenTitleList=new ArrayList<>();

    public SectionPagerAdapter(MainActivity fm) {
        super(fm);
    }


    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmenTitleList.add(title);

    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }



}
