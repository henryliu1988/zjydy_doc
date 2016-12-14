package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.data.AppData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.presenter.contract.LoginPasswordChangeContract;
import com.zhjydy_doc.util.MD5;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/10/30 0030.
 */
public class LoginPasswordChangePresenterImp implements LoginPasswordChangeContract.Presenter {

    private LoginPasswordChangeContract.View mView;
    public LoginPasswordChangePresenterImp(LoginPasswordChangeContract.View view){
        mView = view;
        mView.setPresenter(this);
        start();
    }
    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }

    @Override
    public void confirmUpdate(String oldPw, String newPw) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("password", MD5.GetMD5Code(oldPw));
        params.put("newpassword",MD5.GetMD5Code(newPw));
        params.put("id", AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_updatePassword,params).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"正在提交数据") {
            @Override
            public void onNext(WebResponse webResponse) {
                if(WebUtils.getWebStatus(webResponse)) {
                    mView.updatePassWordOk();
                } else {
                    zhToast.showToast("修改密码失败");
                }
            }
        });
    }
}
