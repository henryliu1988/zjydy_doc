package com.zhjydy_doc.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import com.zhjydy_doc.app.ZhJDocApplication;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.view.activity.LoginActivity;
import com.zhjydy_doc.view.activity.MainTabsActivity;

import java.util.Stack;

/**
 * Created by admin on 2016/8/2.
 */
public class ActivityUtils
{
    private static Stack<Activity> activityStack = new Stack<Activity>();


    //添加Activity到容器中
    public static void addActivity(Activity activity)
    {
        activityStack.push(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity()
    {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishCurrentActivity()
    {
        Activity activity = activityStack.pop();
        activity.finish();
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity)
    {
        if (activity != null)
        {
            activityStack.remove(activity);
            if (!activity.isFinishing())
            {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls)
    {
        for (Activity activity : activityStack)
        {
            if (activity.getClass().equals(cls))
            {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity()
    {
        for (Activity activity : activityStack)
        {
            if (activity != null)
            {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    public static void finishActivityExceptOne(Class<?> cls) {
        for (Activity activity : activityStack)
        {
            if (!activity.getClass().equals(cls))
            {
                finishActivity(activity);
            }
        }
    }
    /**
     * 退出应用程序
     */
    public static void AppExit(Context context)
    {
        try
        {
            finishAllActivity();
            ActivityManager manager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void showHome(Activity context, boolean finish)
    {
        Intent intent = new Intent(context, MainTabsActivity.class);
        context.startActivity(intent);
        if (finish)
        {
            context.finish();
        }
    }

    public static void showLogin(Activity context, boolean finish)
    {
        if (finish)
        {
            finishAllActivity();
        }
        Intent intent = new Intent(ZhJDocApplication.getInstance().getContext(), LoginActivity.class);
        context.startActivity(intent);
    }

    public static void transActivity(Activity context1, Class des, boolean finish)
    {
        Intent intent = new Intent(context1, des);
        context1.startActivity(intent);
        if (finish)
        {
            context1.finish();
        }
    }
    public static void transActivity(Activity context1, Class des, Bundle bundle, boolean finish)
    {
        Intent intent = new Intent(context1, des);
        intent.putExtra(IntentKey.INTENT_BUNDLE, bundle);
        context1.startActivity(intent);
        if (finish)
        {
            context1.finish();
        }
    }

    public static void transToFragPagerActivity(Activity context1, Class des,int key,String info,boolean finish) {
        Bundle bundle = new Bundle();
        bundle.putInt(IntentKey.FRAG_KEY, key);
        bundle.putString(IntentKey.FRAG_INFO, info);
        transActivity(context1,des,bundle,finish);
    }
    public static View getRootView(Activity context)
    {
        return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
    }

    public static void refreshFragment(Class activityCl,Class FragmentCl) {
        Activity activity = null;
        for (Activity ac : activityStack)
        {
            if (ac.getClass().equals(activityCl))
            {
                activity = ac;
            }
        }
        if (activity == null && !(activity instanceof FragmentActivity)) {
            return;
        }

    }


}
