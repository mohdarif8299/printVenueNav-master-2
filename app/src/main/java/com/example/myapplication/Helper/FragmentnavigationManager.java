package com.example.myapplication.Helper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.Fragment.FragmentContent;
import com.example.myapplication.Interface.NavigationManager;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class FragmentnavigationManager implements NavigationManager {
    private static FragmentnavigationManager mInstance;
    private FragmentManager mFragmentManager;
    private MainActivity mainActivity;
    public static FragmentnavigationManager getmInstance(MainActivity mainActivity){
        if (mInstance == null)
                mInstance = new FragmentnavigationManager();
            mInstance.configure(mainActivity);
            return mInstance;
    }

    private void configure(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        mFragmentManager = mainActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragment(String tittle) {
        showFragment(FragmentContent.newInstance(tittle),false);
    }

    private void showFragment(Fragment fragmentContent, boolean b){
        FragmentManager fm = mFragmentManager;
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.container,fragmentContent);
       ft.addToBackStack(null);
        if (b || !BuildConfig.DEBUG)
            ft.commitAllowingStateLoss();
        else
            ft.commit();
        fm.executePendingTransactions();
    }
}
