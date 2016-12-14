package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface ExpertDetailContract
{

    interface View extends BaseView<Presenter>
    {
        void updateExpertInfos(Map<String, Object> expertInfo);

        void updateComments(List<Map<String, Object>> comments);
        void updateFavStatus(boolean isCollect);
        void makeCommentSuccess();
    }

    interface Presenter extends BasePresenter
    {
        void makeNewComment(String commentId);
        void reloadData();
        void saveExpert();
        void cancelSaveExpert();
        Map<String,Object> getExpertSubScribInfo();
    }
}
