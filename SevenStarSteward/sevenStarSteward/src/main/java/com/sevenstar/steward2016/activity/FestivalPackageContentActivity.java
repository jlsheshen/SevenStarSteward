package com.sevenstar.steward2016.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.manager.AppManager;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.adapter.FestivalPackageLayoutPagerAdapter;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.callback.PayCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.utils.PayUtils;
/**
 * 节日套餐内容详情界面
 */
public class FestivalPackageContentActivity extends BannerBaseActivity implements OnClickListener {
	@ViewInject(R.id.banner_Base_activity_head_right_text_view)
	TextView rightTV;
	@ViewInject(R.id.viewpager)
	ViewPager viewPager;
	private JSONObject jsonObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_festival_package_content_page);
		setColumnText("节日套餐");
		x.view().inject(this);
		ViewUtils.setVisibility(View.VISIBLE, rightTV);
		ViewUtils.setTextData(rightTV, "立即下单");
		ViewUtils.setOnClickListener(rightTV, this);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		Bundle extras = IntentUtils.getIntentBundle(this);
		if (extras != null) {
			String json = extras.getString("data");
			if (OtherUtils.isNotEmpty(json)) {
				jsonObj = JSONObject.parseObject(json);
				if (jsonObj != null) {
					HttpSendMananger.sendPost(GlobalUrl.GET_HOLIDAY_CONTENT_DATA_URL, new BaseSimpleHttpCallback(this, true, false) {
						@Override
						public void onSuccess(JSONObject data) {
							if (data != null) {
								JSONArray jsonArray = data.getJSONArray("data");
								viewPager.setAdapter(new FestivalPackageLayoutPagerAdapter(getActivity(), jsonArray, jsonObj));
							}
						}

					}, "PackageID", jsonObj.getString("PackageID"));
				}
			}
		}
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void onClick(View v) {
		if (StarApplication.userInfo == null) {
			ToastUtil.showInfo(getActivity(), "您未登录,不能进行此操作");
			return;
		}
		DialogUtils.showInfoDialog(this, new OnClickListener() {

			@Override
			public void onClick(View v) {
				JSONObject tempData = new JSONObject();
				tempData.put("Price",jsonObj.getString("PackagePrice"));
				tempData.put("Name",jsonObj.getString("PackageName"));
				tempData.put("OrderID",jsonObj.getString("PackageID"));
				PayUtils.pay(GlobalUrl.getPayOrderInfo, getActivity(), tempData, new PayCallBack() {
					@Override
					public void onSuccess() {

						HttpSendMananger.sendPost(GlobalUrl.INSTALL_Live_ORDER_URL, new BaseSimpleHttpCallback(getActivity(), true, true) {
							@Override
							public void onSuccess(JSONObject data) {

							}

							@Override
							public OnClickListener getDialogMessageListener() {
								// TODO Auto-generated method stub
								return new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										if (state == 0) {
											AppManager.getInstance().removeAllActivity();
										}
									}
								};
							}
						}, "serviceType", "1", "UserID", StarApplication.userInfo.getString("UserID"), "PackageID", jsonObj.getString("PackageID"));
					}

					@Override
					public void onFailure() {

					}
				});
			}
		}, "确认是否下此订单", null, null, null, false);
	}

}
