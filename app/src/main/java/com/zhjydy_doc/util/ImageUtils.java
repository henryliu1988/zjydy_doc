package com.zhjydy_doc.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhjydy_doc.app.ZhJDocApplication;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.view.zjview.Displayer;

import java.io.File;

/**
 * Created by admin on 2016/8/8.
 */
public class ImageUtils {

    File cacheDir = StorageUtils.getOwnCacheDirectory(ZhJDocApplication.getInstance().getContext(), "zhj121_doc/imagecache");


    private DisplayImageOptions mSimpleOptions;
    private DisplayImageOptions mOverOptions;

    private ImageUtils() {
    }

    private static ImageUtils instance = null;

    public void initImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)  //缓存在内存中
                .cacheOnDisk(true)  //磁盘缓存
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(ZhJDocApplication.getInstance().getContext())
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                //  .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(5)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .diskCacheFileCount(10000)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheSize(1024 * 1024 * 1024)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(ZhJDocApplication.getInstance().getContext(), 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();//开始构建          ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().init(config);
    }


    private DisplayImageOptions getSimpleOptions() {
        if (mSimpleOptions == null) {
            mSimpleOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(new ColorDrawable(Color.parseColor("#f0f0f0"))) //设置图片在下载期间显示的图片
                    // .showImageForEmptyUri(R.drawable.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
                    //  .showImageOnFail(R.mipmap.login_logo)  //设置图片加载/解码过程中错误时候显示的图片
                    .resetViewBeforeLoading(true)
                    .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                    .cacheInMemory(true)  //缓存在内存中
                    .cacheOnDisk(true)
                    .build();//构建完成

        }
        return mSimpleOptions;
    }


    private DisplayImageOptions getOverOptions() {
        if (mOverOptions == null) {
            mOverOptions = new DisplayImageOptions.Builder()
                    //.showStubImage(R.drawable.ic_stub)
                    .showImageForEmptyUri(new ColorDrawable(Color.parseColor("#f0f0f0")))
                    .showImageOnFail(new ColorDrawable(Color.parseColor("#f0f0f0")))
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)   //设置图片的解码类型
                    .displayer(new Displayer(0))
                    .cacheInMemory(true)  //缓存在内存中
                    .cacheOnDisk(true)
                    .build();

        }
        return mOverOptions;
    }

    public static synchronized ImageUtils getInstance() {
        if (instance == null) {
            instance = new ImageUtils();
        }
        return instance;
    }

    public static void cleanMemotyCache() {
        ImageLoader.getInstance().clearMemoryCache();
    }
    public static void cleanDiskCache() {
        ImageLoader.getInstance().clearDiskCache();
    }
    /**
     * 从内存卡中异步加载本地图片
     *
     * @param uri
     * @param imageView
     */
    public void displayFromSDCard(String uri, ImageView imageView) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        ImageLoader.getInstance().displayImage("file://" + uri, imageView);
    }

    /**
     * 从assets文件夹中异步加载图片
     *
     * @param imageName 图片名称，带后缀的，例如：1.png
     * @param imageView
     */
    public void dispalyFromAssets(String imageName, ImageView imageView) {
        // String imageUri = "assets://image.png"; // from assets
        ImageLoader.getInstance().displayImage("assets://" + imageName,
                imageView);
    }


    public String transition(String imageUrl) {

        File f = new File(imageUrl);
        if (f.exists()) {
            //正常逻辑代码
        } else {
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
    public void displayFromDrawable(int imageId, ImageView imageView) {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        //ImageLoader.getInstance().displayImage("drawable://" + imageId,
        //       imageView);
        imageView.setImageResource(imageId);
    }

    public void displayFromDrawableOver(int imageId, ImageView imageView) {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        ImageLoader.getInstance().displayImage("drawable://" + imageId,
                imageView, getOverOptions());
    }

    public void displayFromSdcardOver(String uri, ImageView imageView) {
        ImageLoader.getInstance().displayImage("file://" + uri, imageView, getOverOptions());
    }

    public void displayFromRemoteOver(String imageurl, ImageView imageView) {
        String encodedUrl = WebKey.WEBKEY_BASE + transition(imageurl);
        ImageLoader.getInstance().displayImage(encodedUrl, imageView, getOverOptions());
    }

    public void displayFromRemote(String imageurl, ImageView imageView) {
        String encodedUrl = WebKey.WEBKEY_BASE + transition(imageurl);
        ImageLoader.getInstance().displayImage(encodedUrl, imageView, getSimpleOptions());
    }

    public File getAppImageFilePath(String name) {
        File file = getAppFilePath();
        if (file == null) {
            return null;
        }
        File sdDir = null;
        sdDir = new File(file
                + "/image");
        if (!sdDir.exists()) {
            sdDir.mkdir();
        }
        File imageFile = new File(file
                + name);
        return imageFile;
    }

    public File getAppFilePath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            // 这里可以修改为你的路径
            sdDir = new File(Environment.getExternalStorageDirectory()
                    + "/zhj121_doc");
            if (!sdDir.exists()) {
                sdDir.mkdir();
            }

        }
        return sdDir;
    }

}
