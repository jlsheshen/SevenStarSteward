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
 * 支付工具类
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
                            // 构造PayTask 对象
                            PayTask alipay = new PayTask(activity);
                            // 调用支付接口，获取支付结果
                          final  String result = alipay.pay(payInfo, true);
                          handler.post(new Runnable() {
                              @Override
                              public void run() {
                                  PayResult payResult = new PayResult(result);
                                  /**
                                   * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                                   * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                                   * docType=1) 建议商户依赖异步通知
                                   */
                                  String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                                  String resultStatus = payResult.getResultStatus();
                                  // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                                  if (TextUtils.equals(resultStatus, "9000")) {
//                                      Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
//                                      Intent data=new Intent();
//                                      data.putExtra("OrderID",jsonObj.getString("OrderID"));
//                                      activity.setResult(0x1000,data);
//                                      activity.finish();
                                      payCallBack.onSuccess();
                                  } else {
                                      // 判断resultStatus 为非"9000"则代表可能支付失败
                                      // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                                      if (TextUtils.equals(resultStatus, "8000")) {
                                          Toast.makeText(activity, "支付结果确认中", Toast.LENGTH_SHORT).show();

                                      } else {
                                          // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                          Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
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
