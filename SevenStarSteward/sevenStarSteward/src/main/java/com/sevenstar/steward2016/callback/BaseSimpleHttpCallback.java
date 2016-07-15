package com.sevenstar.steward2016.callback;

import org.xutils.common.Callback;
import android.app.Activity;
import android.view.View.OnClickListener;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.LogUtils;

public class BaseSimpleHttpCallback implements Callback.CommonCallback<String> {

	private boolean isShowMessage;
	private Activity activity;
	private String title = "";
	private String messageInfo = "";
	public int state = -1;
	private boolean isShowDialog;

	public BaseSimpleHttpCallback(Activity activity, boolean isShowDialog, boolean isShowMessage) {
		this(activity, isShowDialog, isShowMessage, "", "");
	}

	/**
	 * 
	 * @param activity
	 * @param isShowDialog
	 *            true:显示等待diglog
	 * @param isShowMessage
	 *            true:显示提示信息
	 * @param titleRes
	 *            string xml 中的资源id,设置提示信息标题文字
	 * @param messageInfo
	 *            string xml 设置异常及网络错误提示
	 */
	public BaseSimpleHttpCallback(Activity activity, boolean isShowDialog, boolean isShowMessage, String title, String messageInfo) {
		this.activity = activity;
		this.isShowDialog = isShowDialog;
		if (isShowDialog) {
			DialogUtils.closeDialog();
			DialogUtils.showLoading(activity);
		}
		this.isShowMessage = isShowMessage;
		this.title = title;
		this.messageInfo = messageInfo;
	}

	@Override
	public void onSuccess(String data) {
		if (isShowDialog) {
			DialogUtils.closeDialog();
		}
		String message = null;
		int error = 0;
		try {
			if (data != null) {
				JSONObject json = JSONObject.parseObject(data);
				if (json != null) {
					message = json.getString("message");
					error = json.getIntValue("error");
					if (error == 100) {
						state = 0;
						JSONObject jsonObj = json.getJSONObject("data");
						onSuccess(jsonObj);
					} else {
						state = 1;
					}
				}
			}
			if (isShowMessage) {
				if (message != null && !message.equals("")) {
					DialogUtils.showErrorDialog(activity, message, getDialogMessageListener(), title);
				}
			}
		} catch (Exception e) {
			state = -1;
			LogUtils.showErrorMessage(e);
			// 异常时候返回消息
			if (isShowMessage) {
				if (message != null && !message.equals("")) {
					DialogUtils.showErrorDialog(activity, message, getDialogMessageListener(), title);
				}
			}
		}
	}

	public void onSuccess(JSONObject data) {

	}

	/**
	 * 如果需要关闭dialog同时需要执行其他操作时，重新次方法
	 * 
	 * @return
	 */
	public OnClickListener getDialogMessageListener() {
		return null;

	}

	@Override
	public void onCancelled(CancelledException arg0) {
		// TODO Auto-generated method stub
		if (isShowDialog) {
			DialogUtils.closeDialog();
		}
	}

	/**
	 * 获取数据失败及异常时调用此方法 重写父类方法（此类的子类调用时自动调取该方法）
	 */
	@Override
	public void onError(Throwable throwable, boolean flag) {
		// TODO Auto-generated method stub
		if (isShowDialog) {
			DialogUtils.closeDialog();
		}
		state = -1;
		if (isShowMessage) {
			DialogUtils.showErrorDialog(activity, messageInfo == "" ? "网络异常,操作失败" : messageInfo, getDialogMessageListener(), title);
		}
	}

	@Override
	public void onFinished() {
		// TODO Auto-generated method stub
	}

}
