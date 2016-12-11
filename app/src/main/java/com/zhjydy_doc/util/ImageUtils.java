package com.zhjydy_doc.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zhjydy_doc.app.ZhJDocApplication;
import com.zhjydy_doc.model.net.WebKey;

import java.io.File;

/**
 * Created by admin on 2016/8/8.
 */
public class ImageUtils
{

    private ImageUtils()
    {
    }

    private static ImageUtils instance = null;

    public void initImageLoader()
    {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory()  //缓存在内存中
                .cacheOnDisc()  //磁盘缓存
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ZhJDocApplication.getInstance().getContext())
                .defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

    private DisplayImageOptions getSimpleOptions()
    {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(new ColorDrawable(Color.parseColor("#f0f0f0"))) //设置图片在下载期间显示的图片
                // .showImageForEmptyUri(R.drawable.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
                //  .showImageOnFail(R.mipmap.login_logo)  //设置图片加载/解码过程中错误时候显示的图片
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .build();//构建完成
        return options;
    }

    public static synchronized ImageUtils getInstance()
    {
        if (instance == null)
        {
            instance = new ImageUtils();
        }
        return instance;
    }

    /**
     * 从内存卡中异步加载本地图片
     *
     * @param uri
     * @param imageView
     */
    public void displayFromSDCard(String uri, ImageView imageView)
    {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        ImageLoader.getInstance().displayImage("file://" + uri, imageView);
    }

    /**
     * 从assets文件夹中异步加载图片
     *
     * @param imageName 图片名称，带后缀的，例如：1.png
     * @param imageView
     */
    public void dispalyFromAssets(String imageName, ImageView imageView)
    {
        // String imageUri = "assets://image.png"; // from assets
        ImageLoader.getInstance().displayImage("assets://" + imageName,
                imageView);
    }




    public String transition(String imageUrl)
    {

        File f = new File(imageUrl);
        if (f.exists())
        {
            //正常逻辑代码
        } else
        {
            //处理中文路径
            imageUrl = Uri.encode(imageUrl);
        }
        imageUrl = imageUrl.replace("%3A", ":");
        imageUrl = imageUrl.replace("%2F", "/");
        return imageUrl;
    }

    /**
     * 从drawable中异步加载本地图片
     *
     * @param imageId
     * @param imageView
     */
    public void displayFromDrawable(int imageId, ImageView imageView)
    {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        ImageLoader.getInstance().displayImage("drawable://" + imageId,
                imageView);
    }

    public void displayFromRemote(String imageurl, ImageView imageView)
    {
        String encodedUrl = WebKey.WEBKEY_BASE + transition(imageurl);
        ImageLoader.getInstance().displayImage(encodedUrl, imageView, getSimpleOptions());
    }

    public File getSimsImageFilePath(String name)
    {
        File file = getSimsAppFilePath();
        if (file == null)
        {
            return null;
        }
        File sdDir = null;
        sdDir = new File(file
                + "/image");
        if (!sdDir.exists())
        {
            sdDir.mkdir();
        }
        File imageFile = new File(file
                + name);
        return imageFile;
    }

    public File getSimsAppFilePath()
    {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist)
        {
            // 这里可以修改为你的路径
            sdDir = new File(Environment.getExternalStorageDirectory()
                    + "/Sims");
            if (!sdDir.exists())
            {
                sdDir.mkdir();
            }

        }
        return sdDir;
    }


}
