package com.zhjydy_doc.view.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.MineNameChangContract;
import com.zhjydy_doc.presenter.presenterImp.MineNamePresenterIml;
import com.zhjydy_doc.view.zjview.zhToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class MineNameChangeFragment extends PageImpBaseFragment implements MineNameChangContract.View {

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.edit_title)
    TextView editTitle;
    private MineNameChangContract.Presenter mPresenter;


    String valueText = "";
    private String changeKey = "";

    @Override
    public void setPresenter(MineNameChangContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_name_change;
    }

    @Override
    protected void afterViewCreate() {

        if (TextUtils.isEmpty(getArguments().getString(IntentKey.FRAG_INFO))) {
            return;
        }
        changeKey = getArguments().getString(IntentKey.FRAG_INFO);
        if ("phone".equals(changeKey)) {
            valueText = "联系电话";
        } else if ("realname".equals(changeKey)) {
            valueText = "用户姓名";
        } else if ("adept".equals(changeKey)){
            valueText = "专业擅长";
            editName.setLines(4);
        } else if ("sofunc".equals(changeKey)) {
            valueText = "社会任职";
            editName.setLines(4);
        }
        titleCenterTv.setText("修改" + valueText);
        editTitle.setText("请输入" + valueText);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    zhToast.showToast("请输入" + valueText);
                }

                mPresenter.submitChangeConfirm(changeKey,name);
            }
        });


        new MineNamePresenterIml(this);
    }

    @Override
    public void refreshView() {

    }


    @Override
    public void submitResult(boolean result, String msg) {
        if (result) {
            back();
        } else {
            zhToast.showToast(msg);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
