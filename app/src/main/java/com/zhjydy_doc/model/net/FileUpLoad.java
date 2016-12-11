package com.zhjydy_doc.model.net;



import com.zhjydy_doc.util.Utils;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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


    public Observable<List<Map<String, Object>>> uploadFiles(List<Map<String, Object>> items)
    {
        return Observable.from(items).flatMap(new Func1<Map<String, Object>, Observable<Map<String, Object>>>()
        {
            @Override
            public Observable<Map<String, Object>> call(Map<String, Object> item)
            {
                File file = (File) item.get("file");
                String url = Utils.toString(item.get("url"));
                return uploadFile(file, url);
            }
        }).buffer(items.size());
    }

    public Observable<Map<String, Object>> uploadFile(final File file, final String path)
    {

        return Observable.create(new Observable.OnSubscribe<Map<String, Object>>()
        {
            @Override
            public void call(final Subscriber<? super Map<String, Object>> subscriber)
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uploadDir", path);
                Map<String, Object> result = uploadFileService(file, params);
                if (result.size() > 0 && Utils.toInteger(result.get("error")) == 0)
                {
                    result.put("name",file.getName());
                    subscriber.onNext(result);
                } else
                {
                    subscriber.onError(new Throwable());
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public static Map<String, Object> uploadFileService(File file, Map<String, String> params)
    {
        Map<String, Object> result = new HashMap<>();
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        String RequestURL = WebKey.WEBKEY_URL + "/../../servlet/upload";
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
                    for (Map.Entry<String, String> entry : params.entrySet())
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
                    while ((str = br.readLine()) != null)
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
