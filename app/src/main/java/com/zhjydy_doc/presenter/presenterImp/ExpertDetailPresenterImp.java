package com.zhjydy_doc.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy_doc.model.data.ExpertData;
import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithKey;
import com.zhjydy_doc.presenter.contract.ExpertDetailContract;
import com.zhjydy_doc.util.ListMapComparator;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class ExpertDetailPresenterImp implements ExpertDetailContract.Presenter,RefreshWithKey {

    private ExpertDetailContract.View mView;

    private String expertId;


    private Map<String, Object> mExpertInfo = new HashMap<>();
    private List<Map<String, Object>> mComments = new ArrayList<>();

    public ExpertDetailPresenterImp(ExpertDetailContract.View view, String id) {
        this.mView = view;
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.GUAN_ALL_LIST_CHANGE,this);
        this.expertId = id;
        start();
    }

    @Override
    public void start() {
        if (TextUtils.isEmpty(expertId)) {
            return;
        }
        loadExpertInfo(expertId);
        loadIdentiFyStatus(expertId);
        loadComments(expertId);
    }

    
    private Observable<Map<String, Object>> expertInfoObserval(String id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("expertid", id);
        return WebCall.getInstance().call(WebKey.func_getExpert, params).map(new Func1<WebResponse, Map<String, Object>>() {
            @Override
            public Map<String, Object> call(WebResponse webResponse) {
                String data = webResponse.getData();
                Map<String, Object> map = Utils.parseObjectToMapString(data);
                return map;
            }
        });
    }
    private void loadExpertInfo( String id) {
        Observable.zip(expertInfoObserval(id), getGuanzhuStatus(id), new Func2<Map<String,Object>, Integer, Map<String,Object>>() {
            @Override
            public Map<String, Object> call(Map<String, Object> info, Integer guanStatus) {
                info.put("guanzhu",guanStatus);
                return info;
            }
        }).subscribe(new BaseSubscriber<Map<String, Object>>(mView.getContext(),"") {
            @Override
            public void onNext(Map<String, Object> info) {
                if (mView != null){
                    mView.updateExpertInfos(info);
                }
            }
        });
    }

    private void loadIdentiFyStatus(String id) {
        getIdentiStatus(id).subscribe(new BaseSubscriber<Integer>() {
            @Override
            public void onNext(Integer status) {
                mView.updateIdentyStatus(status);
            }
        });
    }
    private void loadComments(String id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("expertid", id);
        params.put("type", 2);
        WebCall.getInstance().call(WebKey.func_getCommentList, params).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(webResponse.getData());
                return list;
            }
        }).subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> maps) {
                mComments = maps;
                ListMapComparator comp = new ListMapComparator("addtime", 0);
                Collections.sort(mComments, comp);
                List<Map<String,Object>> userComment = new ArrayList<Map<String, Object>>();
                for (Map<String, Object> m : mComments) {
                    String sendId = Utils.toString(m.get("sendid"));
                    String getId = Utils.toString(m.get("getid"));
                    String userId = Utils.toString(UserData.getInstance().getToken().getId());
                    if (userId != null && (userId.equals(sendId) || userId.equals(getId))){
                        userComment.add(m);
                    }
                }
                mView.updateComments(userComment);
            }
        });
    }

    @Override
    public void finish() {

    }


    @Override
    public Map<String, Object> getExpertSubScribInfo() {
        return mExpertInfo;
    }


    @Override
    public void makeNewComment(String content) {
        HashMap<String, Object> params = new HashMap<>();
        String getName = Utils.toString(mExpertInfo.get("realname"));

        String mark = null;

        if (mComments != null && mComments.size() > 0) {
            Map<String, Object> item = mComments.get(0);
            mark = Utils.toString(item.get("mark"));
        }
        params.put("sendid", UserData.getInstance().getToken().getId());
        params.put("sendname", UserData.getInstance().getToken().getmExpertInfo().getRealname());

        params.put("getid", expertId);
        params.put("getname", getName);

        params.put("content", content);
        params.put("expertid",  expertId);

        if (!TextUtils.isEmpty(mark)) {
            params.put("mark", mark);
        }
        params.put("type", 2);
        WebCall.getInstance().call(WebKey.func_addComment, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                if (WebUtils.getWebStatus(webResponse)) {
                    loadComments(expertId);
                    mView.makeCommentSuccess();
                  //  MsgData.getInstance().loadNewCommentList();
                } else {
                    zhToast.showToast("留言失败！");
                }
            }
        });
    }

    @Override
    public void reloadData() {
        loadExpertInfo(expertId);
        loadComments(expertId);
    }


    private Observable<Integer> getGuanzhuStatus(String expertId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("bei_memberid", expertId);
        params.put("memberid", UserData.getInstance().getToken().getId());
        return WebCall.getInstance().call(WebKey.func_getGuanbyid,params).map(new Func1<WebResponse, Integer>() {
            @Override
            public Integer call(WebResponse webResponse) {
                String data = webResponse.getReturnData();
                Map<String,Object> map = Utils.parseObjectToMapString(data);
                int status = Utils.toInteger(map.get("status"));
                return status;
            }
        });
    }
    private Observable<Integer> getIdentiStatus(String expertId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("memberid", expertId);
        return WebCall.getInstance().call(WebKey.func_getExpertStatusbyid,params).map(new Func1<WebResponse, Integer>() {
            @Override
            public Integer call(WebResponse webResponse) {
                String data = webResponse.getReturnData();
                Map<String,Object> map = Utils.parseObjectToMapString(data);
                int status = Utils.toInteger(map.get("status"));
                return status;
            }
        });
    }

    @Override
    public void guanzhuExpert() {
        ExpertData.getInstance().guanzhuExpert(expertId).subscribe(new BaseSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    zhToast.showToast("关注成功");
                } else{
                    zhToast.showToast("关注失败");
                }
                ExpertData.getInstance().loadGuanZhuExperts();
                ExpertData.getInstance().loadMeAndExperts();
                loadExpertInfo(expertId);
            }
        });
    }

    @Override
    public void cancelGuanzhuExpert() {
        ExpertData.getInstance().canCelGuanzhuExpert(expertId).subscribe(new BaseSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    zhToast.showToast("取消关注成功");
                } else{
                    zhToast.showToast("取消关注失败");
                }
                ExpertData.getInstance().loadGuanZhuExperts();
                ExpertData.getInstance().loadMeAndExperts();
                loadExpertInfo(expertId);
            }
        });
    }


    @Override
    public void onRefreshWithKey(int key) {
        if (key == RefreshKey.GUAN_ALL_LIST_CHANGE) {

        }
    }
}
