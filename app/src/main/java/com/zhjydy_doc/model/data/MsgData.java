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
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class MsgData
{

    private static MsgData instance;


    private List<Map<String, Object>> mOrderList = new ArrayList<>();
    private List<Map<String, Object>> mNewPatientCommentList = new ArrayList<>();

    private List<Map<String, Object>> mNewExpertCommentList = new ArrayList<>();

    private List<Map<String, Object>> mNewSystemList = new ArrayList<>();

    private WebResponse mOrderMsgData;
    private WebResponse mCommentNewPatientData;
    private WebResponse mCommentNewExpertData;
    private WebResponse mNewSystemData;

    public MsgData()
    {
    }

    public static MsgData getInstance()
    {
        if (instance == null)
        {
            instance = new MsgData();
        }
        return instance;
    }

    public void loadData()
    {
        loadOrderMsgData();
        loadNewSystemList();
        loadNewCommentList(1);
        loadNewCommentList(2);
    }


    public int getUnReadMsgCount()
    {

        int count = 0;
        if (mOrderList != null && mOrderList.size() > 0)
        {
            for (Map<String, Object> order : mOrderList)
            {
                int status = Utils.toInteger(order.get("status"));
                if (status == 0)
                {
                    count++;
                }
            }
        }
        if (mNewExpertCommentList != null && mNewExpertCommentList.size() > 0)
        {
            for (Map<String, Object> comment : mNewExpertCommentList)
            {
                int status = Utils.toInteger(comment.get("status"));
                if (status == 0)
                {
                    count++;
                }
            }
        }

        if (mNewPatientCommentList != null && mNewPatientCommentList.size() > 0)
        {
            for (Map<String, Object> comment : mNewPatientCommentList)
            {
                int status = Utils.toInteger(comment.get("status"));
                if (status == 0)
                {
                    count++;
                }
            }
        }
        return count;
    }


    public Observable<List<Map<String, Object>>> getAllOrderMsgList()
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", UserData.getInstance().getToken().getId());
        return WebCall.getInstance().callCache(WebKey.func_getOrdersMsg, params, mOrderMsgData).map(new Func1<WebResponse, List<Map<String, Object>>>()
        {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse)
            {
                mOrderMsgData = webResponse;
                String data = webResponse.getData();
                mOrderList = parseOrderDataToNewMsg(data);
                return mOrderList;
            }
        });
    }


    public List<Map<String, Object>> parseOrderDataToNewMsg(String data)
    {
        List<Map<String, Object>> msgList = new ArrayList<>();
        Map<String, Object> map = Utils.parseObjectToMapString(data);
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            Object value = entry.getValue();
            List<Map<String,Object>> list = Utils.parseObjectToListMapString(value);
            if(list.size() < 1) {
                break;
            }
            ListMapComparator comp = new ListMapComparator("addtime", 0);
            Collections.sort(list, comp);
            Integer statusGroup[] = {1, 3, 4, 5, 6, 9, 10};
            List<Integer> statusList = Arrays.asList(statusGroup);
            Map<String,Object> msgData = list.get(0);
            if (statusList.contains(Utils.toInteger(msgData.get("orderstatus"))))
            {
                msgList.add(msgData);
            }
        }
        ListMapComparator comp = new ListMapComparator("addtime", 0);
        Collections.sort(msgList, comp);

        return  msgList;
    }

    public Observable<List<Map<String, Object>>> getAllCommentPatientNewList()
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", UserData.getInstance().getToken().getId());
        params.put("pagesize", 1000000);
        params.put("type", 1);
        return WebCall.getInstance().callCache(WebKey.func_getNewCommentList, params, mCommentNewPatientData).map(new Func1<WebResponse, List<Map<String, Object>>>()
        {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse)
            {
                String data = webResponse.getData();
                mCommentNewPatientData = webResponse;
                mNewPatientCommentList = Utils.parseObjectToListMapString(data);
                RefreshManager.getInstance().refreshData(RefreshKey.NEW_COMMENT_PATIENT_DATA_LIST);
                RefreshManager.getInstance().refreshData(RefreshKey.NEW_COMMENT_PATIENT_DATA_READ);
                return mNewPatientCommentList;
            }
        });
    }

    public Observable<List<Map<String, Object>>> getAllCommentExpertNewList()
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", UserData.getInstance().getToken().getId());
        params.put("pagesize", 1000000);
        params.put("type", 2);
        return WebCall.getInstance().callCache(WebKey.func_getNewCommentList, params, mCommentNewExpertData).map(new Func1<WebResponse, List<Map<String, Object>>>()
        {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse)
            {
                String data = webResponse.getData();
                mCommentNewExpertData = webResponse;
                mNewExpertCommentList = Utils.parseObjectToListMapString(data);
                RefreshManager.getInstance().refreshData(RefreshKey.NEW_COMMENT_EXPERT_DATA_LIST);
                RefreshManager.getInstance().refreshData(RefreshKey.NEW_COMMENT_PATIENT_DATA_READ);
                return mNewExpertCommentList;
            }
        });
    }

    public Observable<List<Map<String, Object>>> getNewSystemMsgList()
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("pagesize", 100000);
        return WebCall.getInstance().callCache(WebKey.func_getSysMsg, params, mNewSystemData).map(new Func1<WebResponse, List<Map<String, Object>>>()
        {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse)
            {
                mNewSystemData = webResponse;
                String data = webResponse.getReturnData();
                Map<String, Object> map = Utils.parseObjectToMapString(data);
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(map.get("data"));
                RefreshManager.getInstance().refreshData(RefreshKey.SYTEM_DATA_LIST);
                return list;
            }
        });


    }


    public void loadOrderMsgData()
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", UserData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_getOrdersMsg, params).subscribe(new BaseSubscriber<WebResponse>()
        {
            @Override
            public void onNext(WebResponse webResponse)
            {
                String data = webResponse.getData();
                mOrderMsgData = webResponse;
                mOrderList = parseOrderDataToNewMsg(data);
                RefreshManager.getInstance().refreshData(RefreshKey.ORDER_MSG_CHANGE);

            }
        });
    }

    public void loadNewCommentList(final int type)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", UserData.getInstance().getToken().getId());
        params.put("pagesize", 30);
        params.put("type", type);
        WebCall.getInstance().call(WebKey.func_getNewCommentList, params).subscribe(new BaseSubscriber<WebResponse>()
        {
            @Override
            public void onNext(WebResponse webResponse)
            {
                String data = webResponse.getData();
                if (type == 1)
                {
                    mCommentNewExpertData = webResponse;
                    mNewPatientCommentList = Utils.parseObjectToListMapString(data);
                    RefreshManager.getInstance().refreshData(RefreshKey.NEW_COMMENT_PATIENT_DATA_LIST);
                    RefreshManager.getInstance().refreshData(RefreshKey.NEW_COMMENT_PATIENT_DATA_LIST);
                } else if (type == 2)
                {
                    mCommentNewExpertData = webResponse;
                    mNewExpertCommentList = Utils.parseObjectToListMapString(data);
                    RefreshManager.getInstance().refreshData(RefreshKey.NEW_COMMENT_EXPERT_DATA_LIST);
                    RefreshManager.getInstance().refreshData(RefreshKey.NEW_COMMENT_EXPERT_DATA_READ);

                }

            }
        });

    }

    private void loadNewSystemList()
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("pagesize", 100000);
        WebCall.getInstance().call(WebKey.func_getSysMsg, params).subscribe(new BaseSubscriber<WebResponse>()
        {
            @Override
            public void onNext(WebResponse webResponse)
            {
                mNewSystemData = webResponse;
                String data = webResponse.getReturnData();
                Map<String, Object> map = Utils.parseObjectToMapString(data);
                mNewSystemList = Utils.parseObjectToListMapString(map.get("data"));
                RefreshManager.getInstance().refreshData(RefreshKey.SYTEM_DATA_LIST);
            }
        });


    }

}
