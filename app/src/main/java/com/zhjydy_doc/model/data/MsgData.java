package com.zhjydy_doc.model.data;



import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.util.ListMapComparator;
import com.zhjydy_doc.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class MsgData {

    private static MsgData instance;


    private List<Map<String, Object>> mOrderList = new ArrayList<>();
    private List<Map<String, Object>> mNewCommentList = new ArrayList<>();
    private List<Map<String, Object>> mNewSystemList = new ArrayList<>();

    private WebResponse mOrderMsgData;
    private WebResponse mCommentNewData;
    private WebResponse mNewSystemData;

    public MsgData() {
    }

    public static MsgData getInstance() {
        if (instance == null) {
            instance = new MsgData();
        }
        return instance;
    }

    public void loadData() {
        loadOrderMsgData();
        loadNewSystemList();
        loadNewCommentList();
    }


    public int getUnReadMsgCount() {

        int count = 0;
        if (mOrderList != null && mOrderList.size() > 0) {
            for (Map<String, Object> order : mOrderList) {
                int status = Utils.toInteger(order.get("status"));
                if (status == 0) {
                    count++;
                }
            }
        }
        if (mNewCommentList != null && mNewCommentList.size() > 0) {
            for (Map<String, Object> comment : mNewCommentList) {
                int status = Utils.toInteger(comment.get("status"));
                if (status == 0) {
                    count++;
                }
            }
        }
        return count;
    }


    public Observable<List<Map<String, Object>>> getAllOrderMsgList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", AppData.getInstance().getToken().getId());
        return WebCall.getInstance().callCache(WebKey.func_getOrdersMsg, params, mOrderMsgData).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                mOrderMsgData = webResponse;
                String data = webResponse.getData();
                mOrderList = Utils.parseObjectToListMapString(data);
                ListMapComparator comp = new ListMapComparator("addtime",0);
                Collections.sort(mOrderList,comp);
                Integer statusGroup[] = {2,3,4,5,6,7,9,10,11,12};

                Iterator<Map<String,Object>> it = mOrderList.iterator();
                List<Integer> statusList = Arrays.asList(statusGroup);
                while (it.hasNext()) {
                    Map<String,Object> qu = it.next();
                    if (!statusList.contains(Utils.toInteger(qu.get("orderstatus")))) {
                        it.remove();
                    }
                }
                RefreshManager.getInstance().refreshData(RefreshKey.ORDER_DATA_READ);
                RefreshManager.getInstance().refreshData(RefreshKey.ORDER_DATA_LIST);
                return mOrderList;
            }
        });
    }

    public Observable<List<Map<String, Object>>> getAllCommentNewList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", AppData.getInstance().getToken().getId());
        params.put("pagesize", 30);
        return WebCall.getInstance().callCache(WebKey.func_getNewCommentList, params, mCommentNewData).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                String data = webResponse.getData();
                mCommentNewData = webResponse;
                mNewCommentList = Utils.parseObjectToListMapString(data);
                RefreshManager.getInstance().refreshData(RefreshKey.NEW_COMMENT_DATA_LIST);
                RefreshManager.getInstance().refreshData(RefreshKey.NEW_COMMENT_DATA_READ);
                return mNewCommentList;
            }
        });
    }

    public Observable<List<Map<String, Object>>> getNewSystemMsgList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("pagesize", 1);
      return   WebCall.getInstance().callCache(WebKey.func_getSysMsg, params,mNewSystemData).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                mNewSystemData = webResponse;
                String data = webResponse.getReturnData();
                Map<String, Object> map = Utils.parseObjectToMapString(data);
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(map.get("data"));
                RefreshManager.getInstance().refreshData(RefreshKey.SYTEM_DATA_LIST);

                return list;
            }
        });


    }


    public void loadOrderMsgData() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_getOrdersMsg, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                mOrderMsgData = webResponse;
                mOrderList = Utils.parseObjectToListMapString(data);
                ListMapComparator comp = new ListMapComparator("addtime",0);
                Collections.sort(mOrderList,comp);
                Integer statusGroup[] = {2,3,4,5,6,7,9,10,11,12};

                Iterator<Map<String,Object>> it = mOrderList.iterator();
                List<Integer> statusList = Arrays.asList(statusGroup);
                while (it.hasNext()) {
                    Map<String,Object> qu = it.next();
                    if (!statusList.contains(Utils.toInteger(qu.get("orderstatus")))) {
                        it.remove();
                    }
                }

                RefreshManager.getInstance().refreshData(RefreshKey.ORDER_DATA_LIST);
                RefreshManager.getInstance().refreshData(RefreshKey.ORDER_DATA_READ);

            }
        });
    }

    public void loadNewCommentList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", AppData.getInstance().getToken().getId());
        params.put("pagesize", 30);
        WebCall.getInstance().call(WebKey.func_getNewCommentList, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                mCommentNewData = webResponse;
                mNewCommentList = Utils.parseObjectToListMapString(data);
                RefreshManager.getInstance().refreshData(RefreshKey.NEW_COMMENT_DATA_LIST);
                RefreshManager.getInstance().refreshData(RefreshKey.NEW_COMMENT_DATA_READ);

            }
        });

    }

    private void loadNewSystemList() {
    }

}
