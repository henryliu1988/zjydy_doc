package com.zhjydy_doc.view.fragment;


import com.zhjydy_doc.presenter.contract.PasswordChangContract;
import com.zhjydy_doc.presenter.presenterImp.PasswordChangPresenterImp;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class PasswordChangeFragment extends  PageImpBaseFragment implements PasswordChangContract.View {

    private PasswordChangContract.Presenter mPresenter;
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void afterViewCreate() {
        new PasswordChangPresenterImp(this);
    }



    @Override
    public void setPresenter(PasswordChangContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }
}
