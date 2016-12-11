package com.zhjydy_doc.model.net;

import android.text.TextUtils;

import com.zhjydy_doc.model.preference.SPUtils;
import com.zhjydy_doc.util.Log;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2016/7/29.
 */
public class WebCall {
    private static WebCall webCall;

    public WebCall() {
    }

    public static WebCall getInstance() {
        if (webCall == null) {
            webCall = new WebCall();
        }
        return webCall;
    }


    public Observable<WebResponse> call(int methodId,HashMap<String,Object> params) {
        if (!WebKey.WEBKEY_FUNC_MAP.containsKey(methodId)) {
            WebResponse response = new WebResponse(1, "没有此接口", null);
            return Observable.just(response);
        }
        String method = WebKey.WEBKEY_FUNC_MAP.get(methodId);
        return callWebService(new WebService(method,
                params), null);
    }
    public Observable<WebResponse> callWebService(final WebService webService, final String key) {
        return Observable.create(new Observable.OnSubscribe<WebResponse>() {
            @Override
            public void call(Subscriber<? super WebResponse> subscriber) {
                //订阅者回调 onNext 和 onCompleted
                WebResponse result = webService.call();
                Log.d(this.getClass(), result.getError() + "");
                if (result != null && result.getError() == 0) {
                    if (!TextUtils.isEmpty(key) && !result.isEmptyData()) {
                        SPUtils.put(key, result.getData());
                    }
                    subscriber.onNext(result);
                } else {
                    subscriber.onError(new Throwable(result.getInfo()));
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


}

