package com.zhjydy_doc.view.fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/5 0005.
 */
public class FragKey {

    public static final int search_expert_fragment = 1001;
    public static final int search_info_fragment = 1002;
    public static final int detail_expert_fragment = 1003;
    public static final int detail_info_fragment = 1004;
    public static final int detail_order_fragment = 1005;
    public static final int account_safe_fragment = 1006;
    public static final int pay_password_change_fragment = 1007;
    public static final int phone_num_change_fragment = 1008;
    public static final int common_fragment = 1009;
    public static final int identify_info_fragment = 1010;
    public static final int identify_new_fragment = 1017;

    public static final int mine_info_fragment = 1011;
    public static final int msg_all_fragment = 1012;
    public static final int password_change_fragment = 1013;
    public static final int patient_case_fragment = 1014;
    public static final int patient_case_detail_fragment = 1015;
    public static final int patient_case_edit_fragment = 1016;
    public static final int login_password_change_fragment = 1018;
    public static final int msg_order_list_fragment = 1019;
    public static final int system_order_list_fragment = 1020;
    public static final int doc_chat_record_fragment = 1021;
    public static final int patient_case_edit_attach_fragment = 1022;
    public static final int fave_expert_list_fragment = 1023;
    public static final int fave_info_list_fragment = 1024;
    public static final int search_home_fragment = 1025;
    public static final int pay_password_add_fragment = 1026;
    public static final int order_confirm_fragment = 1027;
    public static final int patient_case_select_fragment = 1028;
    public static final int order_cancel_fragment = 1029;
    public static final int mine_name_change_fragment = 1030;

    public static final Map<Integer,String> FragMap = new HashMap<>();
    static
    {
        FragMap.put(patient_case_select_fragment, "patient_case_select_fragment");
        FragMap.put(order_cancel_fragment, "order_cancel_fragment");

        FragMap.put(order_confirm_fragment, "order_confirm_fragment");
        FragMap.put(detail_order_fragment, "detail_order_fragment");
        FragMap.put(fave_expert_list_fragment, "fave_expert_list_fragment");
        FragMap.put(fave_info_list_fragment, "fave_info_list_fragment");
        FragMap.put(search_expert_fragment, "search_expert_fragment");
        FragMap.put(search_info_fragment, "search_info_fragment");
        FragMap.put(detail_expert_fragment, "detail_expert_fragment");
        FragMap.put(detail_info_fragment, "detail_info_fragment");
        FragMap.put(account_safe_fragment, "account_safe_fragment");
        FragMap.put(pay_password_change_fragment, "pay_password_change_fragment");
        FragMap.put(pay_password_add_fragment, "pay_password_add_fragment");

        FragMap.put(common_fragment, "common_fragment");
        FragMap.put(phone_num_change_fragment, "phone_num_change_fragment");
        FragMap.put(identify_info_fragment, "identify_info_fragment");
        FragMap.put(identify_new_fragment, "identify_new_fragment");

        FragMap.put(mine_info_fragment, "mine_info_fragment");
        FragMap.put(msg_all_fragment, "msg_all_fragment");
        FragMap.put(password_change_fragment, "password_change_fragment");
        FragMap.put(patient_case_fragment, "patient_case_fragment");
        FragMap.put(patient_case_detail_fragment, "patient_case_detail_fragment");
        FragMap.put(patient_case_edit_fragment, "patient_case_edit_fragment");
        FragMap.put(login_password_change_fragment, "login_password_change_fragment");
        FragMap.put(msg_order_list_fragment, "msg_order_list_fragment");
        FragMap.put(system_order_list_fragment, "system_order_list_fragment");
        FragMap.put(doc_chat_record_fragment, "doc_chat_record_fragment");
        FragMap.put(patient_case_edit_attach_fragment, "patient_case_edit_attach_fragment");
        FragMap.put(search_home_fragment, "search_home_fragment");
        FragMap.put(mine_name_change_fragment, "mine_name_change_fragment");

    }
}
