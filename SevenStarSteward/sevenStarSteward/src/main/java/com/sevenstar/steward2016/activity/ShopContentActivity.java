package com.sevenstar.steward2016.activity;

import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.manager.AppManager;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.callback.PayCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.utils.AdUtils;
import com.sevenstar.steward2016.utils.PayUtils;
import com.sevenstar.steward2016.view.SetTimeDialogLayout;

public class ShopContentActivity extends BannerBaseActivity implements OnClickListener {

	@ViewInject(R.id.shop_content_head_layout)
	RelativeLayout headLayout;
	@ViewInject(R.id.shop_content_six_layout)
	View sixLayout;
	@ViewInject(R.id.shop_content_four_layout)
	View fourLayout;
	@ViewInject(R.id.shop_content_three_layout)
	View threeLayout;
	@ViewInject(R.id.shop_content_type_tv_two)
	TextView twoTypeTV;
	@ViewInject(R.id.shop_content_type_tv_five)
	TextView fiveTypeTV;
	@ViewInject(R.id.shop_content_type_tv_six)
	TextView sixTypeTV;
	@ViewInject(R.id.shop_content_tv_one)
	TextView oneTV;
	@ViewInject(R.id.shop_content_tv_two)
	TextView twoTV;
	@ViewInject(R.id.shop_content_tv_three)
	TextView threeTV;
	@ViewInject(R.id.shop_content_tv_four)
	TextView fourTV;
	@ViewInject(R.id.shop_content_tv_five)
	TextView fiveTV;
	@ViewInject(R.id.shop_content_tv_six)
	TextView sixTV;
	@ViewInject(R.id.shop_content_tv_seven)
	TextView sevenTV;
	@ViewInject(R.id.shop_content_tv_eight)
	TextView eightTV;
	@ViewInject(R.id.banner_Base_activity_head_right_text_view)
	TextView rightTV;
	private JSONObject jsonObj;
	private int serviceType;
	private String shopType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_shop_content_page);
		setColumnText("商品详情");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		Bundle bundle = IntentUtils.getIntentBundle(this);
		if (bundle != null) {
			String json = bundle.getString("json");
			serviceType = bundle.getInt("serviceType");
			if (!bundle.getBoolean("noShowOrder")) {
				ViewUtils.setVisibility(View.VISIBLE, rightTV);
				ViewUtils.setOnClickListener(rightTV, this);
				ViewUtils.setTextData(rightTV, "立即下单");
			}
			if (json != null) {
				jsonObj = JSONObject.parseObject(json);
				shopType = "";
				switch (serviceType) {
				case 1:
					setColumnText("商品详情");
					shopType = "ProductID";
					ViewUtils.setTextData(oneTV, jsonObj.getString("Name"));
					ViewUtils.setTextData(twoTV, jsonObj.getString("StandPrice") + "元");
					ViewUtils.setTextData(threeTV, jsonObj.getString("SellPrice") + "元");
					ViewUtils.setTextData(fourTV, jsonObj.getString("StoreNumber"));
					ViewUtils.setTextData(fiveTV, jsonObj.getString("SellNumber"));
					ViewUtils.setTextData(sixTV, jsonObj.getString("Address"));
					ViewUtils.setTextData(sevenTV, jsonObj.getString("ProductDate"));
					ViewUtils.setTextData(eightTV, jsonObj.getString("Introduction"));
					break;
				case 2:
					setColumnText("租用详情");
					shopType = "EquipmentID";
					ViewUtils.setTextData(twoTypeTV, "价格");
					ViewUtils.setTextData(sixTypeTV, "设备地址");
					ViewUtils.setVisibility(View.GONE, threeLayout);
					ViewUtils.setVisibility(View.GONE, fourLayout);
					ViewUtils.setTextData(oneTV, jsonObj.getString("Name"));
					ViewUtils.setTextData(twoTV, jsonObj.getString("Price") + "元");
					ViewUtils.setTextData(fiveTV, jsonObj.getString("Number"));
					ViewUtils.setTextData(sixTV, jsonObj.getString("Address"));
					ViewUtils.setTextData(sevenTV, jsonObj.getString("Date"));
					ViewUtils.setTextData(eightTV, jsonObj.getString("Introduction"));
					break;
				case 3:
					setColumnText("众筹详情");
					shopType = "MarkID";
					ViewUtils.setTextData(twoTypeTV, "价格");
					ViewUtils.setTextData(fiveTypeTV, "众筹数量");
					ViewUtils.setVisibility(View.GONE, threeLayout);
					ViewUtils.setVisibility(View.GONE, fourLayout);
					ViewUtils.setVisibility(View.GONE, sixLayout);
					ViewUtils.setTextData(oneTV, jsonObj.getString("Name"));
					ViewUtils.setTextData(twoTV, jsonObj.getString("Price") + "元");
					ViewUtils.setTextData(fiveTV, jsonObj.getString("Number"));
					ViewUtils.setTextData(sevenTV, jsonObj.getString("Date"));
					ViewUtils.setTextData(eightTV, jsonObj.getString("Introduction"));
					break;
				case 4:
					setColumnText("特购详情");
					shopType = "SpecialID";
					ViewUtils.setTextData(twoTypeTV, "价格");
					ViewUtils.setVisibility(View.GONE, threeLayout);
					ViewUtils.setVisibility(View.GONE, fourLayout);
					ViewUtils.setTextData(oneTV, jsonObj.getString("Name"));
					ViewUtils.setTextData(twoTV, jsonObj.getString("Price") + "元");
					ViewUtils.setTextData(fiveTV, jsonObj.getString("Number"));
					ViewUtils.setTextData(sixTV, jsonObj.getString("Address"));
					ViewUtils.setTextData(sevenTV, jsonObj.getString("ProductDate"));
					ViewUtils.setTextData(eightTV, jsonObj.getString("Introduction"));
					break;
				}

				HttpSendMananger.sendPost(GlobalUrl.HAPPY_SHOP_BANNER_IMAGE_URL, new BaseSimpleHttpCallback(this, true, false) {
					@Override
					public void onSuccess(JSONObject data) {
						List<JSONObject> listData = JSONObject.parseArray(data.getString("images"), JSONObject.class);
						if (listData != null && listData.size() > 0) {
							AdUtils.getAdLayout(getActivity(), listData, headLayout, "FileFolder");
						}
					}
				}, shopType, jsonObj.getString(shopType), "serviceType", String.valueOf(serviceType));
			}
		}
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (StarApplication.userInfo == null) {
			ToastUtil.showInfo(getActivity(), "您未登录,不能进行此操作");
			return;
		}
		if (serviceType == 2) {
			final View layout = LayoutInflater.from(getActivity()).inflate(R.layout.start_date_and_end_date_layout, null);
			final DatePicker startDate = ViewUtils.find(layout, R.id.start_date);
			final DatePicker endDate = ViewUtils.find(layout, R.id.end_date);
			ViewUtils.setOnClickListener(layout, R.id.queding, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String startDateStr = String.format("%d-%d-%d", startDate.getYear(), startDate.getMonth() + 1,
							startDate.getDayOfMonth());
					String endDateStr = String.format("%d-%d-%d", endDate.getYear(), endDate.getMonth() + 1, endDate.getDayOfMonth());
					DialogUtils.closeDialog();
					sendOrder(startDateStr, endDateStr);
				}
			});
			ViewUtils.setOnClickListener(layout, R.id.quxiao, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DialogUtils.closeDialog();
				}
			});
			DialogUtils.show(getActivity(), R.style.custom_dialog_style, -1, layout, Gravity.CENTER, false);
			DialogUtils.showDialog();
		} else {
			sendOrder("", "");
		}

	}

	private void sendOrder(final String startDate, final String endDate) {
		final JSONObject tempData = new JSONObject();
		tempData.put("Name",jsonObj.getString("Name"));
		if(serviceType!=1){
			tempData.put("Price",jsonObj.getString("Price"));
		}else{
			tempData.put("SellPrice",jsonObj.getString("SellPrice"));
		}
		tempData.put("Content",jsonObj.getString("Introduction"));
		tempData.put("OrderID",jsonObj.getString(shopType));
		DialogUtils.showInfoDialog(this, new OnClickListener() {

			@Override
			public void onClick(View v) {

				PayUtils.pay(GlobalUrl.getPayOrderInfo,getActivity(),tempData, new PayCallBack() {
					@Override
					public void onSuccess() {
						HttpSendMananger.sendPost(GlobalUrl.HAPPY_SHOP_ORDER_URL, new BaseSimpleHttpCallback(getActivity(), true, true) {
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
								}, "Name", jsonObj.getString("Name"), "FileFolder", jsonObj.getString("FileFolder"), "UserID",
								StarApplication.userInfo.getString("UserID"), "MyID", jsonObj.getString(shopType), "serviceType",
								String.valueOf(serviceType), "StartDate", startDate, "EndDate", endDate);
					}

					@Override
					public void onFailure() {

					}
				});

			}
		}, "确认是否下此订单", null, null, null, false);
	}

}
