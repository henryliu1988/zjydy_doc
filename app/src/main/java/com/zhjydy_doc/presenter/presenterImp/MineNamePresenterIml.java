package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.entity.ExpertInfo;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.presenter.contract.MineNameChangContract;

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
    public void submitChangeConfirm(final String key,final String value) {
        HashMap<String,Object> params = new HashMap<>();
        params.put(key, value);
        params.put("memberid",UserData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_updateExpertInformation,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                //zhToast.showToast(WebUtils.getWebMsg(webResponse));
                String msg = status ? "修改成功":"修改失败";
                if (status) {
                  //  UserData.getInstance().getToken().setNickname(name);
                    ExpertInfo info=  UserData.getInstance().getToken().getmExpertInfo();
                    info.setValueByKey(key,value);
                    UserData.getInstance().getToken().setmExpertInfo(info);
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
