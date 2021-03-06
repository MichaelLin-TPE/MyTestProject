package com.example.climbtogether.home_page;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.climbtogether.disscuss_fragment.DiscussFragment;
import com.example.climbtogether.equipment_fragment.EquipmentFragment;
import com.example.climbtogether.home_fragment.HomeFragment;
import com.example.climbtogether.mountain_fragment.MountainFragment;

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return HomeFragment.newInstance();
        }else if (position == 1){
            return MountainFragment.newInstance();
        }else if (position == 2){
            return EquipmentFragment.newInstance();
        }else {
            return DiscussFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
