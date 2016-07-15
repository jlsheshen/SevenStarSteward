package com.sevenstar.steward2016.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

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
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.callback.PayCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.utils.PayUtils;
/**
 * 钟点服务:联系人信息界面
 */
public class HourPointContactActivity extends BannerBaseActivity implements OnClickListener {
	private int type = 0;// 0=故障服务,1=理疗服务,2=私人厨师

	@ViewInject(R.id.edittext_one)
	EditText oneET;
	@ViewInject(R.id.edittext_two)
	EditText twoET;
	@ViewInject(R.id.edittext_three)
	EditText threeET;
	@ViewInject(R.id.banner_Base_activity_head_right_text_view)
	TextView rightTV;
	private String childType;
	private String serviceType;
	private JSONObject jsonObj = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_hour_point_contact_info_layout);
		x.view().inject(this);
		setColumnText("订单联系人");
		Bundle extras = IntentUtils.getIntentBundle(this);
		if (extras != null) {
			type = extras.getInt("type");
			jsonObj = JSONObject.parseObject(extras.getString("json"));
		}
		ViewUtils.setVisibility(View.VISIBLE, rightTV);
		ViewUtils.setOnClickListener(rightTV, this);
		ViewUtils.setTextData(rightTV, "立即下单");
		initData();
	}

	private void initData() {
		if (StarApplication.userInfo != null) {
			ViewUtils.setTextData(oneET, StarApplication.userInfo.getString("UserName"));
			ViewUtils.setTextData(twoET, StarApplication.userInfo.getString("Tel"));
			ViewUtils.setTextData(threeET, StarApplication.userInfo.getString("Address"));
		}
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (OtherUtils.isEmpty(oneET.getText())) {
			ToastUtil.showInfo(this, "请输入联系人");
			return;
		}
		if (OtherUtils.isEmpty(twoET.getText())) {
			ToastUtil.showInfo(this, "请输入电话");
			return;
		}
		if (OtherUtils.isEmpty(threeET.getText())) {
			ToastUtil.showInfo(this, "请输入地址");
			return;
		}
		DialogUtils.showInfoDialog(this, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jsonObj.put("UserID", StarApplication.userInfo.getString("UserID"));
				jsonObj.put("Name", oneET.getText().toString());
				jsonObj.put("Telephone", twoET.getText().toString());
				jsonObj.put("Address", threeET.getText().toString());
				 childType = "2";
				 serviceType = "6";
				String Price="";
				String Name="";
				String OrderID="";
				String Content="";
				switch (type) {
				case 0:
					childType = "2";
					Price=jsonObj.getString("ToDoorPrice");
					Name=jsonObj.getString("Equipment");
					Content=jsonObj.getString("Fault");
					OrderID=jsonObj.getString("ReparID");
					break;
				case 1:
					childType = "4";
					Price=jsonObj.getString("Cost");
					Name=jsonObj.getString("PhysicalTypeName");
					Content=jsonObj.getString("Service");
					OrderID=jsonObj.getString("PhysicalTypeID");
					break;
				case 2:
					childType = "3";
					Price=String.valueOf(jsonObj.getFloatValue("Price")+jsonObj.getFloatValue("Material"));
					Name=jsonObj.getString("Dishes");
					Content=jsonObj.getString("Service");
					OrderID=jsonObj.getString("CookID");
					break;
				case 3:
					childType = "6";
					Price=jsonObj.getString("Price");
					Name="接送："+jsonObj.getString("WhoAre");
					Content=jsonObj.getString("Coments");
					OrderID=jsonObj.getString("ShuttleID");
					break;
				case 4:
					childType = "1";
					Price=jsonObj.getString("Price");
					Name=jsonObj.getString("Target");
					Content=jsonObj.getString("Coments");
					OrderID=jsonObj.getString("ClearingID");
					break;
				case 7:
					serviceType = "7";
					break;
				}
		if(type!=7){
			JSONObject tempData = new JSONObject();
			tempData.put("Price",Price);
			tempData.put("Name",Name);
			tempData.put("Content",Content);
			tempData.put("OrderID",OrderID);
			PayUtils.pay(GlobalUrl.getPayOrderInfo, getActivity(),tempData, new PayCallBack() {
				@Override
				public void onSuccess() {
					downOrder();
				}
				@Override
				public void onFailure() {

				}
			});

		}else{
			downOrder();
		}



			}
		}, "确认是否下此订单", null, null, null, false);

	}

	private void downOrder() {
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
		}, "serviceType", serviceType, "childType", childType, "json", jsonObj.toJSONString());

	}


}
