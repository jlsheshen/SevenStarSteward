package com.sevenstar.steward2016.callback;

import java.io.File;
import org.xutils.common.Callback;
import android.app.Activity;
import android.app.ProgressDialog;
import com.lib.shiguotao.utils.ToastUtil;

public class FileCallBack implements Callback.ProgressCallback<File> {

	public int state = -1;
	private ProgressDialog progressDialog;
	private Activity activity;

	public FileCallBack(Activity activity) {
		this.activity = activity;
		progressDialog = ProgressDialog.show(activity, "正在加载...", "请等待...", false, true);
		progressDialog.setMax(100);
	}

	private void colseDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	@Override
	public void onSuccess(File data) {
		colseDialog();
	}

	@Override
	public void onCancelled(CancelledException arg0) {
		// TODO Auto-generated method stub
		colseDialog();
	}

	@Override
	public void onError(Throwable arg0, boolean arg1) {
		// TODO Auto-generated method stub
		colseDialog();
		ToastUtil.showInfo(activity, "网络异常,下载失败");
	}

	@Override
	public void onFinished() {
		// TODO Auto-generated method stub
		colseDialog();
	}

	@Override
	public void onLoading(long total, long current, boolean isDownloading) {
		// TODO Auto-generated method stub
		if (total > 0 && progressDialog != null && progressDialog.isShowing()) {
			progressDialog.setProgress((int) (current * 100 / total));
		}
	}

	@Override
	public void onStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWaiting() {
		// TODO Auto-generated method stub

	}

}
