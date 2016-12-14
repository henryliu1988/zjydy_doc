package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.InfoDetailContract;
import com.zhjydy_doc.presenter.presenterImp.InfoDetailPresenterImp;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class InfoDetailFragment extends PageImpBaseFragment implements InfoDetailContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.m_save_info_button)
    ImageView mSaveInfoButton;
    @BindView(R.id.m_share_info_button)
    ImageView mShareInfoButton;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @BindView(R.id.m_info_webview)
    WebView mInfoWebview;
    private InfoDetailContract.Presenter mPresenter;
  //  private ShareAction mShareAction;
    String id;

    @Override
    public void setPresenter(InfoDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    protected void initData() {
        titleCenterTv.setText("资讯详情");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info_detail;
    }

    @Override
    protected void afterViewCreate() {
        if (getArguments() == null) {
            return;
        }
        id = getArguments().getString(IntentKey.FRAG_INFO);
        if (TextUtils.isEmpty(id)) {
            return;
        }
        new InfoDetailPresenterImp(this, id);
        mShareInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shareInfo();
            }
        });
    //    initShareAction();

    }

    /*
    private void initShareAction() {
        mShareAction = new ShareAction(getActivity())
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA).withText("分享资讯")
                .setCallback(new UMShareListener() {
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                });

    }

    private void shareInfo() {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
        mShareAction.open(config);

    }

*/
    @Override
    public void update(Map<String, Object> info) {
        String url = Utils.toString(info.get("url"));
        mInfoWebview.loadUrl(url);
        mInfoWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

    }

    @Override
    public void updateFavStatus(boolean isCollect) {
        if (isCollect) {
            ImageUtils.getInstance().displayFromDrawable(R.mipmap.save_cancel_bottom, mSaveInfoButton);
            mSaveInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.cancelSaveInfo();
                }
            });
        } else {
            ImageUtils.getInstance().displayFromDrawable(R.mipmap.save_text_green, mSaveInfoButton);
            mSaveInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.saveInfo();
                }
            });
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