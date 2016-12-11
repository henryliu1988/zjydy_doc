package com.zhjydy_doc.view.zjview;

import android.widget.Toast;

import com.zhjydy_doc.app.ZhJDocApplication;


/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class zhToast {

    public static void showToast(String text)
    {
        Toast.makeText(ZhJDocApplication.getInstance().getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
