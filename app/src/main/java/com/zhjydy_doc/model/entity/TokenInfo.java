package com.zhjydy_doc.model.entity;

import android.text.TextUtils;


import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class TokenInfo {
    String id;
    String mobile;
    String nickname;
    String collectExperts;
    String collectNews;
    String paypass;
    String idcard;
    String status;
    String passoword;
    String sex;
    String photoId;
    String photoUrl;
    public String getPassoword() {
        return passoword;
    }

    public void setPassoword(String passoword) {
        this.passoword = passoword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        RefreshManager.getInstance().refreshData(RefreshKey.TOKEN_MSG_NICK_NAME);
    }

    public String getCollectExperts() {
        return collectExperts;
    }

    public void setCollectExperts(String collectExperts) {
        this.collectExperts = collectExperts;
        RefreshManager.getInstance().refreshData(RefreshKey.KEY_FAV_EXPERT);
    }

    public String getCollectNews() {
        return collectNews;
    }

    public void setCollectNews(String collectNews) {
        this.collectNews = collectNews;
        RefreshManager.getInstance().refreshData(RefreshKey.KEY_FAV_INFO);
    }

    public String getPaypass() {
        return paypass;
    }

    public void setPaypass(String paypass) {
        this.paypass = paypass;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
        RefreshManager.getInstance().refreshData(RefreshKey.TOKEN_MSG_SEX);

    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        RefreshManager.getInstance().refreshData(RefreshKey.TOKEN_MSG_PHOTO);
    }

    public List<String> getCollectExpertList(){
        String collect = getCollectExperts();
        List<String> coList = new ArrayList<String>();
        if (!TextUtils.isEmpty(collect)) {
            coList = Arrays.asList(collect.split(","));
        }
        return coList;
    }
    public List<String> getCollectNewsList(){
        String news = getCollectNews();
        List<String> list = new ArrayList<String>();
        if (!TextUtils.isEmpty(news)) {
            list = Arrays.asList(news.split(","));
        }
        return list;
    }

    public void setCollectExpertAsList(List<String> collect) {
        String str = "";
        if (collect != null && collect.size() > 0) {
            for (int i = 0 ; i < collect.size() ; i ++) {
                str += collect.get(i);
                if (i < collect.size() -1)
                str +=",";
            }
        }
        setCollectExperts(str);
    }

    public void setCollectNewAsList(List<String> news) {
        String str = "";
        if (news != null && news.size() > 0) {
            for (int i = 0 ; i < news.size() ; i ++) {
                str += news.get(i);
                if (i < news.size() -1)
                    str +=",";
            }
        }
        setCollectNews(str);
    }
}
