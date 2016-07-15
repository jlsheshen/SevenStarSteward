package com.sevenstar.steward2016.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.IntentUtils;
import com.sevenstar.steward2016.R;
/**
 *	我的订单 界面
 */
public class MyOrderActivity extends BannerBaseActivity implements OnClickListener {
	@ViewInject(R.id.shop_order_tv)
	View shopBT;
	@ViewInject(R.id.hour_point_order_tv)
	View HourPointBT;
	@ViewInject(R.id.service_order_tv)
	View serviceBT;
	@ViewInject(R.id.my_pinggu_tv)
	View myPingGuBT;
	@ViewInject(R.id.my_hetong_tv)
	View myHeTongBT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_my_order_page);
		setColumnText("我的订单");
		x.view().inject(this);
		shopBT.setOnClickListener(this);
		HourPointBT.setOnClickListener(this);
		serviceBT.setOnClickListener(this);
		myPingGuBT.setOnClickListener(this);
		myHeTongBT.setOnClickListener(this);
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Class<?> mClass = null;
		switch (v.getId()) {
		case R.id.shop_order_tv:
			mClass = MyShopOrderActivity.class;
			break;
		case R.id.hour_point_order_tv:
			mClass = MyHourServiceOrderActivity.class;
			break;
		case R.id.service_order_tv:
			mClass = MyServicePackageOrderActivity.class;
			break;
		case R.id.my_pinggu_tv:
			mClass = MyPingGuActivity.class;
			break;
		case R.id.my_hetong_tv:
			mClass = MyHeTongActivity.class;
			break;
		}
		if (mClass != null) {
			IntentUtils.goTo(getActivity(), mClass);
		}

	}

}
