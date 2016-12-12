package com.zhjydy_doc.model.net;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/1.
 */
public class WebKey {
    public static final String WEBKEY_NAMESPACE = "http://59.110.24.36/yiduyi/";
    public static final String WEBKEY_URL_COMMON = "http://59.110.24.36/yiduyi/index.php?s=App/Common/index";
    public static final String WEBKEY_URL_HUAN = "http://59.110.24.36/yiduyi/index.php?s=App/Huan/index";

    public static final String WEBKEY_BASE = "http://59.110.24.36/yiduyi/";
    public static final String WEBKEY_URL_RES = "http://59.110.24.36/yiduyi/index.php?s=App/Common/imgUpload";



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
    public static final int func_getoffice = 1023;  //科室字典
    public static final int func_getbusiness = 1024;  //职称字典
    public static final int func_getHospital = 1025;  //医院字典
    public static final int func_getpro = 1026;  //医院字典
    public static final int func_getCity = 1027;  //医院字典
    public static final int func_getQu = 1028;  //医院字典
    public static final int func_getCollectExpert = 1031;  //医院字典
    public static final int func_getCollectNews = 1032;  //医院字典
    public static final int func_updateHuan = 1033;  //医院字典
    public static final int func_getOrders = 1034;  //全部订单
    public static final int func_getOrdersById = 1035;  //订单详情
    public static final int func_getOrdersMsg = 1036;  //订单详情
    public static final int func_updateOrdersMsg = 1037;  //订单详情
    public static final int func_cancelCollectExpert = 1038;  //订单详情
    public static final int func_cancelCollectNews = 1039;  //订单详情
    public static final int func_addComment = 1040;  //订单详情
    public static final int func_updatePassword = 1041;  //修改登录密码
    public static final int func_addPayPass = 1042;  //修改支付密码
    public static final int func_makeOrder = 1043;  //预约专家
    public static final int func_getNewCommentList = 1044;  //预约专家
    public static final int func_getComment = 1045;  //预约专家
    public static final int func_cancelOrder = 1046;  //预约专家
    public static final int func_updateMobile = 1047;  //修改手机号
    public static final int func_updateMember = 1048;  //
    public static final int func_getCancelReason = 1049;  //
    public static final int func_searchCollectExpert = 1050;  //
    public static final int func_searchCollectNews = 1051;  //
    public static final int func_getRecommend = 1052;  //
    public static final int func_updateCommentStatus = 1053;  //
    public static final int func_getSysMsg = 1054;  //




    public static final Map<Integer, String> WEBKEY_FUNC_COMMON_MAP = new HashMap<Integer, String>();
    static {
        WEBKEY_FUNC_COMMON_MAP.put(func_getComment, "getComment");
        WEBKEY_FUNC_COMMON_MAP.put(func_getNewCommentList, "getNewCommentList");
        WEBKEY_FUNC_COMMON_MAP.put(func_cancelCollectExpert, "cancelCollectExpert");
        WEBKEY_FUNC_COMMON_MAP.put(func_cancelCollectNews, "cancelCollectNews");
        WEBKEY_FUNC_COMMON_MAP.put(func_getoffice, "getoffice");
        WEBKEY_FUNC_COMMON_MAP.put(func_getbusiness, "getbusiness");
        WEBKEY_FUNC_COMMON_MAP.put(func_getHospital, "getHospital");
        WEBKEY_FUNC_COMMON_MAP.put(func_getpro, "getpro");
        WEBKEY_FUNC_COMMON_MAP.put(func_getCity, "getcity");
        WEBKEY_FUNC_COMMON_MAP.put(func_getQu, "getqu");
        WEBKEY_FUNC_COMMON_MAP.put(func_login, "login");
        WEBKEY_FUNC_COMMON_MAP.put(func_checkMobile, "checkMobile");
        WEBKEY_FUNC_COMMON_MAP.put(func_sendSms, "sendSms");
        WEBKEY_FUNC_COMMON_MAP.put(func_reset, "pass_reset");
        WEBKEY_FUNC_COMMON_MAP.put(func_register, "register");
        WEBKEY_FUNC_COMMON_MAP.put(func_banner, "getbanner");
        WEBKEY_FUNC_COMMON_MAP.put(func_getNews, "getNews");
        WEBKEY_FUNC_COMMON_MAP.put(func_getNewsList, "getNewsList");
        WEBKEY_FUNC_COMMON_MAP.put(func_getRecommendZhuan, "getRecommendZhuan");
        WEBKEY_FUNC_COMMON_MAP.put(func_getExpertsList, "getExpertsList");
        WEBKEY_FUNC_COMMON_MAP.put(func_getExpert, "getExpert");
        WEBKEY_FUNC_COMMON_MAP.put(func_getNewsById, "getNewsById");
        WEBKEY_FUNC_COMMON_MAP.put(func_getCommentList, "getCommentList");
        WEBKEY_FUNC_COMMON_MAP.put(func_searchExpertsList, "searchExpertsList");
        WEBKEY_FUNC_COMMON_MAP.put(func_searchNewsList, "searchNewsList");
        WEBKEY_FUNC_COMMON_MAP.put(func_collectExpert, "collectExpert");
        WEBKEY_FUNC_COMMON_MAP.put(func_collectNews, "collectNews");
        WEBKEY_FUNC_COMMON_MAP.put(func_getCollectNews, "getCollectNews");
        WEBKEY_FUNC_COMMON_MAP.put(func_addComment, "addComment");
        WEBKEY_FUNC_COMMON_MAP.put(func_addPayPass, "addPayPass");
        WEBKEY_FUNC_COMMON_MAP.put(func_getCancelReason, "getCancelReason");
        WEBKEY_FUNC_COMMON_MAP.put(func_searchCollectNews, "searchCollectNews");
        WEBKEY_FUNC_COMMON_MAP.put(func_getRecommend, "getRecommend");
        WEBKEY_FUNC_COMMON_MAP.put(func_updateCommentStatus, "updateCommentStatus");
        WEBKEY_FUNC_COMMON_MAP.put(func_getSysMsg, "getSysMsg");


    }

    public static final Map<Integer, String> WEBKEY_FUNC_HUAN_MAP = new HashMap<Integer, String>();
    static {
        WEBKEY_FUNC_HUAN_MAP.put(func_getCollectExpert, "getCollectExpert");
        WEBKEY_FUNC_HUAN_MAP.put(func_getPatientList, "getPatientList");
        WEBKEY_FUNC_HUAN_MAP.put(func_getPatientById, "getPatientById");
        WEBKEY_FUNC_HUAN_MAP.put(func_addPatient, "addPatient");
        WEBKEY_FUNC_HUAN_MAP.put(func_updatePatient, "updatePatient");
        WEBKEY_FUNC_HUAN_MAP.put(func_patient, "patient");
        WEBKEY_FUNC_HUAN_MAP.put(func_updateHuan, "updateHuan");
        WEBKEY_FUNC_HUAN_MAP.put(func_getOrders, "getOrders");
        WEBKEY_FUNC_HUAN_MAP.put(func_getOrdersById, "getOrdersById");
        WEBKEY_FUNC_HUAN_MAP.put(func_getOrdersMsg, "getOrdersMsg");
        WEBKEY_FUNC_HUAN_MAP.put(func_updateOrdersMsg, "updateOrdersMsg");
        WEBKEY_FUNC_HUAN_MAP.put(func_updatePassword, "updatePassword");
        WEBKEY_FUNC_HUAN_MAP.put(func_makeOrder, "makeOrder");
        WEBKEY_FUNC_HUAN_MAP.put(func_cancelOrder, "cancelOrder");
        WEBKEY_FUNC_HUAN_MAP.put(func_updateMobile, "updateMobile");
        WEBKEY_FUNC_HUAN_MAP.put(func_updateMember, "updateMember");
        WEBKEY_FUNC_HUAN_MAP.put(func_searchCollectExpert, "searchCollectExpert");



    }
}
