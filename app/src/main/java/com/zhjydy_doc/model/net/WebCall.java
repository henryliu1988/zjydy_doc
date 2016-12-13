package com.zhjydy_doc.model.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.zhjydy_doc.model.preference.SPUtils;
import com.zhjydy_doc.util.Utils;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2016/7/29.
 */
public class WebCall
{
    private static WebCall webCall;

    public WebCall()
    {
    }

    public static WebCall getInstance()
    {
        if (webCall == null)
        {
            webCall = new WebCall();
        }
        return webCall;
    }


    public Observable<WebResponse> call(int methodId, HashMap<String, Object> params ,boolean needToast) {
        if (!WebKey.WEBKEY_FUNC_COMMON_MAP.containsKey(methodId) && !WebKey.WEBKEY_FUNC_HUAN_MAP.containsKey(methodId))
        {
            WebResponse response = new WebResponse(1, "没有此接口", null);
            return Observable.just(response);
        }
        return callWebService(new WebService(methodId,
                params), null,needToast);
    }

    public Observable<WebResponse> call(int methodId, HashMap<String, Object> params)
    {
        if (!WebKey.WEBKEY_FUNC_COMMON_MAP.containsKey(methodId) && !WebKey.WEBKEY_FUNC_HUAN_MAP.containsKey(methodId))
        {
            WebResponse response = new WebResponse(1, "没有此接口", null);
            return Observable.just(response);
        }
        return callWebService(new WebService(methodId,
                params), null,false);
    }

    public Observable<WebResponse> callCache(int methodId, HashMap<String, Object> params, final WebResponse cache)
    {
        Observable<WebResponse> cacheOb = Observable.create(new Observable.OnSubscribe<WebResponse>()
        {
            @Override
            public void call(Subscriber<? super WebResponse> subscriber)
            {
                subscriber.onNext(cache);
                subscriber.onCompleted();
            }
        });
        Observable<WebResponse> netOb = (Observable<WebResponse>) callWebService(new WebService(methodId,
                params), null,false);

        return Observable
                .concat(cacheOb, netOb)
                .first(new Func1<WebResponse, Boolean>()
                {
                    @Override
                    public Boolean call(WebResponse o)
                    {
                        return (o != null) && (!o.isEmptyData());
                    }
                });
    }

    public Observable<WebResponse> callCacheThree(int methodId, HashMap<String, Object> params, final WebResponse memory, final String prefKey)
    {
        Observable<WebResponse> callMemory = Observable.create(new Observable.OnSubscribe<WebResponse>()
        {
            @Override
            public void call(Subscriber<? super WebResponse> subscriber)
            {
                subscriber.onNext(memory);
                subscriber.onCompleted();
            }
        });
        Observable<WebResponse> callPref = Observable.create(new Observable.OnSubscribe<WebResponse>()
        {
            @Override
            public void call(Subscriber<? super WebResponse> subscriber)
            {
                String value = Utils.toString(SPUtils.get(prefKey, ""));
                WebResponse response = null;
                if (!TextUtils.isEmpty(value))
                {
                    response = new WebResponse();
                    response.setError(0);
                    response.setData(value);
                }
                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });

        Observable<WebResponse> callNet = (Observable<WebResponse>) callWebService(new WebService(methodId,
                params), null,false);

        return Observable.concat(
                callMemory,
                callPref,
                callNet)
                .first(new Func1<WebResponse, Boolean>()
                {
                    @Override
                    public Boolean call(WebResponse o)
                    {
                        return (o != null) && (!o.isEmptyData());
                    }
                });
    }


    public Observable<WebResponse> callWebService(final WebService webService, final String key,boolean needToast)
    {
        return Observable.create(new Observable.OnSubscribe<WebResponse>()
        {
            @Override
            public void call(Subscriber<? super WebResponse> subscriber)
            {
                //订阅者回调 onNext 和 onCompleted
                WebResponse result = webService.call();
            //    Log.d(this.getClass(), result.getError() + "");
                if (result != null && result.getError() == 0)
                {
                    if (!TextUtils.isEmpty(key) && !result.isEmptyData())
                    {
                        SPUtils.put(key, result.getData());
                    }
                    subscriber.onNext(result);
                } else
                {
                    Map<String, Object> erroMap = new HashMap<>();
                    erroMap.put("code", result.getError());
                    erroMap.put("info", result.getInfo());

                    subscriber.onError(new Throwable(JSONObject.toJSONString(erroMap)));
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


}

