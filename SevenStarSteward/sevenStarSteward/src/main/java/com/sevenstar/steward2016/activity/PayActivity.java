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
                     * ͬ�����صĽ��������õ�����˽�����֤����֤�Ĺ����뿴https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) �����̻������첽֪ͨ
                     */
                    String resultInfo = payResult.getResult();// ͬ��������Ҫ��֤����Ϣ

                    String resultStatus = payResult.getResultStatus();
                    // �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayActivity.this, "֧���ɹ�", Toast.LENGTH_SHORT).show();
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
                        // �ж�resultStatus Ϊ��"9000"��������֧��ʧ��
                        // "8000"����֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayActivity.this, "֧�����ȷ����", Toast.LENGTH_SHORT).show();

                        } else {
                            // ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
                            Toast.makeText(PayActivity.this, "֧��ʧ��", Toast.LENGTH_SHORT).show();

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
        setColumnText("�ҵĸ���");
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
                ViewUtils.setTextData(priceTV, jsonObj.getString("Price") + "Ԫ");
            } else {
                finish();
            }
        }
    }

    /**
     * call alipay sdk pay. ����SDK֧��
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
                            // ����PayTask ����
                            PayTask alipay = new PayTask(PayActivity.this);
                            // ����֧���ӿڣ���ȡ֧�����
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
