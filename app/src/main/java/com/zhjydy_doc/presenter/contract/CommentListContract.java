package com.zhjydy_doc.presenter.contract;

import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public interface CommentListContract  {

    interface View extends BaseView<Presenter>
    {
        void updateCommentList(List<Map<String,Object>> list);
    }

    interface Presenter extends BasePresenter
    {
        void readComment(String id);
    }

}
