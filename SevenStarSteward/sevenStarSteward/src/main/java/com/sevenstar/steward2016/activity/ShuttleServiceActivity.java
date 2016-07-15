package com.sevenstar.steward2016.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.manager.AppManager;
import com.lib.shiguotao.utils.ConvertUtils;
import com.lib.shiguotao.utils.HeadImageManager;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;

public class ShuttleServiceActivity extends BannerBaseActivity implements OnCheckedChangeListener, OnClickListener {
	@ViewInject(R.id.shuttle_service_et_one)
	EditText oneET;
	@ViewInject(R.id.shuttle_service_et_two)
	EditText twoET;
	@ViewInject(R.id.shuttle_service_et_three)
	EditText threeET;
	@ViewInject(R.id.shuttle_service_et_four)
	EditText fourET;
	@ViewInject(R.id.shuttle_service_et_five)
	EditText fiveET;
	@ViewInject(R.id.shuttle_service_et_six)
	EditText sixET;
	@ViewInject(R.id.shuttle_service_et_seven)
	EditText sevenET;
	@ViewInject(R.id.shuttle_service_et_eight)
	EditText eightET;
	@ViewInject(R.id.radiogroup)
	RadioGroup radioGroup;
	@ViewInject(R.id.radiobutton_one)
	RadioButton radiobuttonOne;
	@ViewInject(R.id.radiobutton_two)
	RadioButton radiobuttonTwo;
	@ViewInject(R.id.banner_Base_activity_head_right_text_view)
	TextView rightTV;
	@ViewInject(R.id.select_object_photo)
	View selectObjectPhoto;
	@ViewInject(R.id.set_object_photo_iv)
	ImageView setObjectIV;

	private JSONObject shuttleEntity = new JSONObject();
	private HeadImageManager headImageManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_shuttle_service_layout);
		setColumnText("接送服务");
		x.view().inject(this);
		rightTV.setText("立即下单");
		rightTV.setOnClickListener(this);
		selectObjectPhoto.setOnClickListener(this);
		setObjectIV.setOnClickListener(this);
		ViewUtils.setVisibility(View.VISIBLE, rightTV);
		headImageManager = new HeadImageManager();
		// int width = AppManager.getInstance().getWindowSizeEntity().getWidth()
		// / 2;
		headImageManager.setOutPutXY(128, 128);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		HttpSendMananger.sendPost(GlobalUrl.GET_MAIN_LIVE_SERVICE, new BaseSimpleHttpCallback(this, false, false) {
			@Override
			public void onSuccess(JSONObject data) {
				if (data != null) {
					shuttleEntity = data;
					oneET.setText(data.getString("Peoples"));
					twoET.setText(data.getString("WhoAre"));
					threeET.setText(data.getString("Date"));
					fourET.setText(data.getString("Time"));
					eightET.setText(data.getString("Price"));
					fiveET.setText(data.getString("Start"));
					sixET.setText(data.getString("End"));
					sevenET.setText(data.getString("Coments"));
					int type = data.getIntValue("Car");
					if (type == 0) {
						radiobuttonOne.setChecked(false);
						radiobuttonTwo.setChecked(true);
					} else {
						radiobuttonTwo.setChecked(false);
						radiobuttonOne.setChecked(true);
					}
				}
			}
		}, "serviceType", "6", "childType", "6");
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.radiobutton_one:
			shuttleEntity.put("Car", "1");
			break;
		case R.id.radiobutton_two:
			shuttleEntity.put("Car", "0");
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap bitmap = headImageManager.getActivityResult(requestCode, resultCode, RESULT_OK, getActivity(), data);
		if (bitmap != null) {
			ViewUtils.setVisibility(View.GONE, selectObjectPhoto);
			ViewUtils.setVisibility(View.VISIBLE, setObjectIV);
			setObjectIV.setImageBitmap(bitmap);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (StarApplication.userInfo == null) {
			ToastUtil.showInfo(getActivity(), "您未登录,不能进行此操作");
			return;
		}
		switch (v.getId()) {
		case R.id.select_object_photo:
		case R.id.set_object_photo_iv:
			headImageManager.doPickPhotoAction(getActivity(), "拍照", "从相册中选择", "选择接送对象");
			break;
		case R.id.banner_Base_activity_head_right_text_view:
			shuttleEntity.put("Peoples", oneET.getText().toString());
			shuttleEntity.put("WhoAre", twoET.getText().toString());
			shuttleEntity.put("Date", threeET.getText().toString());
			shuttleEntity.put("Time", fourET.getText().toString());
			shuttleEntity.put("Start", fiveET.getText().toString());
			shuttleEntity.put("End", sixET.getText().toString());
			shuttleEntity.put("Coments", sevenET.getText().toString());
			shuttleEntity.put("Price", eightET.getText().toString());
			Bundle ext = new Bundle();
			ext.putInt("type", 3);
			ext.putString("json", shuttleEntity.toJSONString());
			IntentUtils.goTo(this, HourPointContactActivity.class, ext);
			break;
		}
	}

}
