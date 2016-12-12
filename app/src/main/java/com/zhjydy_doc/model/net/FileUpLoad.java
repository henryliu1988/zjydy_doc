package com.zhjydy_doc.model.net;


import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.util.ViewKey;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2016/9/6.
 */
public class FileUpLoad
{

    private static FileUpLoad fileUpload;

    public FileUpLoad()
    {
    }

    public static FileUpLoad getInstance()
    {
        if (fileUpload == null)
        {
            fileUpload = new FileUpLoad();
        }
        return fileUpload;
    }

    private static final int TIME_OUT = 10 * 1000;   //超时时间
    private static final String CHARSET = "UTF-8"; //设置编码

    public static Observable<List<Map<String,Object>>> uploadFiles(List<Map<String, Object>> files) {
        if (files == null &&files.size() < 1) {
            List<Map<String,Object>> ids = new ArrayList<>();
            return Observable.just(ids);
        }
        List<Map<String, Object>> existFile = new ArrayList<>();
        List<String> newFile = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            int imageType = Utils.toInteger(files.get(i).get(ViewKey.FILE_KEY_TYPE));
            if (imageType == ViewKey.TYPE_FILE_PATH) {
                String path = Utils.toString(files.get(i).get(ViewKey.FILE_KEY_URL));
                newFile.add(path);
            } else {
                existFile.add(files.get(i));
            }
        }
        Observable<List<Map<String, Object>>> obExist = Observable.just(existFile);
        if (newFile.size() > 0) {
            Observable<List<Map<String, Object>>> obNew =   Observable.from(newFile).map(new Func1<String, File>() {
                @Override
                public File call(String s) {
                    return new File(s);
                }
            }).flatMap(new Func1<File, Observable<Map<String,Object>>>() {
                @Override
                public Observable<Map<String, Object>> call(File file) {
                    HashMap<String,Object> params = new HashMap<String, Object>();
                    params.put("X_FILENAME",".jpg");
                    return FileUpLoad.getInstance().uploadFile(file, params);
                }
            }).buffer(newFile.size());
            return Observable.zip(obExist, obNew, new Func2<List<Map<String,Object>>, List<Map<String,Object>>, List<Map<String,Object>>>() {
                @Override
                public List<Map<String,Object>> call(List<Map<String, Object>> oldFile, List<Map<String, Object>> newFile) {
                    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
                    for (Map<String,Object> file:oldFile) {
                        list.add(file);
                    }
                    for (Map<String,Object> file:newFile) {
                        list.add(file);
                    }

                    return  list;
                }
            });
        } else {
            return obExist;
        }

    }


    public Observable<Map<String, Object>> uploadFile(final File file, final Map<String,Object> params)
    {

        return Observable.create(new Observable.OnSubscribe<Map<String, Object>>()
        {
            @Override
            public void call(final Subscriber<? super Map<String, Object>> subscriber)
            {
                Map<String, Object> result = uploadFileService(file, params);
                if (result.size() > 0 && Utils.toBoolean(result.get("status")))
                {
                    subscriber.onNext(result);
                } else
                {
                    subscriber.onError(new Throwable());
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public static Map<String, Object> uploadFileService(File file, Map<String, Object> params)
    {
        Map<String, Object> result = new HashMap<>();
        String BOUNDARY = UUID  .randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        String RequestURL = WebKey.WEBKEY_URL_RES;
        try
        {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);
            if (file != null)
            {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                OutputStream outputSteam = conn.getOutputStream();

                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */

                sb.append(LINE_END);

                if (params != null)
                {
                    for (Map.Entry<String, Object> entry : params.entrySet())
                    {
                        sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                        sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END);
                        sb.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END);
                        sb.append("Content-Transfer-Encoding: 8bit" + LINE_END);
                        sb.append(LINE_END);
                        sb.append(entry.getValue());
                        sb.append(LINE_END);
                    }
                }
                sb.append(PREFIX);//开始拼接文件参数
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                sb.append("Content-Disposition: form-data; name=\"img\"; filename=\"" + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                sb.append(LINE_END);

                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1)
                {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                if (res == 200)
                {
                    InputStream in = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String str = null;
                    StringBuffer buffer = new StringBuffer();
                    while ((str =br.readLine ()) != null)
                    {//BufferedReader特有功能，一次读取一行数据
                        buffer.append(str);
                    }
                    in.close();
                    br.close();
                    result = Utils.parseObjectToMapString(buffer);
                    return result;
                }
            }
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
            return result;
        } catch (IOException e)
        {
            e.printStackTrace();
            return result;
        }
        return result;
    }
}
