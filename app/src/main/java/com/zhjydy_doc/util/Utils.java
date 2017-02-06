package com.zhjydy_doc.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhjydy_doc.app.ZhJDocApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liutao on 2016/6/20.
 */
public class Utils {

    public static String toString(Object ob) {

        if (ob == null) {
            return "";
        } else {
            return ob.toString();
        }
    }

    public static Boolean toBoolean(Object ob) {

        if (ob == null) {
            return false;
        } else {
            if (ob instanceof String) {
                String s = (String) ob;
                return "true".equals(s) || "True".equals(s) || "TRUE".equals(s) || "ture".equals(s);
            }
            if (ob instanceof Integer) {
                int i = (int) ob;
                return i > 0;
            }
            try {
                return (Boolean) ob;
            } catch (Exception e) {
                return false;
            }
        }

    }

    public static int toInteger(Object ob) {

        if (ob == null) {
            return -1;
        } else {
            try {
                return Integer.parseInt(toString(ob));
            } catch (NumberFormatException e) {
                return -1;
            }
        }
    }

    public static Long toLong(Object ob) {

        if (ob == null) {
            return -1L;
        } else {
            try {
                return Long.parseLong(toString(ob));
            } catch (NumberFormatException e) {
                return -1L;
            }
        }
    }

    public static String[] toStringArray(Object ob) {

        if (ob == null) {
            return null;
        } else {
            try {
                return (String[]) ob;
            } catch (Exception e) {
                return null;
            }
        }
    }


    public static List<Map<String, String>> toStringMapList(Object ob) {
        if (ob == null) {
            return null;
        } else {
            try {
                return (List<Map<String, String>>) ob;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static String mapListValueToString(Object ob, final String key) {
        List<Map<String, String>> list = toStringMapList(ob);
        String str = "";
        if (list == null) {
            return str;
        }
        for (int i = 0; i < list.size(); i++) {
            str += list.get(i).get(key);
            if (i < list.size() - 1) {
                str += ",";
            }
        }
        return str;
    }

    public static String strListToString(List<String> list) {
        String str = "";
        if (list == null || list.size() < 1) {
            return str;
        }
        for (int i = 0; i < list.size(); i++) {
            str += list.get(i);
            if (i < list.size() - 1) {
                str += ",";
            }
        }
        return str;
    }

    public static List<String> mapListValueToList(Object ob, final String key) {
        List<Map<String, String>> list = toStringMapList(ob);
        List<String> idList = new ArrayList<>();
        String str = "";
        if (list == null) {
            return idList;
        }
        for (int i = 0; i < list.size(); i++) {
            idList.add(list.get(i).get(key));
        }
        return idList;
    }

    public static String mapValue(Object ob, final String key) {
        String value = "";
        if (ob != null && ob instanceof Map) {
            Map map = (Map) ob;
            value = Utils.toString(map.get(key));
        }
        return value;
    }

    public static boolean isEmptyObject(Object ob) {
        if (ob == null) {
            return true;
        }
        if (ob instanceof List) {
            List l = (List) ob;
            return l.size() <= 0;
        }
        if (ob instanceof Map) {
            Map m = (Map) ob;
            return m.size() <= 0;
        }
        return TextUtils.isEmpty(toString(ob));
    }

    public static List<String> parseObjectToListString(Object object) {
        List<String> list = new ArrayList<>();
        if (object == null) {
            return list;
        }
        try {
            list = JSON.parseObject(Utils.toString(object), new TypeReference<List<String>>() {
            });
        } catch (Exception e) {

        }
        return list;
    }

    public static List<Map<String, Object>> parseObjectToListMapString(Object object) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (object == null) {
            return list;
        }
        try {
            list = JSON.parseObject(Utils.toString(object), new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {

        }
        return list;
    }



    public static Map<String, Object> parseObjectToMapString(Object object) {
        Map<String, Object> map = new HashMap<>();
        if (object == null) {
            return map;
        }
        if (object instanceof  Map) {
            try {
                map = ( Map<String, Object> ) object;
            }catch (Exception e) {

            }finally {
                return map;
            }
        }
        try {
            map = JSON.parseObject(Utils.toString(object), new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {

        }
        return map;
    }

    public List<Map<String, Object>> transDataToItems(Map<String, Object> data, List<Map<String, String>> formats) {
        List<Map<String, Object>> map = new ArrayList<>();
        for (int i = 0; i < formats.size(); i++) {
            Map<String, String> formate = formats.get(i);
            Map<String, Object> item = new HashMap<>();
            for (Map.Entry<String, String> entry : formate.entrySet()) {
                Object value = data.get(entry.getValue());
                item.put(entry.getKey(), value);
            }
            map.add(item);
        }
        return map;
    }


    public static String getItemValue(Object ob) {
        String data = "";
        if (ob instanceof String) {
            data = Utils.toString(ob);
        } else if (ob instanceof List) {
            List list = (List) ob;
            if (list.size() > 0 && list.get(0) instanceof Map) {
                data = Utils.mapListValueToString(ob, "name");
            }
        } else if (ob instanceof Map) {
            Map map = (Map) ob;
            data = Utils.toString(map.get("name"));
        }
        return data;
    }

    public static List<String> getItemValueList(Object ob) {
        List<String> data = new ArrayList<>();
        if (ob instanceof String) {
            data.add((String) ob);
        } else if (ob instanceof List) {
            List list = (List) ob;
            if (list.size() > 0 && list.get(0) instanceof Map) {
                data = Utils.mapListValueToList(ob, "id");
            }
        }
        return data;
    }

    public static String getItemId(Object ob) {
        String data = "";
        if (ob instanceof String) {
            data = Utils.toString(ob);
        } else if (ob instanceof List) {
            List list = (List) ob;
            if (list.size() > 0 && list.get(0) instanceof Map) {
                data = Utils.mapListValueToString(ob, "id");
            }
        } else if (ob instanceof Map) {
            Map map = (Map) ob;
            data = Utils.toString(map.get("id"));
        }
        return data;
    }


    /**
     * 根据图片的Uri获取图片的绝对路径(已经适配多种API)
     *
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion < 11) {
            // SDK < Api11
            return getRealPathFromUri_BelowApi11(context, uri);
        }
        if (sdkVersion < 19) {
            // SDK > 11 && SDK < 19
            return getRealPathFromUri_Api11To18(context, uri);
        }
        // SDK > 19
        return getRealPathFromUri_AboveApi19(context, uri);
    }

    /**
     * 适配api19以上,根据uri获取图片的绝对路径
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
        String filePath = null;
        String wholeID = DocumentsContract.getDocumentId(uri);

        // 使用':'分割
        String id = wholeID.split(":")[1];

        String[] projection = {MediaStore.Images.Media.DATA};
        String selection = MediaStore.Images.Media._ID + "=?";
        String[] selectionArgs = {id};

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, null);
        int columnIndex = cursor.getColumnIndex(projection[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    /**
     * 适配api11-api18,根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_Api11To18(Context context, Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};

        CursorLoader loader = new CursorLoader(context, uri, projection, null,
                null, null);
        Cursor cursor = loader.loadInBackground();

        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    /**
     * 适配api11以下(不包括api11),根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_BelowApi11(Context context, Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    @SuppressLint("NewApi")
    public static String getPath(final Uri uri) {
        final Context context = ZhJDocApplication.getInstance().getContext();
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }


    public static Map<String, String> getMapByValue(String ids[], String values[]) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            if (values.length <= i + 1) {
                map.put(id, values[i]);
            } else {
                map.put(id, "");
            }
        }
        return map;
    }

    public static int getCountOfString(String str, String target) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while (true) {
            index = str.indexOf(target, index + 1);
            if (index > 0) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    public static boolean isPhone(String inputText) {
        Pattern p = Pattern.compile("^((14[0-9])|(13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }

    public static String getListStrsAdd(List<Map<String, Object>> list, String key) {
        String ids = "";
        List<String> idList = new ArrayList<>();
        for (Map<String, Object> l : list) {
            idList.add(Utils.toString(l.get(key)));
        }

        for (int i = 0; i < idList.size(); i++) {
            ids += idList.get(i);
            if (i < idList.size() - 1) {
            }
            ids += ",";
        }
        return ids;
    }

    public static List<String> getListFromMapList(List<Map<String, Object>> list, String key) {
        List<String> idList = new ArrayList<>();
        for (Map<String, Object> l : list) {
            idList.add(Utils.toString(l.get(key)));
        }
        return idList;
    }


}
