package com.sevenstar.steward2016.fragment;

import android.content.Context;
import android.util.AttributeSet;
import com.lib.shiguotao.R;
import com.lib.shiguotao.fragment.FragmentBaseTabHost;
import com.lib.shiguotao.utils.ConvertUtils;

public class StarFragmentBaseTabHost extends FragmentBaseTabHost {

	public StarFragmentBaseTabHost(Context context) {
		super(context);
	}

	public StarFragmentBaseTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected int getMainTabBackgroundDrawable() {
		// TODO Auto-generated method stub
		return R.color.white;
	}

	@Override
	protected int getTabWidgetHeight() {
		return ConvertUtils.dp2px(getContext(), 60);
	}

	@Override
	protected boolean isCoverBottom() {
		return false;
	}

	@Override
	protected boolean isShowTabWidget() {
		return true;
	}

}
