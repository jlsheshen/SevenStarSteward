package com.sevenstar.steward2016.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.pay.PayResult;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class PayActivity extends BannerBaseActivity {

    private static final int SDK_PAY_FLAG = 1;
    @ViewInject(R.id.product_subject)
    TextView nameTV;
    @ViewInject(R.id.product_content)
    TextView contentTV;
    @ViewInject(R.id.product_price)
    TextView priceTV;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        HttpSendMananger.sendPost(GlobalUrl.installXiaoFeiOrder, new BaseSimpleHttpCallback(getActivity(), true, false) {
                            @Override
                            public void onSuccess(JSONObject data) {
//                                if (data != null) {
//                                    Intent data=new Intent();
//                                    data.putExtra("OrderID",jsonObj.getString("OrderID"));
//                                    setResult(0x1000,data);
                                    finish();
//                                }
                            }
                            //jsonObj.getString("Price")
                        }, "UserID", StarApplication.userInfo.getString("UserID"),"Type","1");
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    private JSONObject jsonObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContextView(R.layout.pay_main);
        x.view().inject(this);
        setColumnText("我的付款");
        initData();
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String json = extras.getString("json");
            if (OtherUtils.isNotEmpty(json)) {
                jsonObj = JSONObject.parseObject(json);
                ViewUtils.setTextData(nameTV, jsonObj.getString("Name"));
                ViewUtils.setTextData(contentTV, jsonObj.getString("Content"));
                ViewUtils.setTextData(priceTV, jsonObj.getString("Price") + "元");
            } else {
                finish();
            }
        }
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(View v) {
        HttpSendMananger.sendPost(GlobalUrl.getPayOrderInfo, new BaseSimpleHttpCallback(this, true, false) {
            @Override
            public void onSuccess(JSONObject data) {
                if (data != null) {
                    final String payInfo = data.getString("data");
                    new Thread() {
                        @Override
                        public void run() {
                            // 构造PayTask 对象
                            PayTask alipay = new PayTask(PayActivity.this);
                            // 调用支付接口，获取支付结果
                            String result = alipay.pay(payInfo, true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    }.start();
                }
            }
            //jsonObj.getString("Price")
        }, "UserID", StarApplication.userInfo.getString("UserID"), "Name", jsonObj.getString("Name"), "Content", jsonObj.getString("Content"), "Price", "0.01", "OrderID", jsonObj.getString("OrderID"), "Type", jsonObj.getString("Type"));
    }


    @Override
    public Activity getActivity() {
        return this;
    }
}
