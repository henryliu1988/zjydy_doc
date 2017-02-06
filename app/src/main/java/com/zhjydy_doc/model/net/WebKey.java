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
    public static final String WEBKEY_URL_ZHUAN = "http://59.110.24.36/yiduyi/index.php?s=App/Zhuan/index";

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
    public static final int func_cancelCollectExpert = 1038;  //取消收藏专家
    public static final int func_getRecommendZhuan = 1009;  //首页推荐专家
    public static final int func_getExpertsList = 1010;  //专家列表
    public static final int func_getExpert = 1011;  //专家详情
    public static final int func_getNewsById = 1012;  //资讯详情
    public static final int func_getCommentList = 1013;  //根据专家id获取留言列表
    public static final int func_searchExpertsList = 1014;  //搜索专家列表
    public static final int func_searchNewsList = 1015;  //搜索资讯列表
    public static final int func_cancelCollectNews = 1039;  //取消收藏资讯
    public static final int func_collectExpert = 1016;  //收藏专家
    public static final int func_collectNews = 1022;  //收藏资讯
    public static final int func_getoffice = 1023;  //科室字典
    public static final int func_getbusiness = 1024;  //职称字典
    public static final int func_getHospital = 1025;  //医院字典
    public static final int func_getpro = 1026;  //省份字典
    public static final int func_getCity = 1027;  //城市字典
    public static final int func_getQu = 1028;  //地區字典
    public static final int func_getCollectNews = 1032;  //获取收藏资讯
    public static final int func_getOrders = 1034;  //全部订单
    public static final int func_getOrdersById = 1035;  //订单详情
    public static final int func_getOrdersMsg = 1036;  //订单消息
    public static final int func_updateOrdersMsg = 1037;  //标记订单消息为已读
    public static final int func_addComment = 1040;  //添加留言
    public static final int func_updatePassword = 1041;  //修改登录密码
    public static final int func_addPayPass = 1042;  //修改支付密码
    public static final int func_getNewCommentList = 1044;  //获取最新留言列表
    public static final int func_getComment = 1045;  //获取留言列表
 //   public static final int func_cancelOrder = 1046;  //预约专家

    public static final int func_updateMember = 1048;  //关注专家

    public static final int func_updateMobile = 1047;  //修改手机号
    public static final int func_getCancelReason = 1049;  //取消预约原因列表
//    public static final int func_searchCollectExpert = 1050;  //
    public static final int func_searchCollectNews = 1051;  //
    public static final int func_getRecommend = 1052;  //首页推荐专家
    public static final int func_updateCommentStatus = 1053;  //修改留言状态
    public static final int func_getSysMsg = 1054;  //获取系统消息

    public static final int func_getOrderTrue = 1055;  //专家接受预约，回复会诊时间
    public static final int func_rebackzhi = 1056;  //专家回复治疗会诊信息
    public static final int func_updateExpertInformation = 1057;  //专家信息更新
    public static final int func_updateAuthent = 1058;  //专家认证信息
    public static final int func_getGuanExperts = 1059;  //获取关注的专家列表
    public static final int func_getMyfans = 1060;  //获取我的粉丝
    public static final int func_getMeAndExpert = 1061;  //获取互相关注的专家列表
    public static final int func_GuanExperts = 1062;  //关注专家
    public static final int func_cancelGuanExperts = 1063;  //关注专家
    public static final int func_getGuanbyid = 1064; // 根据双方id获取关注情况
    public static final int func_getExpertStatusbyid = 1065;  //根据专家id获取认证情况


    public static final int func_getPatientById = 1066;  //根据患者病例Id获取详情
    public static final int func_readMyfans = 1067;  //根据患者病例Id获取详情
    public static final int func_getExpertOrderCancelReason = 1068;  //根据患者病例Id获取详情
    public static final int func_expert_cancelorder = 1069;  //根据患者病例Id获取详情
    public static final int func_intoHui = 1070;  //根据患者病例Id获取详情
    public static final int func_intoZhi = 1071;  //根据患者病例Id获取详情
    public static final int func_closeZhi = 1072;  //根据患者病例Id获取详情
    public static final int func_getRebackMoney = 1073;  // 同意退款
    public static final int func_cancelRebackMoney = 1074;  //拒绝退款
    public static final int func_addidear = 1075;  //拒绝退款

    public static final Map<Integer, String> WEBKEY_FUNC_HUAN_MAP = new HashMap<Integer, String>();
    static {
        WEBKEY_FUNC_HUAN_MAP.put(func_getPatientById, "getPatientById");
    }

    public static final Map<Integer, String> WEBKEY_FUNC_ZHUAN_MAP = new HashMap<Integer, String>();
    static {
        WEBKEY_FUNC_ZHUAN_MAP.put(func_getRebackMoney, "getRebackMoney");//
        WEBKEY_FUNC_ZHUAN_MAP.put(func_cancelRebackMoney, "cancelRebackMoney");//
        WEBKEY_FUNC_ZHUAN_MAP.put(func_intoHui, "intoHui");//
        WEBKEY_FUNC_ZHUAN_MAP.put(func_intoZhi, "intoZhi");//
        WEBKEY_FUNC_ZHUAN_MAP.put(func_closeZhi, "closeZhi");//
        WEBKEY_FUNC_ZHUAN_MAP.put(func_expert_cancelorder, "cancelOrder");//
        WEBKEY_FUNC_ZHUAN_MAP.put(func_readMyfans, "readMyfans");//
        WEBKEY_FUNC_ZHUAN_MAP.put(func_getOrders, "getOrders");//全部订单
        WEBKEY_FUNC_ZHUAN_MAP.put(func_getOrdersById, "getOrdersById");//
        WEBKEY_FUNC_ZHUAN_MAP.put(func_getOrdersMsg, "getOrdersMsg");//getOrdersMsg
        WEBKEY_FUNC_ZHUAN_MAP.put(func_updateOrdersMsg, "updateOrdersMsg"); //updateOrdersMsg
        WEBKEY_FUNC_ZHUAN_MAP.put(func_getOrderTrue, "getOrderTrue");//专家接受预约，回复会诊时间
        WEBKEY_FUNC_ZHUAN_MAP.put(func_rebackzhi, "rebackzhi"); //.专家回复治疗会诊信息
        WEBKEY_FUNC_ZHUAN_MAP.put(func_updatePassword, "updatePassword");
      //  WEBKEY_FUNC_HUAN_MAP.put(func_makeOrder, "makeOrder");
      //  WEBKEY_FUNC_HUAN_MAP.put(func_cancelOrder, "cancelOrder");
        WEBKEY_FUNC_ZHUAN_MAP.put(func_updateMobile, "updateMobile");
        WEBKEY_FUNC_ZHUAN_MAP.put(func_updateExpertInformation, "updateExpertInformation");
        WEBKEY_FUNC_ZHUAN_MAP.put(func_updateAuthent, "updateAuthent");
        WEBKEY_FUNC_ZHUAN_MAP.put(func_getGuanExperts, "getGuanExperts");   // 获取关注的专家列表
        WEBKEY_FUNC_ZHUAN_MAP.put(func_getMyfans, "getMyfans");   // 获取我的粉丝
        WEBKEY_FUNC_ZHUAN_MAP.put(func_getMeAndExpert, "getMeAndExpert");   // 获取互相关注的专家列表
        WEBKEY_FUNC_ZHUAN_MAP.put(func_GuanExperts, "GuanExperts");   // 关注专家
        WEBKEY_FUNC_ZHUAN_MAP.put(func_cancelGuanExperts, "cancelGuanExperts");   // 关注专家

        WEBKEY_FUNC_ZHUAN_MAP.put(func_updateMember, "updateMember");
        WEBKEY_FUNC_ZHUAN_MAP.put(func_getGuanbyid, "getGuanbyid");
        WEBKEY_FUNC_ZHUAN_MAP.put(func_getExpertStatusbyid, "getExpertStatusbyid");
        WEBKEY_FUNC_ZHUAN_MAP.put(func_getExpertOrderCancelReason, "cancelOrderReason");

        //    WEBKEY_FUNC_HUAN_MAP.put(func_searchCollectExpert, "searchCollectExpert");



    }


    public static final Map<Integer, String> WEBKEY_FUNC_COMMON_MAP = new HashMap<Integer, String>();
    static {
        WEBKEY_FUNC_COMMON_MAP.put(func_addidear, "addidear");
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


}
