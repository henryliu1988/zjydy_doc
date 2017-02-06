package com.zhjydy_doc.view.fragment;

/**
 * Created by admin on 2016/8/9.
 */
public class PagerFragmentFactory
{
    public static PageImpBaseFragment createFragment(int key)
    {
        switch (key){
            case FragKey.search_expert_fragment:
                return new SearchExpertFragment();
            case FragKey.search_info_fragment:
                return new SearchInfoFragment();
            case FragKey.detail_info_fragment:
                return new InfoDetailFragment();
            case FragKey.detail_expert_fragment:
                return new ExpertDetailFragment();
            case FragKey.detail_order_fragment:
                return new OrderDetailFragment();
            case FragKey.pay_password_change_fragment:
                return new PayPasswordChangeFragment();
            case FragKey.phone_num_change_fragment:
                return new PhoneNumChangeFragment();
            case FragKey.common_fragment:
                return new CommonFragment();
            case FragKey.identify_info_fragment:
                return new IdentityInfoFragment();
            case FragKey.identify_new_fragment:
                return new IdentityInfoNewFragment();
            case FragKey.mine_info_fragment:
                return new MineInfoFragment();
            case FragKey.account_safe_fragment:
                return new AccountSafeFragment();
            case FragKey.msg_all_fragment:
                return new MsgAllListFragment();
            case FragKey.password_change_fragment:
                return new PasswordChangeFragment();
            case FragKey.login_password_change_fragment:
                return new LoginPasswordChangeFragment();
            case FragKey.msg_order_list_fragment:
                return new OrderMsgListFragment();
            case FragKey.system_order_list_fragment:
                return new SystemMsgListFragment();
            case FragKey.doc_chat_record_fragment:
                return new DocChatRecordFragment();
            case FragKey.fave_info_list_fragment:
                return new FaveInfoFragment();
            case FragKey.search_home_fragment:
                return new SearchHomeFragment();
            case FragKey.pay_password_add_fragment:
                return new PayPasswordAddFragment();
            case FragKey.mine_name_change_fragment:
                return new MineNameChangeFragment();
            case FragKey.mine_star_fragment:
                return new MineStarFragment();
            case FragKey.comment_list_fragment:
                return new CommentListFragment();
            case FragKey.fans_list_fragment:
                return new FansListFragment();
            case FragKey.order_subsribe_detail_fragment:
                return new OrderSubsribeDetailFragment();
            case FragKey.patient_case_detail_fragment:
                return new PatientCaseDetailFragment();
            case FragKey.order_accept_fragment:
                return new OrderAcceptFragment();
            case FragKey.order_detail_fragment:
                return new OrderDetailFragment();
            case FragKey.order_reject_reason_fragment:
                return new BackRejectReasonFragment();
            case FragKey.expert_office_filter_list_fragment:
                return new ExpertOfficeListFragment();
            case FragKey.order_reject_fragment:
                return new OrderRejectFragment();
            case FragKey.order_money_reject_fragment:
                return new OrderMoneyRejectFragment();
            case FragKey.about_app_main_fragment:
                return new AboutFragment();
            case FragKey.about_app_kefu_fragment:
                return new AboutKefuFragment();
            case FragKey.about_app_advice_fragment:
                return new AboutAdviceFragment();

        }
      return null;
    }
}
