package com.sevenstar.steward2016.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.sevenstar.steward2016.R;
/**
 * 保洁清理类型选择界面
 */
public class CleanKeepingCleanoutActivity extends BannerBaseActivity implements OnClickListener {
	@ViewInject(R.id.clean_keeping_cleanout_head_imageview)
	ImageView clean_keeping_cleanout_head_imageview;
	@ViewInject(R.id.clean_keeping_cleanout_richang_imageview)
	View clean_keeping_cleanout_richang_imageview;
	@ViewInject(R.id.clean_keeping_cleanout_shendu_imageview)
	View clean_keeping_cleanout_shendu_imageview;
	@ViewInject(R.id.clean_keeping_cleanout_xinkaihuang_imageview)
	View clean_keeping_cleanout_xinkaihuang_imageview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_clean_keeping_cleanout_pager);
		setColumnText("保洁清洗");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		clean_keeping_cleanout_richang_imageview.setOnClickListener(this);
		clean_keeping_cleanout_shendu_imageview.setOnClickListener(this);
		clean_keeping_cleanout_xinkaihuang_imageview.setOnClickListener(this);
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String title = "";
		int type = 0;
		switch (v.getId()) {
		case R.id.clean_keeping_cleanout_richang_imageview:
			type = 1;
			title = "日常清理";
			break;
		case R.id.clean_keeping_cleanout_shendu_imageview:
			type = 2;
			title = "深度清理";
			break;
		case R.id.clean_keeping_cleanout_xinkaihuang_imageview:
			type = 3;
			title = "新居开荒";
			break;
		}
		Bundle bundle = new Bundle();
		bundle.putString("title", title);
		bundle.putInt("type", type);
		IntentUtils.goTo(this, CleaningOrderContentActivity.class, bundle);
	}

}
