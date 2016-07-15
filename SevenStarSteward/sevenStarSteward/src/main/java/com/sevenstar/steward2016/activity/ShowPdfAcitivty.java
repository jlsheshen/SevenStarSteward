package com.sevenstar.steward2016.activity;

import java.io.File;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.LogUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.SharedPreferencesUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.callback.FileCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;

public class ShowPdfAcitivty extends BannerBaseActivity implements OnPageChangeListener, OnClickListener {
	int pageNumber = 1;
	@ViewInject(R.id.pdfView)
	PDFView pdfView;
	@ViewInject(R.id.banner_Base_activity_head_right_text_view)
	TextView rightTV;
	private int type = 0; // 0评估,1合同
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.show_pdf_layout);
		x.view().inject(this);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			type = extras.getInt("type");
			id = extras.getString("id");
			boolean isShow = extras.getBoolean("isShow");
			setColumnText(extras.getString("title"));
			String url = extras.getString("url");
			if (isShow) {
				ViewUtils.setVisibility(View.VISIBLE, rightTV);
				ViewUtils.setTextData(rightTV, "确认");
				ViewUtils.setOnClickListener(rightTV, this);
			}
			if (OtherUtils.isEmpty(url)) {
				ToastUtil.showInfo(getActivity(), "文件不存在");
			} else {
				display(url, true);
			}
		} else {
			finish();
		}
	}

	private void display(String url, boolean jumpToFirstPage) {
		if (jumpToFirstPage)
			pageNumber = 1;
		HttpSendMananger.getFile(url, new FileCallBack(getActivity()) {
			@Override
			public void onSuccess(File data) {
				super.onSuccess(data);
				if (data != null) {
					try {
						pdfView.fromFile(data).defaultPage(pageNumber).onPageChange(ShowPdfAcitivty.this).enableSwipe(true)
								.showMinimap(true).load();
					} catch (Exception e) {
						LogUtils.showErrorMessage(e);
						ToastUtil.showInfo(getActivity(), "解析PDF文档失败");
					}
				}
			}
		});
	}

	@Override
	public void onPageChanged(int page, int pageCount) {

	}

	@Override
	public void finish() {
		setResult(0x1000);
		super.finish();
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String str = type == 0 ? "评估" : "合同";
		DialogUtils.showInfoDialog(getActivity(), new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String state = type == 0 ? "2" : "1";
				HttpSendMananger.sendPost(GlobalUrl.EDIT_PING_GU_HE_TONG_STATE, new BaseSimpleHttpCallback(getActivity(), true, true) {

					@Override
					public OnClickListener getDialogMessageListener() {
						// TODO Auto-generated method stub
						return new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								if (state == 0) {
									finish();
								}
							}
						};
					}
				}, "id", id, "type", String.valueOf(type), "state", state);
			}
		}, "是否确认" + str, null, "确认", "取消", false);
	}

}
