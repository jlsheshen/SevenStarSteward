package com.sevenstar.steward2016.callback;

/**
 * Created by Administrator on 2016/5/29.
 */
public interface PayCallBack {
    /**
     * 支付成功
     */
    void onSuccess();
    /**
     * 支付失败
     */
    void onFailure();
}
