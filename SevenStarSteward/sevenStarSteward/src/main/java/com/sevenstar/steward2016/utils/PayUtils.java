package com.sevenstar.steward2016.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.lib.shiguotao.pay.PayResult;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.callback.PayCallBack;
import com.sevenstar.steward2016.manager.HttpSendMananger;

/**
 * ֧��������
 */
public class PayUtils {

    private static final int SDK_PAY_FLAG = 1;

    private static Handler handler = new Handler() {};


    public static void pay(String url, final Activity activity, final JSONObject jsonObj,final PayCallBack payCallBack) {
        HttpSendMananger.sendPost(url, new BaseSimpleHttpCallback(activity, true, false) {
            @Override
            public void onSuccess(JSONObject data) {
                if (data != null) {
                    final String payInfo = data.getString("data");
                    new Thread() {
                        @Override
                        public void run() {
                            // ����PayTask ����
                            PayTask alipay = new PayTask(activity);
                            // ����֧���ӿڣ���ȡ֧�����
                          final  String result = alipay.pay(payInfo, true);
                          handler.post(new Runnable() {
                              @Override
                              public void run() {
                                  PayResult payResult = new PayResult(result);
                                  /**
                                   * ͬ�����صĽ��������õ�����˽�����֤����֤�Ĺ����뿴https://doc.open.alipay.com/doc2/
                                   * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                                   * docType=1) �����̻������첽֪ͨ
                                   */
                                  String resultInfo = payResult.getResult();// ͬ��������Ҫ��֤����Ϣ

                                  String resultStatus = payResult.getResultStatus();
                                  // �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
                                  if (TextUtils.equals(resultStatus, "9000")) {
//                                      Toast.makeText(activity, "֧���ɹ�", Toast.LENGTH_SHORT).show();
//                                      Intent data=new Intent();
//                                      data.putExtra("OrderID",jsonObj.getString("OrderID"));
//                                      activity.setResult(0x1000,data);
//                                      activity.finish();
                                      payCallBack.onSuccess();
                                  } else {
                                      // �ж�resultStatus Ϊ��"9000"��������֧��ʧ��
                                      // "8000"����֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
                                      if (TextUtils.equals(resultStatus, "8000")) {
                                          Toast.makeText(activity, "֧�����ȷ����", Toast.LENGTH_SHORT).show();

                                      } else {
                                          // ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
                                          Toast.makeText(activity, "֧��ʧ��", Toast.LENGTH_SHORT).show();
                                      }
                                  }
                              }
                          });
                        }
                    }.start();
                }
            }
            //jsonObj.getString("Price")
        }, "UserID", StarApplication.userInfo.getString("UserID"), "Name", jsonObj.getString("Name"), "Content", jsonObj.getString("Content"), "Price", "0.01", "OrderID", jsonObj.getString("OrderID"), "Type", jsonObj.getString("Type"));
    }
}
