package com.sevenstar.steward2016.activity;

import java.io.InputStream;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.manager.AppManager;
import com.lib.shiguotao.manager.HttpManager;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.LogUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.lib.shiguotao.view.wheel.OnWheelChangedListener;
import com.lib.shiguotao.view.wheel.WheelView;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.SelectAreaAdapter;
import com.sevenstar.steward2016.adapter.SingleDataAdapter;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.utils.Utils;
import com.sevenstar.steward2016.view.SetTimeDialogLayout;
/**
 * 保洁清理订单详情页
 */
public class CleaningOrderContentActivity extends BannerBaseActivity implements OnClickListener, OnWheelChangedListener {
	private int type = 0;
	@ViewInject(R.id.select_house_type_layout)
	View select_house_type_layout;
	@ViewInject(R.id.select_service_layout)
	View select_service_layout;
	@ViewInject(R.id.select_area_layout)
	View select_area_layout;
	@ViewInject(R.id.select_time_layout)
	View select_time_layout;
	@ViewInject(R.id.select_requirement_layout)
	View select_requirement_layout;
	@ViewInject(R.id.select_equipment_layout)
	View select_equipment_layout;
	@ViewInject(R.id.select_house_type_tv)
	TextView select_house_type_tv;
	@ViewInject(R.id.select_service_tv)
	TextView select_service_tv;
	@ViewInject(R.id.select_area_tv)
	TextView select_area_tv;
	@ViewInject(R.id.select_time_tv)
	TextView select_time_tv;
	@ViewInject(R.id.select_requirement_tv)
	TextView select_requirement_tv;
	@ViewInject(R.id.select_equipment_tv)
	TextView select_equipment_tv;
	@ViewInject(R.id.banner_Base_activity_head_right_text_view)
	TextView rightTV;
	private JSONArray provinceList = null;
	private WheelView provineWheelView;
	private WheelView cityWheelView;
	private SelectAreaAdapter provineAdapter;
	private SelectAreaAdapter cityAdapter;
	private SelectAreaAdapter districtAdapter;
	private WheelView districtWheelView;
	private JSONObject houseTypeJSON = null;
	private String houseTypeList;
	private String clearingServiceList;
	private String address = null;
	private String time = null;
	private String requirement = null;
	private int clearEquipmentIndex = -1;
	private JSONObject cleanServiceJSON;
	private List<JSONObject> equipmentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = IntentUtils.getIntentBundle(this);
		if (bundle != null) {
			type = bundle.getInt("type");
			setColumnText(bundle.getString("title"));
		}
		setBaseContextView(R.layout.activity_cleaning_order_content_layout);
		x.view().inject(this);
		select_house_type_layout.setOnClickListener(this);
		select_service_layout.setOnClickListener(this);
		select_area_layout.setOnClickListener(this);
		select_time_layout.setOnClickListener(new SetTimeDialogLayout(this, select_time_tv, null));
		select_requirement_layout.setOnClickListener(this);
		select_equipment_layout.setOnClickListener(this);
		rightTV.setVisibility(View.VISIBLE);
		rightTV.setOnClickListener(this);
		ViewUtils.setTextData(rightTV, "立即下单");
		initData();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.select_house_type_layout:
			selectHouseType();
			break;
		case R.id.select_service_layout:
			selectCleanService();
			break;
		case R.id.select_area_layout:
			selectArea();
			break;
		case R.id.select_time_layout:
			// showDateTime();
			break;
		case R.id.select_requirement_layout:
			String content = select_requirement_tv.getText().toString();
			content = "请输入您的特殊要求".equals(content) ? "" : content;
			Utils.showEditTextDialog(getActivity(), content, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					requirement = v.getTag().toString();
					ViewUtils.setTextData(select_requirement_tv, requirement);

				}
			}, null, "请输入您的特殊要求", false);
			break;
		case R.id.select_equipment_layout:
			if (equipmentList != null && equipmentList.size() > 0) {
				final SingleDataAdapter adapter = new SingleDataAdapter(getActivity(), equipmentList, "EquipmentName");
				Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("请选择我们的设备");
				builder.setSingleChoiceItems(adapter, clearEquipmentIndex, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						clearEquipmentIndex = which;
					}
				});

				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int which) {
						if (clearEquipmentIndex != which) {
							ViewUtils.setTextData(select_equipment_tv, adapter.getItem(clearEquipmentIndex).getString("EquipmentName"));
						}
					}
				});
				builder.create().show();
			}
			break;
		case R.id.banner_Base_activity_head_right_text_view:
			if (houseTypeJSON == null) {
				ToastUtil.showInfo(this, "请选择户型");
				return;
			}
			if (cleanServiceJSON == null) {
				ToastUtil.showInfo(this, "请选择清洗服务");
				return;
			}
			if (OtherUtils.isEmpty(address)) {
				ToastUtil.showInfo(this, "请选择您的地址");
				return;
			}
			time = select_time_tv.getText().toString();
			if (OtherUtils.isEmpty(time) || "请选择您的时间".equals(time)) {
				ToastUtil.showInfo(this, "请选择你的时间");
				return;
			}
			JSONObject json = new JSONObject();
			if (OtherUtils.isNotEmpty(requirement)) {
				json.put("Remarks", requirement);
			}
			if (clearEquipmentIndex != -1) {
				json.put("EquipmentID", equipmentList.get(clearEquipmentIndex).getIntValue("EquipmentID"));
			}
			json.put("ClearingID", cleanServiceJSON.getString("ClearingID"));
			json.put("HouseType", houseTypeJSON.getString("HouseTypeName"));
			json.put("Area", houseTypeJSON.getString("Area"));
			json.put("Target", cleanServiceJSON.getString("Target"));
			json.put("Hours", cleanServiceJSON.getString("Hours"));
			json.put("SuppliesCost", cleanServiceJSON.getString("SuppliesCost"));
			json.put("Price", cleanServiceJSON.getString("Price"));
			json.put("Address2", address);
			String[] times = time.split("\t");
			json.put("Date", times[0]);
			json.put("Time", times[1]);
			Bundle ext = new Bundle();
			ext.putString("json", json.toJSONString());
			ext.putInt("type", 4);
			IntentUtils.goTo(this, HourPointContactActivity.class, ext);
			break;
		}
	}

	private void selectCleanService() {
		// TODO Auto-generated method stub
		if (OtherUtils.isNotEmpty(clearingServiceList)) {
			Bundle bundle = new Bundle();
			Intent intent = new Intent(this, SelectCleanServiceActivity.class);
			bundle.putString("data", clearingServiceList);
			intent.putExtras(bundle);
			startActivityForResult(intent, 0x2000);
		} else {
			ToastUtil.showInfo(this, "清洁服务数据为空");
		}
	}

	private void selectHouseType() {
		// TODO Auto-generated method stub
		if (OtherUtils.isNotEmpty(houseTypeList)) {
			Bundle bundle = new Bundle();
			Intent intent = new Intent(this, SelectHouseTypeActivity.class);
			bundle.putString("data", houseTypeList);
			intent.putExtras(bundle);
			startActivityForResult(intent, 0x1000);
		} else {
			ToastUtil.showInfo(this, "户型数据为空");
		}

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 0x1000 && arg1 == 0x1000 && arg2 != null) {
			houseTypeJSON = JSONObject.parseObject(arg2.getStringExtra("data"));
			ViewUtils.setTextData(select_house_type_tv,
					String.format("%s|%s", houseTypeJSON.getString("HouseTypeName"), houseTypeJSON.getString("Area")));
		} else if (arg0 == 0x2000 && arg1 == 0x2000 && arg2 != null) {
			cleanServiceJSON = JSONObject.parseObject(arg2.getStringExtra("data"));

			ViewUtils.setTextData(select_service_tv, String.format("%s元|%s|%s小时", cleanServiceJSON.getString("Price"),
					cleanServiceJSON.getString("Target"), cleanServiceJSON.getString("Hours")));
		}
	}

	private void selectArea() {
		if (provinceList != null) {
			View dialogLayout = ViewUtils.getLayout(this, R.layout.select_province_or_city_layout);
			provineWheelView = ViewUtils.find(dialogLayout, R.id.wheelview_province);
			cityWheelView = ViewUtils.find(dialogLayout, R.id.wheelview_city);
			districtWheelView = ViewUtils.find(dialogLayout, R.id.wheelview_district);
			provineAdapter = new SelectAreaAdapter(getActivity(), provinceList, "ProvinceName");
			provineWheelView.setViewAdapter(provineAdapter);
			provineWheelView.setVisibleItems(6);
			provineWheelView.addChangingListener(this);
			cityAdapter = new SelectAreaAdapter(getActivity(), provineAdapter.getJSONObject(0).getJSONArray("cityList"), "CityName");
			cityWheelView.setViewAdapter(cityAdapter);
			cityWheelView.setVisibleItems(6);
			cityWheelView.addChangingListener(this);
			districtAdapter = new SelectAreaAdapter(getActivity(), cityAdapter.getJSONObject(0).getJSONArray("districtList"),
					"DistrictName");
			districtWheelView.setViewAdapter(districtAdapter);
			districtWheelView.setVisibleItems(6);
			districtWheelView.addChangingListener(this);
			View confirm = ViewUtils.find(dialogLayout, R.id.confirm_button);
			View cancel = ViewUtils.find(dialogLayout, R.id.cancel_button);

			confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					JSONObject province = provineAdapter.getJSONObject(provineWheelView.getCurrentItem());
					JSONObject city = cityAdapter.getJSONObject(cityWheelView.getCurrentItem());
					JSONObject district = districtAdapter.getJSONObject(districtWheelView.getCurrentItem());
					address = String.format("%s-%s-%s", province.getString("ProvinceName"), city.getString("CityName"),
							district.getString("DistrictName"));
					ViewUtils.setTextData(select_area_tv, address);
					DialogUtils.closeDialog();
				}
			});
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DialogUtils.closeDialog();
				}
			});
			DialogUtils.show(this, R.style.custom_dialog_style, 0, dialogLayout, Gravity.CENTER, false);
			DialogUtils.showDialog();
		}

	}

	private void initData() {
		// TODO Auto-generated method stub

		AppManager.getInstance().getLoadThreadPool().execute(new Runnable() {
			@Override
			public void run() {
				try {
					InputStream fileInput = getAssets().open("city.json");
					String json = HttpManager.getInstance().inStream2String(fileInput, "GBK");
					provinceList = JSONObject.parseArray(json);
				} catch (Exception e) {
					LogUtils.showErrorMessage(e);
				}
			}
		});

		HttpSendMananger.sendPost(GlobalUrl.GET_MAIN_LIVE_SERVICE, new BaseSimpleHttpCallback(this, true, true) {

			@Override
			public void onSuccess(JSONObject data) {
				if (data != null) {
					// provinceList = data.getJSONArray("provinceList");
					houseTypeList = data.getString("houseTypeList");
					clearingServiceList = data.getString("clearingServiceList");
					String clearingEquipmentList = data.getString("clearingEquipmentList");
					if (OtherUtils.isNotEmpty(clearingEquipmentList)) {
						equipmentList = JSONArray.parseArray(clearingEquipmentList, JSONObject.class);
					}
				} else {
					finish();
				}
			}

			@Override
			public OnClickListener getDialogMessageListener() {
				// TODO Auto-generated method stub
				return new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (state != 0) {
							finish();
						}
					}
				};
			}
		}, "serviceType", "6", "childType", "1", "ClearingTypeID", String.valueOf(type));
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == provineWheelView) {
			cityAdapter = new SelectAreaAdapter(getActivity(), provineAdapter.getJSONObject(newValue).getJSONArray("cityList"), "CityName");
			districtAdapter = new SelectAreaAdapter(getActivity(), cityAdapter.getJSONObject(0).getJSONArray("districtList"),
					"DistrictName");
			cityWheelView.setViewAdapter(cityAdapter);
			cityWheelView.setCurrentItem(0, false);
			districtWheelView.setViewAdapter(districtAdapter);
			districtWheelView.setCurrentItem(0, false);
		} else if (wheel == cityWheelView) {
			districtAdapter = new SelectAreaAdapter(getActivity(), cityAdapter.getJSONObject(newValue).getJSONArray("districtList"),
					"DistrictName");
			districtWheelView.setViewAdapter(districtAdapter);
			districtWheelView.setCurrentItem(0, false);
		}
	}

}
