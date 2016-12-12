package com.zhjydy_doc.view.zjview;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Created by admin on 2016/8/1.
 */
public interface ActivityResultView
{

    void onActivityResult1(int requestCode, int resultCode, Intent data);
    void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
