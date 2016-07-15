package com.sevenstar.steward2016.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.manager.AppManager;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.callback.PayCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.utils.PayUtils;

/**
 * 休闲旅游及我的行程 内容详情页面
 */
public class LeisureTourismContentActivity extends BannerBaseActivity implements OnClickListener {
    @ViewInject(R.id.banner_Base_activity_head_right_text_view)
    TextView rightTV;
    @ViewInject(R.id.text_one)
    TextView textOne;
    @ViewInject(R.id.text_two)
    TextView textTwo;
    @ViewInject(R.id.text_three)
    TextView textThree;
    @ViewInject(R.id.text_five)
    TextView textFive;
    @ViewInject(R.id.text_six)
    TextView textSix;
    @ViewInject(R.id.text_seven)
    TextView textSeven;
    @ViewInject(R.id.text_eight)
    TextView textEight;
    @ViewInject(R.id.activity_image_iv)
    ImageView imageIV;
    private JSONObject jsonObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContextView(R.layout.activity_leisure_tourism_content_page);
        x.view().inject(this);
        initData();
    }

    private void initData() {
        // TODO Auto-generated method stub
        Bundle extras = IntentUtils.getIntentBundle(this);
        if (extras != null) {
            String json = extras.getString("data");
            int type = extras.getInt("type", 0);
            if (type == 0) {
                setColumnText("休闲旅游");
                ViewUtils.setVisibility(View.VISIBLE, rightTV);
                ViewUtils.setTextData(rightTV, "立即下单");
                ViewUtils.setOnClickListener(rightTV, this);
            }else{
                setColumnText("我的行程");
            }
            if (OtherUtils.isNotEmpty(json)) {
                jsonObj = JSONObject.parseObject(json);
                if (jsonObj != null) {
                    ViewUtils.setTextData(textOne, jsonObj.getString("TypeName"));
                    ViewUtils.setTextData(textTwo, jsonObj.getString("Price") + "元");
                    ViewUtils.setTextData(textThree, jsonObj.getString("Title"));
                    ViewUtils.setTextData(textFive, jsonObj.getString("Date") + "\t" + jsonObj.getString("Time"));
                    ViewUtils.setTextData(textSix, jsonObj.getString("Introduction"));
                    ViewUtils.setTextData(textSeven, "提供商:\t" + jsonObj.getString("ProviderName"));
                    ViewUtils.setTextData(textEight, "组织者:\t" + jsonObj.getString("Organizer"));
                    x.image().bind(imageIV, GlobalUrl.ADDR + jsonObj.getString("Picture"));
                }
            }
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onClick(View v) {
        if (StarApplication.userInfo == null) {
            ToastUtil.showInfo(getActivity(), "您未登录,不能进行此操作");
            return;
        }



        DialogUtils.showInfoDialog(getActivity(), new OnClickListener() {

            @Override
            public void onClick(View v) {


                JSONObject tempData = new JSONObject();
                tempData.put("Price",jsonObj.getString("Price"));
                tempData.put("Name",jsonObj.getString("TypeName"));
                tempData.put("Content",jsonObj.getString("Introduction"));
                tempData.put("OrderID",jsonObj.getString("TripID"));
                PayUtils.pay(GlobalUrl.getPayOrderInfo, getActivity(), tempData, new PayCallBack() {

                    @Override
                    public void onSuccess() {
                        HttpSendMananger.sendPost(GlobalUrl.INSTALL_Live_ORDER_URL, new BaseSimpleHttpCallback(getActivity(), true, true) {
                            @Override
                            public void onSuccess(JSONObject data) {

                            }

                            @Override
                            public OnClickListener getDialogMessageListener() {
                                // TODO Auto-generated method stub
                                return new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        if (state == 0) {
                                            AppManager.getInstance().removeAllActivity();
                                        }
                                    }
                                };
                            }
                        }, "serviceType", "4", "UserID", StarApplication.userInfo.getString("UserID"), "MyID", jsonObj.getString("TripID"));
                    }

                    @Override
                    public void onFailure() {

                    }
                });



            }
        }, "确认是否下此订单", null, null, null, false);
    }

}
