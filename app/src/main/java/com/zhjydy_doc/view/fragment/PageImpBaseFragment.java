package com.zhjydy_doc.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zhjydy_doc.app.ZhJDocApplication;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.view.activity.PagerImpActivity;
import com.zhjydy_doc.view.zjview.ActivityResultView;
import com.zhjydy_doc.view.zjview.zhToast;

import java.io.File;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
public abstract class PageImpBaseFragment extends StatedFragment
{

    public static final int SELECT_PICTURE = 1;
    public static final int SELECT_CAMER = 0;


    protected int getViewId()
    {
        Activity activity = getActivity();
        if (!(activity instanceof PagerImpActivity))
        {
            return -1;
        }
        return ((PagerImpActivity) activity).getFragmentViewId();
    }


    protected void gotoFragment(int key)
    {
        String tag = FragKey.FragMap.get(key);
        PageImpBaseFragment newFragment = PagerFragmentFactory.createFragment(key);
        if (!TextUtils.isEmpty(tag) && newFragment != null)
        {
            FragmentUtils.changeFragment(getActivity(), this, newFragment, tag, getViewId());
        }
    }
    /**
     * 收起软键盘
     */
    public static void closeKeyBoard(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) ZhJDocApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void gotoFragment(int key, Bundle bundle)
    {
        String tag = FragKey.FragMap.get(key);
        PageImpBaseFragment newFragment = PagerFragmentFactory.createFragment(key);
        if (!TextUtils.isEmpty(tag) && newFragment != null)
        {
            newFragment.setArguments(bundle);
            FragmentUtils.changeFragment(getActivity(), this, newFragment, tag, getViewId());
        }
    }

    protected void gotoFragment(int key, String info)
    {
        String tag = FragKey.FragMap.get(key);
        PageImpBaseFragment newFragment = PagerFragmentFactory.createFragment(key);
        if (!TextUtils.isEmpty(tag) && newFragment != null)
        {
            Bundle bundle = new Bundle();
            bundle.putString(IntentKey.FRAG_INFO,info);
            newFragment.setArguments(bundle);
            FragmentUtils.changeFragment(getActivity(), this, newFragment, tag, getViewId());
        }
    }
    protected void back()
    {
        Activity activity = getActivity();
        if (!(activity instanceof PagerImpActivity))
        {
            return;
        }
        FragmentUtils.back((PagerImpActivity) activity);
    }

    protected void back(int step)
    {
        while (step > 0)
        {
            back();
            step--;
        }
    }

    protected void back(int step, int[] fragkey)
    {
        while (step > 0)
        {
            back();
            step--;
        }
        Activity activity = getActivity();
        if (!(activity instanceof PagerImpActivity))
        {
            return;
        }
        FragmentUtils.refreshFragments((PagerImpActivity) activity, fragkey);
    }

    protected void back(int[] fragkey)
    {
        Activity activity = getActivity();
        if (!(activity instanceof PagerImpActivity))
        {
            return;
        }
        FragmentUtils.back((PagerImpActivity) activity);
        FragmentUtils.refreshFragments((PagerImpActivity) activity, fragkey);
    }

    protected void addOnActivityResultView(ActivityResultView view)
    {
        if (!(getActivity() instanceof PagerImpActivity))
        {
            return;
        }
        PagerImpActivity activity = (PagerImpActivity) getActivity();
        activity.addOnActivityResultView(view);
    }


    protected void selectImg()
    {
        final CharSequence[] items = {"拍照上传", "从相册选择"};
        new AlertDialog.Builder(getContext()).setTitle("选择图片来源")
                .setItems(items, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (which == SELECT_PICTURE)
                        {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                            {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_PICTURE);
                            } else
                            {
                                toGetLocalImage();
                            }
                        } else
                        {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                            {
                                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_CAMER);
                            } else
                            {
                                toGetCameraImage();
                            }
                            //
                        }
                    }
                }).create().show();
    }

    /**
     * 选择本地图片
     */
    public void toGetLocalImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (getActivity() != null)
        {
            getActivity().startActivityForResult(intent, SELECT_PICTURE);
        }

    }


    protected File mCameraPath = null;

    /**
     * 照相选择图片
     */
    public void toGetCameraImage()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        String time = DateUtil.getCurrent();
        File path = ImageUtils.getInstance().getAppImageFilePath(time + ".jpg");
        if (path == null)
        {
            zhToast.showToast("创建路径失败");
        }
        mCameraPath = path;
        if (getActivity() != null)
        {
            Uri uri = Uri.fromFile(mCameraPath);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            getActivity().startActivityForResult(intent, SELECT_CAMER);
        }
        // finish();
    }
}


