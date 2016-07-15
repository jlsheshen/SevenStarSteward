package com.sevenstar.steward2016.view;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lib.shiguotao.callBack.HttpFailCallBack;
import com.lib.shiguotao.view.LoadDataBaseErrorLayout;
import com.sevenstar.steward2016.R;

public class LoadDataErrorLayout extends LoadDataBaseErrorLayout {

	@ViewInject(R.id.loding_not_data_tv)
	TextView notDataTV;
	@ViewInject(R.id.loding_waitting_progressbar)
	ProgressBar waittingPB;

	public LoadDataErrorLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		x.view().inject(this);
	}

	public LoadDataErrorLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		x.view().inject(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.load_data_error_layout;
	}

	@Override
	public void showNetworkErrorLayout(int type, HttpFailCallBack callBack) {
		// TODO Auto-generated method stub
		hideWaitLayout();
		notDataTV.setText("数据加载错误");

	}

	@Override
	public void showNoDataLayout(int type) {
		// TODO Auto-generated method stub
		hideWaitLayout();
		notDataTV.setText("暂无数据");
		notDataTV.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideNetworkErrorLayout() {
		notDataTV.setVisibility(View.GONE);
	}

	@Override
	public void showWaitLayout() {
		// TODO Auto-generated method stub
		waittingPB.setIndeterminate(true);
		waittingPB.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideWaitLayout() {
		// TODO Auto-generated method stub
		waittingPB.setIndeterminate(false);
		waittingPB.setVisibility(View.GONE);
	}

}
