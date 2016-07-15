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
	 *            true:��ʾ�ȴ�diglog
	 * @param isShowMessage
	 *            true:��ʾ��ʾ��Ϣ
	 * @param titleRes
	 *            string xml �е���Դid,������ʾ��Ϣ��������
	 * @param messageInfo
	 *            string xml �����쳣�����������ʾ
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
			// �쳣ʱ�򷵻���Ϣ
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
	 * �����Ҫ�ر�dialogͬʱ��Ҫִ����������ʱ�����´η���
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
	 * ��ȡ����ʧ�ܼ��쳣ʱ���ô˷��� ��д���෽����������������ʱ�Զ���ȡ�÷�����
	 */
	@Override
	public void onError(Throwable throwable, boolean flag) {
		// TODO Auto-generated method stub
		if (isShowDialog) {
			DialogUtils.closeDialog();
		}
		state = -1;
		if (isShowMessage) {
			DialogUtils.showErrorDialog(activity, messageInfo == "" ? "�����쳣,����ʧ��" : messageInfo, getDialogMessageListener(), title);
		}
	}

	@Override
	public void onFinished() {
		// TODO Auto-generated method stub
	}

}
