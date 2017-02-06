package com.zhjydy_doc.model.data;

import android.text.TextUtils;

import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Administrator on 2017/1/7 0007.
 */
public class ExpertData {

    public static ExpertData instance = new ExpertData();


    private WebResponse mGuanData;
    private WebResponse mMeGuanData;
    private WebResponse mFansData;
    private WebResponse mAuthentData;

    public static final int GUAN_STAT_NUL = 1;
    public static final int GUAN_STAT_GUAN = 2;
    public static final int GUAN_STAT_MEGUAN = 3;

    public static ExpertData getInstance() {
        return instance;
    }

    public static final int MAX_PAGE = 10000000;

    public void loadData() {
        loadGuanZhuExperts();
        loadFans();
        loadMeAndExperts();
        loadAuthentData();
    }

    private HashMap<String, Object> getParams() {
        HashMap<String, Object> parasm = new HashMap<>();
        parasm.put("page", 1);
        parasm.put("pagesize", MAX_PAGE);
        parasm.put("memberid", UserData.getInstance().getToken().getId());
        return parasm;
    }

    public void loadGuanZhuExperts() {
        WebCall.getInstance().call(WebKey.func_getGuanExperts, getParams()).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                mGuanData = webResponse;
                RefreshManager.getInstance().refreshData(RefreshKey.GUAN_LIST_CHANGE);
                RefreshManager.getInstance().refreshData(RefreshKey.GUAN_ALL_LIST_CHANGE);

            }
        });
    }

    public void loadMeAndExperts() {
        WebCall.getInstance().call(WebKey.func_getMeAndExpert, getParams()).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                mMeGuanData = webResponse;
                RefreshManager.getInstance().refreshData(RefreshKey.GUAN_ME_LSIT_CHANGE);
                RefreshManager.getInstance().refreshData(RefreshKey.GUAN_ALL_LIST_CHANGE);
            }
        });
    }

    public void loadFans() {
        WebCall.getInstance().call(WebKey.func_getMyfans, getParams()).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                mFansData = webResponse;
                RefreshManager.getInstance().refreshData(RefreshKey.FANS_LSIT_CHANGE);
            }
        });
    }

    public Observable<List<Map<String, Object>>> getGuanData() {
        return WebCall.getInstance().callCache(WebKey.func_getGuanExperts, getParams(), mGuanData).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                String data = webResponse.getData();
                return Utils.parseObjectToListMapString(data);
            }
        });

    }

    public Observable<List<Map<String, Object>>> getMeGuanData() {
        return WebCall.getInstance().callCache(WebKey.func_getMeAndExpert, getParams(), mMeGuanData).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                String data = webResponse.getData();
                return Utils.parseObjectToListMapString(data);
            }
        });
    }

    public Observable<List<Map<String, Object>>> getFancs() {
        return WebCall.getInstance().callCache(WebKey.func_getMyfans, getParams(), mFansData).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                String data = webResponse.getReturnData();
                Map<String,Object> map = Utils.parseObjectToMapString(data);
                String fans = Utils.toString(map.get("result"));
                return Utils.parseObjectToListMapString(fans);
            }
        });
    }

    public Observable<List<Map<String,Object>>> getUnreadFans() {
        return WebCall.getInstance().callCache(WebKey.func_getMyfans, getParams(), mFansData).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                String data = webResponse.getReturnData();
                List<Map<String, Object>> newFans = new ArrayList<Map<String, Object>>();
                Map<String,Object> map = Utils.parseObjectToMapString(data);
                String fans = Utils.toString(map.get("result"));
                List<Map<String, Object>> lsit =  Utils.parseObjectToListMapString(fans);
                for (Map<String,Object> m: lsit) {
                    int isUnread = Utils.toInteger(m.get("read_status"));
                    if (isUnread == 1) {
                        newFans.add(m);
                    }
                }
                return newFans;
            }
        });

    }

    public Observable<List<Map<String, Object>>> getFunsAndGuanzhu() {
        return Observable.zip(getFancs(), getGuanData(), new Func2<List<Map<String, Object>>, List<Map<String, Object>>, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(List<Map<String, Object>> fanc, List<Map<String, Object>> guan) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                list.addAll(fanc);
                for (Map<String, Object> m : list) {
                    String id = Utils.toString(m.get("id"));
                    if (!TextUtils.isEmpty(id)) {
                        for (Map<String, Object> g : guan) {
                            if (id.equals(Utils.toString(g.get("id")))) {
                                m.put("guanzhu", true);
                                break;
                            }
                        }
                    }
                }
                return list;
            }
        });
    }

    public Observable<Integer> getGuanStatus(final String id) {
        return Observable.zip(getGuanData(), getMeGuanData(), new Func2<List<Map<String, Object>>, List<Map<String, Object>>, Integer>() {
            @Override
            public Integer call(List<Map<String, Object>> guanList, List<Map<String, Object>> meGuanList) {
                if (TextUtils.isEmpty(id)) {
                    return GUAN_STAT_NUL;
                }
                for (Map<String, Object> m : guanList) {
                    if (id.equals(Utils.toString(m.get("id")))) {
                        return GUAN_STAT_GUAN;
                    }
                }
                for (Map<String, Object> m : meGuanList) {
                    if (id.equals(Utils.toString(m.get("id")))) {
                        return GUAN_STAT_MEGUAN;
                    }
                }
                return GUAN_STAT_NUL;
            }
        });
    }

    public void loadAuthentData() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("memberid", UserData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_updateAuthent, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                mAuthentData = webResponse;
                RefreshManager.getInstance().refreshData(RefreshKey.AUTHENT_DATA_CHANGE);
            }
        });
    }

    public Observable<List<Map<String, Object>>> getAuthentDataYiCards() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("memberid", UserData.getInstance().getToken().getId());
        return WebCall.getInstance().callCache(WebKey.func_updateAuthent, params, mAuthentData).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (!status) {
                    return new ArrayList<Map<String, Object>>();
                }
                Map<String, Object> map = Utils.parseObjectToMapString(webResponse.getReturnData());
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(map.get("yi_cards"));
                return list;
            }
        });
    }

    public Observable<List<Map<String, Object>>> getAuthentDataIdCards() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("memberid", UserData.getInstance().getToken().getId());
        return WebCall.getInstance().callCache(WebKey.func_updateAuthent, params, mAuthentData).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (!status) {
                    return new ArrayList<Map<String, Object>>();
                }
                Map<String, Object> map = Utils.parseObjectToMapString(webResponse.getReturnData());
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(map.get("idcards"));
                return list;
            }
        });
    }

    public Observable<Map<String, Object>> getAuthenDatas() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("memberid", UserData.getInstance().getToken().getId());
        return WebCall.getInstance().callCache(WebKey.func_updateAuthent, params, mAuthentData).map(new Func1<WebResponse, Map<String, Object>>() {
            @Override
            public Map<String, Object> call(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (!status) {
                    return new HashMap<String, Object>();
                }
                Map<String, Object> map = Utils.parseObjectToMapString(webResponse.getReturnData());
                return map;
            }
        });

    }


    public Observable<Boolean> guanzhuExpert(String id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("memberid", UserData.getInstance().getToken().getId());
        params.put("bei_memberid", id);
        return WebCall.getInstance().call(WebKey.func_GuanExperts, params).map(new Func1<WebResponse, Boolean>() {
            @Override
            public Boolean call(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                return status;
            }
        });

    }

    public Observable<Boolean> canCelGuanzhuExpert(String id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("memberid", UserData.getInstance().getToken().getId());
        params.put("bei_memberid", id);
        return WebCall.getInstance().call(WebKey.func_cancelGuanExperts, params).map(new Func1<WebResponse, Boolean>() {
            @Override
            public Boolean call(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                return status;
            }
        });

    }

}
