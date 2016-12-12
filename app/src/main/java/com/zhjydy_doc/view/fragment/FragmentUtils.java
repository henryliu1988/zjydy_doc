package com.zhjydy_doc.view.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
public class FragmentUtils {

    public static void addNewFragment(FragmentActivity context, Fragment fragment, String tag, int containerId) {
        FragmentManager fManager = context.getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.add(containerId, fragment, tag);
        fTransaction.addToBackStack(tag);
        fTransaction.commit();
    }

    public static void changeFragment(FragmentActivity context, Fragment current, Fragment fragment, String tag, int viewId) {
        FragmentManager fManager = context.getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        transaction.hide(current);
        transaction.add(viewId, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }


    public static void back(FragmentActivity context) {
        FragmentManager fManager = context.getSupportFragmentManager();
        if (fManager.getBackStackEntryCount() > 1) {
            fManager.popBackStack();
        } else {
            context.finish();
        }
    }


    public static void refreshFragments(FragmentActivity context, int[] fragkey) {
        FragmentManager fManager = context.getSupportFragmentManager();
        if (fragkey.length > 0) {
            for (int i = 0 ; i < fragkey.length; i ++) {
                String key = FragKey.FragMap.get(fragkey[i]);
                if (!TextUtils.isEmpty(key)) {
                    Fragment fragment = fManager.findFragmentByTag(key);
                    if (fragment != null && fragment instanceof StatedFragment) {
                        StatedFragment statedFragment = (StatedFragment)fragment;
                        statedFragment.refreshView();
                    }
                }
            }
        }
    }

}
