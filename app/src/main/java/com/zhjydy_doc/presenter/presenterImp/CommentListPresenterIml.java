package com.zhjydy_doc.presenter.presenterImp;

import com.zhjydy_doc.model.data.MsgData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.presenter.contract.CommentListContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class CommentListPresenterIml implements CommentListContract.Presenter {


    private CommentListContract.View mView;
    private int type;

    public CommentListPresenterIml(CommentListContract.View view, int type) {
        this.mView = view;
        view.setPresenter(this);
        this.type = type;
        start();
    }

    @Override
    public void start() {
        loadCommentNewList();
    }


    private void loadCommentNewList() {
        if (type == 1) {
            MsgData.getInstance().getAllCommentPatientNewList().subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
                @Override
                public void onNext(List<Map<String, Object>> maps) {
                    mView.updateCommentList(maps);
                }
            });
        }
        if (type == 2) {
            MsgData.getInstance().getAllCommentExpertNewList().subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
                @Override
                public void onNext(List<Map<String, Object>> maps) {
                    mView.updateCommentList(maps);
                }
            });
        }
    }

    @Override
    public void finish() {

    }

    @Override
    public void readComment(String id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        WebCall.getInstance().call(WebKey.func_updateCommentStatus, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                MsgData.getInstance().loadNewCommentList(type);
            }
        });

    }
}
