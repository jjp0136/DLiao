package test.bwie.com.dliao.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import test.bwie.com.dliao.fragments.TabFragmentCondition;
import test.bwie.com.dliao.fragments.TabFragmentEmil;
import test.bwie.com.dliao.fragments.TabFragmentFind;
import test.bwie.com.dliao.fragments.TabFragmentMe;

/**
 * Created by lenovo-pc on 2017/7/12.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TabFragmentFind();
                break;
            case 1:
                fragment = new TabFragmentCondition();
                break;
            case 2:
                fragment = new TabFragmentEmil();
                break;
            case 3:
                fragment = new TabFragmentMe();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
