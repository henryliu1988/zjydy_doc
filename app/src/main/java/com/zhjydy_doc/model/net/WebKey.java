package com.zhjydy_doc.model.net;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/1.
 */
public class WebKey
{
    public static final String WEBKEY_NAMESPACE = "http://59.110.24.36/yiduyi/";
    public static final String WEBKEY_URL = "http://59.110.24.36/yiduyi/index.php?s=App/Common/index";
    public static final String WEBKEY_BASE = "http://59.110.24.36/yiduyi/";



    public static final int func_login = 1001;   //登录
    public static final int func_checkMobile = 1002;  //检查手机号是否被注册
    public static final int func_sendSms = 1003;  //发送验证码
    public static final int func_reset = 1004;  //重置密码
    public static final int func_register = 1005;  //注册
    public static final int func_banner = 1006;  //首页banner列表
    public static final int func_getNews = 1007;  //首页资讯
    public static final int func_getNewsList = 1008;  //资讯列表

    public static final int func_getRecommendZhuan = 1009;  //首页推荐专家
    public static final int func_getExpertsList = 1010;  //专家列表
    public static final int func_getExpert = 1011;  //专家详情
    public static final int func_getNewsById = 1012;  //资讯详情
    public static final int func_getCommentList = 1013;  //根据专家id获取留言列表
    public static final int func_searchExpertsList = 1014;  //搜索专家列表
    public static final int func_searchNewsList = 1015;  //搜索资讯列表
    public static final int func_collectExpert = 1016;  //收藏专家
    public static final int func_getPatientList = 1017;  //获取登录人的患者列表
    public static final int func_getPatientById = 1018;  //获取患者病例详情
    public static final int func_addPatient = 1019;  //添加患者
    public static final int func_updatePatient = 1020;  //修改患者
    public static final int func_patient = 1021;  //返回该用户是否已经提交认证信息
    public static final int func_collectNews = 1022;  //返回该用户是否已经提交认证信息

    public static final Map<Integer, String> WEBKEY_FUNC_MAP = new HashMap<Integer, String>();

    static {
        WEBKEY_FUNC_MAP.put(func_login,"login");
        WEBKEY_FUNC_MAP.put(func_checkMobile,"checkMobile");
        WEBKEY_FUNC_MAP.put(func_sendSms,"sendSms");
        WEBKEY_FUNC_MAP.put(func_reset,"pass_reset");
        WEBKEY_FUNC_MAP.put(func_register,"register");
        WEBKEY_FUNC_MAP.put(func_banner,"banner");
        WEBKEY_FUNC_MAP.put(func_getNews,"getNews");
        WEBKEY_FUNC_MAP.put(func_getNewsList,"getNewsList");
        WEBKEY_FUNC_MAP.put(func_getRecommendZhuan,"getRecommendZhuan");
        WEBKEY_FUNC_MAP.put(func_getExpertsList,"getExpertsList");
        WEBKEY_FUNC_MAP.put(func_getExpert,"getExpert");
        WEBKEY_FUNC_MAP.put(func_getNewsById,"getNewsById");
        WEBKEY_FUNC_MAP.put(func_getCommentList,"getCommentList");
        WEBKEY_FUNC_MAP.put(func_searchExpertsList,"searchExpertsList");
        WEBKEY_FUNC_MAP.put(func_searchNewsList,"searchNewsList");
        WEBKEY_FUNC_MAP.put(func_collectExpert,"collectExpert");
        WEBKEY_FUNC_MAP.put(func_collectNews,"collectNews");
        WEBKEY_FUNC_MAP.put(func_getPatientList,"getPatientList");
        WEBKEY_FUNC_MAP.put(func_getPatientById,"getPatientById");
        WEBKEY_FUNC_MAP.put(func_addPatient,"addPatient");
        WEBKEY_FUNC_MAP.put(func_updatePatient,"updatePatient");
        WEBKEY_FUNC_MAP.put(func_patient,"patient");
    }
}
