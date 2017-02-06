package com.zhjydy_doc.model.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhjydy_doc.util.Utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by admin on 2016/8/1.
 */
public class WebService {
    private HashMap<String, String> properties;

    private int methodId;
    private String method;

    public WebService(int methodId, HashMap<String, Object> params) {
        this.properties = createProporties(params);
        this.methodId = methodId;
    }


    public WebResponse call() {
        String namespace = WebKey.WEBKEY_NAMESPACE;
        String methodName;
        String url;
        if (WebKey.WEBKEY_FUNC_HUAN_MAP.containsKey(methodId)) {
            methodName = WebKey.WEBKEY_FUNC_HUAN_MAP.get(methodId);
            url = WebKey.WEBKEY_URL_HUAN;
        } else if (WebKey.WEBKEY_FUNC_ZHUAN_MAP.containsKey(methodId)) {
            methodName = WebKey.WEBKEY_FUNC_ZHUAN_MAP.get(methodId);
            url = WebKey.WEBKEY_URL_ZHUAN;
        } else {
            methodName = WebKey.WEBKEY_FUNC_COMMON_MAP.get(methodId);
            url = WebKey.WEBKEY_URL_COMMON;

        }
        method = methodName;
        // 创建HttpTransportSE对象，传递WebService服务器地址
        final HttpTransportSE httpTransportSE = new HttpTransportSE(url);
        // 创建SoapObject对象
        SoapObject soapObject = new SoapObject(namespace, methodName);

        // SoapObject添加参数

        if (this.properties != null) {
            soapObject.addProperty("jsonData", mapToJson(this.properties));
            JSONObject json = new JSONObject();
            for (Iterator<Map.Entry<String, String>> it = properties.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, String> entry = it.next();
                json.put(entry.getKey(), entry.getValue());
            }
        }
        // 实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号
        final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        // 设置是否调用的是.Net开发的WebService
        soapEnvelope.setOutputSoapObject(soapObject);
        soapEnvelope.dotNet = false;
        httpTransportSE.debug = true;
        WebResponse response = new WebResponse();
        SoapObject resultSoapObject = null;
        try {
            httpTransportSE.call(namespace + methodName, soapEnvelope);
            if (soapEnvelope.getResponse() != null) {
                // 获取服务器响应返回的SoapObject
                resultSoapObject = (SoapObject) soapEnvelope.bodyIn;
                if (resultSoapObject != null && resultSoapObject.getProperty("return") != null) {
                    Object object = resultSoapObject.getProperty("return");
                    response.setReturnData(Utils.toString(object));
                    boolean status = false;
                    Map<String, Object> m = new HashMap<>();
                    if (object != null) {
                        m = Utils.parseObjectToMapString(object);
                        status = Utils.toBoolean(m.get("status"));
                    }

                    String data = Utils.toString(m.get("data"));
                    if (TextUtils.isEmpty(data)) {
                        data = Utils.toString(m.get("msg"));
                    }
                    response.setData(data);
                    response.setError(0);
                    response.setFuncName(method);
                }
            }
        } catch (Exception e) {
            // Log.e(WebService.class, "exception " + e);
            String info = "连接服务器失败";
            response.setInfo(info);
            response.setError(1);
        }
        return response;
    }

    private HashMap<String, String> createProporties(HashMap<String, Object> data) {
        HashMap<String, String> properties = new HashMap<>();
        for (String key : data.keySet()) {
            properties.put(key, JSON.toJSONString(data.get(key)));
        }
        return properties;
    }


    private String mapToJson(Map<String, String> map) {
        String json = "{";
        int size = map.size();
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            i++;
            String item = "\"" + entry.getKey() + "\":" + entry.getValue();
            json += item;
            if (i < size) {
                json += ",";
            }
        }
        json += "}";
        return json;
    }
}
