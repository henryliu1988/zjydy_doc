package com.zhjydy_doc.presenter.presenterImp;

import android.text.TextUtils;


import com.zhjydy_doc.model.data.AppData;
import com.zhjydy_doc.model.data.MsgData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.presenter.contract.ExpertDetailContract;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class ExpertDetailPresenterImp implements ExpertDetailContract.Presenter {

    private ExpertDetailContract.View mView;

    private String expertId;


    private Map<String, Object> mExpertInfo = new HashMap<>();
    private List<Map<String, Object>> mComments = new ArrayList<>();

    public ExpertDetailPresenterImp(ExpertDetailContract.View view, String id) {
        this.mView = view;
        view.setPresenter(this);
        this.expertId = id;
        start();
    }

    @Override
    public void start() {
        if (TextUtils.isEmpty(expertId)) {
            return;
        }
        loadExpertInfo(expertId);
        loadComments(expertId);
        loadFavStatus();
    }

    private void loadExpertInfo(String id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("zid", id);
        WebCall.getInstance().call(WebKey.func_getExpert, params).map(new Func1<WebResponse, Map<String, Object>>() {
            @Override
            public Map<String, Object> call(WebResponse webResponse) {
                String data = webResponse.getData();
                Map<String, Object> map = Utils.parseObjectToMapString(data);
                return map;
            }
        }).subscribe(new BaseSubscriber<Map<String, Object>>(mView.getContext(), true) {
            @Override
            public void onNext(Map<String, Object> map) {
                String collect = AppData.getInstance().getToken().getCollectExperts();
                map.put("collect", collect);
                mExpertInfo = map;
                mView.updateExpertInfos(map);
            }
        });
    }

    private void loadComments(String id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("expertid", id);
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
                mView.updateComments(maps);
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
        params.put("sendid", AppData.getInstance().getToken().getId());
        params.put("sendname", AppData.getInstance().getToken().getNickname());

        params.put("getid", expertId);
        params.put("getname", getName);

        params.put("content", content);
        params.put("expertid", expertId);

        if (!TextUtils.isEmpty(mark)) {
            params.put("mark", mark);
        }
        WebCall.getInstance().call(WebKey.func_addComment, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                if (WebUtils.getWebStatus(webResponse)) {
                    loadComments(expertId);
                    mView.makeCommentSuccess();
                    MsgData.getInstance().loadNewCommentList();
                }else{
                    zhToast.showToast("留言失败！");
                }
            }
        });
    }

    @Override
    public void reloadData() {
        loadExpertInfo(expertId);
        loadComments(expertId);
        loadFavStatus();
    }


    private void loadFavStatus() {
        List<String> collecs = AppData.getInstance().getToken().getCollectExpertList();
        mView.updateFavStatus(collecs.contains(expertId));
    }

    @Override
    public void saveExpert() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("expertid", expertId);
        params.put("userid", AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_collectExpert, params).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(), "请稍后，正在收藏！") {
            @Override
            public void onNext(WebResponse webResponse) {
                if(WebUtils.getWebStatus(webResponse)) {
                    List<String> collect = AppData.getInstance().getToken().getCollectExpertList();
                    if (!collect.contains(expertId)) {
                        collect.add(expertId);
                    }
                    AppData.getInstance().getToken().setCollectExpertAsList(collect);
                    loadFavStatus();
                } else{
                    zhToast.showToast("收藏失败");
                }
            }
        });
    }

    @Override
    public void cancelSaveExpert() {
        HashMap<String, Object> params = new HashMap<>();
        List<String> collect = new ArrayList<>();
        collect.addAll(AppData.getInstance().getToken().getCollectExpertList());
        if (collect.contains(expertId)) {
            collect.remove(expertId);
        }
        params.put("collectexpert",Utils.strListToString(collect));
        params.put("userid", AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_cancelCollectExpert, params).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(), "取消收藏！") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    ArrayList<String> collect = new ArrayList<String>();
                    collect.addAll(AppData.getInstance().getToken().getCollectExpertList());
                    if (collect.contains(expertId)) {
                        collect.remove(expertId);
                    }
                    AppData.getInstance().getToken().setCollectExpertAsList(collect);
                    loadFavStatus();
                } else {
                    zhToast.showToast("取消收藏失败！");
                }
            }
        });

    }


}
