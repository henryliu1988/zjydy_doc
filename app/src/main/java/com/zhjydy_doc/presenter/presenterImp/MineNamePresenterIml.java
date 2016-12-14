package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.data.AppData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.presenter.contract.MineNameChangContract;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/10 0010.
 */
public class MineNamePresenterIml implements MineNameChangContract.Presenter {

    private MineNameChangContract.View mView;
    public MineNamePresenterIml(MineNameChangContract.View view) {
        mView =view;
        mView.setPresenter(this);
        start();
   }
    @Override
    public void submitChangeConfirm(final String name) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("nickname", name);
        params.put("sex", AppData.getInstance().getToken().getSex());
        params.put("head_img",AppData.getInstance().getToken().getPhotoId());
        params.put("id",AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_updateMember,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                zhToast.showToast(WebUtils.getWebMsg(webResponse));
                String msg = status ? "修改成功":"修改失败";
                if (status) {
                    AppData.getInstance().getToken().setNickname(name);
                    RefreshManager.getInstance().refreshData(RefreshKey.TOKEN_MSG_NICK_NAME);
                }
                mView.submitResult(status,msg);
            }
        });

    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }
}
