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
                //return new IdentityInfoFragment();
            case FragKey.identify_new_fragment:
               // return new IdentityInfoNewFragment();
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


        }
      return null;
    }
}
