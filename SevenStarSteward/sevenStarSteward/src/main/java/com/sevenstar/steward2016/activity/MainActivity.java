package com.sevenstar.steward2016.activity;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.constant.GlobalTabText;
import com.lib.shiguotao.manager.AppManager;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.SharedPreferencesUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.fragment.StarFragmentBaseTabHost;
import com.sevenstar.steward2016.fragment.main.FitnessFragment;
import com.sevenstar.steward2016.fragment.main.HappyShopFragment;
import com.sevenstar.steward2016.fragment.main.HomeFragment;
import com.sevenstar.steward2016.fragment.main.SafetyFragment;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.view.TabStarButton;
import com.videogo.openapi.EZOpenSDK;

/**
 * ������ ACTIVITY
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity implements OnClickListener {
    public static boolean isShowMyFitness;
    private boolean exitFlag;
    private long lastTime;
    @ViewInject(android.R.id.tabhost)
    StarFragmentBaseTabHost mTabHost;
    @ViewInject(R.id.home_phone_icon_iv)
    ImageView phoneIV;
    @ViewInject(R.id.home_my_head_image_icon_iv)
    ImageView myHeadIV;
    @ViewInject(R.id.main_activity_title_tv)
    TextView titleTV;
    private boolean isLoginIng = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EZOpenSDK.getInstance().setAccessToken("at.72h2fyyy0djbely85kf7axu44jh5h4cs-1vvbfyrl8j-1liuxca-zpcmvblco");

        x.view().inject(this);
        logIn();
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        phoneIV.setOnClickListener(this);
        myHeadIV.setOnClickListener(this);
        initBottomFragment();
        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                String title = "";
                if (GlobalTabText.TAB_ONE_TEXT.equals(tabId)) {
                    title = "���ǹܼ�";
                } else if (GlobalTabText.TAB_TWO_TEXT.equals(tabId)) {
                    title = "��ȫ";
                } else if (GlobalTabText.TAB_THREE_TEXT.equals(tabId)) {
                    title = "����";
                } else if (GlobalTabText.TAB_FOUR_TEXT.equals(tabId)) {
                    title = "�ֹ�";
                }
                titleTV.setText(title);
            }
        });
    }

    private void initBottomFragment() {
        addFragment(GlobalTabText.TAB_ONE_TEXT, R.drawable.tab_one_page_icon_selector, HomeFragment.class);
        addFragment(GlobalTabText.TAB_TWO_TEXT, R.drawable.tab_two_page_icon_selector, SafetyFragment.class);
        addFragment(GlobalTabText.TAB_THREE_TEXT, R.drawable.tab_three_page_icon_selector, FitnessFragment.class);
        addFragment(GlobalTabText.TAB_FOUR_TEXT, R.drawable.tab_four_page_icon_selector, HappyShopFragment.class);
        mTabHost.setCurrentTabByTag(GlobalTabText.TAB_ONE_TEXT);
    }

    public void addFragment(String tabSpec, int drawable, Class<?> mFragment) {
        mTabHost.addTab(mTabHost.newTabSpec(tabSpec).setIndicator(new TabStarButton(this, drawable)), mFragment, null);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (exitFlag) {
            if (currentTime - lastTime < 2000) {
                System.exit(0);
                return;
            } else {
                exitFlag = false;
            }
        } else {
            exitFlag = true;
        }
        lastTime = currentTime;
        ToastUtil.showInfo(this, R.string.press_once_finish);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.home_phone_icon_iv:
                IntentUtils.dialPhone(getBaseContext(), R.string.main_phone_number);
                break;
            case R.id.home_my_head_image_icon_iv:
                if (isLoginIng) {
                    ToastUtil.showInfo(this, "���ڵ�¼���Ժ�...");
                } else {
                    if (StarApplication.userInfo != null) {
                        IntentUtils.goTo(getBaseContext(), PersonalCenterActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
                    } else {
                        IntentUtils.goTo(getBaseContext(), LoginActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                }
                break;
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if (extras != null) {
            int type = extras.getInt("type");
            switch (type) {
                case -1:
                    AppManager.getInstance().removeAllActivity();
                    finish();
                    System.exit(0);
                    break;
                case 2:
                    isShowMyFitness = true;
                    mTabHost.setCurrentTabByTag(GlobalTabText.TAB_THREE_TEXT);
                    break;
            }
        }
    }

    private void logIn() {
        String loginName = SharedPreferencesUtils.getStringDate("loginName");
        String password = SharedPreferencesUtils.getStringDate("password");
        if (OtherUtils.isNotEmpty(loginName) && OtherUtils.isNotEmpty(password)) {
            isLoginIng = true;
            HttpSendMananger.sendPost(GlobalUrl.USER_LOGIN_URL, new BaseSimpleHttpCallback(this, false, false) {
                @Override
                public void onSuccess(JSONObject data) {
                    if (data != null) {
                        StarApplication.userInfo = data;
                    }
                    isLoginIng = false;
                }

                @Override
                public void onFinished() {
                    super.onFinished();
                    isLoginIng = false;
                }

                @Override
                public void onCancelled(CancelledException arg0) {
                    super.onCancelled(arg0);
                    isLoginIng = false;
                }

                @Override
                public void onError(Throwable arg0, boolean arg1) {
                    super.onError(arg0, arg1);
                    isLoginIng = false;
                }
            }, "LoginName", loginName, "Password", password);
        }
    }

}
