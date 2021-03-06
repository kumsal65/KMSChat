package com.kumsal.kmschat;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionPagerAdapter extends FragmentPagerAdapter {


    public SectionPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 2:
                RequestFragment requestFragment = new RequestFragment();
                return requestFragment;
            case 0:
                ChatFragments chatFragments = new ChatFragments();
                return chatFragments;
            case 1:
                FrendsFragment frendsFragment = new FrendsFragment();
                return frendsFragment;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return 3;
    }
    public CharSequence getPageTitle(int position){
        switch (position) {
            case 2:
                return "REQUEST";
            case 0:
                return "CHAT";
            case 1:
                return "FRIENDS";
            default:
                return null;
        }

    }
}
