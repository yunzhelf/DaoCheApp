package com.yifactory.daocheapp.utils;

import android.app.Activity;

import java.util.Stack;

public class AppDavikActivityMgr {

    //存储ActivityStack
    private static Stack<Activity> activityStack = new Stack<Activity>();

    //单例模式
    private static AppDavikActivityMgr instance;


    /**
     * 单列堆栈集合对象
     *
     * @return AppDavikActivityMgr 单利堆栈集合对象
     */
    public static AppDavikActivityMgr getScreenManager() {
        if (instance == null) {
            synchronized (AppDavikActivityMgr.class) {
                if (instance == null) {
                    instance = new AppDavikActivityMgr();
                }
            }
        }
        return instance;
    }


    /**
     * 堆栈中销毁并移除
     *
     * @param activity 指定Act对象
     */
    public void removeActivity(Activity activity) {
        if (null != activity) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    /**
     * 栈中销毁并移除所有Act对象
     */
    public void removeAllActivity() {
        if (null != activityStack && activityStack.size() > 0) {
            //创建临时集合对象
            Stack<Activity> activityTemp = new Stack<Activity>();
            for (Activity activity : activityStack) {
                if (null != activity) {
                    //添加到临时集合中
                    activityTemp.add(activity);
                    //结束Activity
                    activity.finish();
                }
            }
            activityStack.removeAll(activityTemp);
        }
        System.gc();
        System.exit(0);
    }

    public void removeMainActivity() {
        if (null != activityStack && activityStack.size() > 0) {
            //创建临时集合对象
            Stack<Activity> activityTemp = new Stack<Activity>();
            for (Activity activity : activityStack) {
                if (null != activity) {
                    //添加到临时集合中
                    activityTemp.add(activity);
                    //结束Activity
                    activity.finish();
                }
            }
            activityStack.removeAll(activityTemp);
        }
        System.gc();
    }


    /**
     * 获取当前Act对象
     *
     * @return Activity 当前act
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (!activityStack.empty()) {
            activity = activityStack.lastElement();
        }
        return activity;
    }


    /**
     * 获得当前Act的类名
     *
     * @return String
     */
    public String getCurrentActivityName() {
        String actSimpleName = "";
        if (!activityStack.empty()) {
            actSimpleName = activityStack.lastElement().getClass().getSimpleName();
        }
        return actSimpleName;
    }


    /**
     * 将Act纳入推栈集合中
     *
     * @param activity Act对象
     */
    public void addActivity(Activity activity) {
        if (null == activityStack) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }


    /**
     * 退出栈中所有Activity
     *
     * @param cls
     * @return void
     */
    public void exitApp(Class<?> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (null == activity) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            removeActivity(activity);
        }
        System.gc();
        System.exit(0);
    }

}
