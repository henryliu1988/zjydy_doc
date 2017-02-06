package com.zhjydy_doc.model.data;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class AppDataManager {


    private static AppDataManager appdata;

    public AppDataManager() {
    }


    public static AppDataManager getInstance() {
        if (appdata == null) {
            appdata = new AppDataManager();
        }
        return appdata;
    }

    public void initData() {
        DicData.getInstance().init();
    }

    public void initLoginSucData() {
        MsgData.getInstance().loadData();
        UserData.getInstance().loadUserData();
        ExpertData.getInstance().loadData();
    }


}
