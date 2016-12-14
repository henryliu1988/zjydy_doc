package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.data.AppData;
import com.zhjydy_doc.presenter.contract.AccountSafeContract;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class AccountSafePresenterImp implements AccountSafeContract.Presenter
{

    private AccountSafeContract.View mView;

    public AccountSafePresenterImp(AccountSafeContract.View view)
    {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start()
    {
        mView.updatePhoneNum(AppData.getInstance().getToken().getMobile());
    }


    @Override
    public void finish()
    {

    }


    @Override
    public void reloadData()
    {
        start();

    }
}
