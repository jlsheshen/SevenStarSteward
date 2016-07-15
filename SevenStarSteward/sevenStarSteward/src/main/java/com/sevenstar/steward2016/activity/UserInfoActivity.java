package com.sevenstar.steward2016.activity;

import java.util.Calendar;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.SharedPreferencesUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.adapter.SingleDataAdapter;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;

public class UserInfoActivity extends BannerBaseActivity implements OnClickListener, OnCheckedChangeListener {
	@ViewInject(R.id.banner_Base_activity_head_right_text_view)
	TextView rightTV;
	@ViewInject(R.id.family_id_tv)
	EditText FamilyIDTV;
	@ViewInject(R.id.agent_id_tv)
	TextView AgentIDTV;
	@ViewInject(R.id.user_name_tv)
	EditText UserNameTV;
	@ViewInject(R.id.user_age_tv)
	EditText ageTV;
	@ViewInject(R.id.sex_radio)
	RadioGroup SexRadio;
	@ViewInject(R.id.marriage_tv)
	EditText MarriageTV;
	@ViewInject(R.id.birthDay_tv)
	TextView BirthDayTV;
	@ViewInject(R.id.tel_tv)
	EditText TelTV;
	@ViewInject(R.id.address_tv)
	EditText AddressTV;
	@ViewInject(R.id.postcode_tv)
	EditText PostcodeTV;
	@ViewInject(R.id.email_tv)
	EditText EmailTV;
	@ViewInject(R.id.qq_tv)
	EditText QQTV;
	@ViewInject(R.id.wechat_tv)
	EditText WechatTV;
	@ViewInject(R.id.favourite_tv)
	EditText FavouriteTV;
	@ViewInject(R.id.pets_tv)
	EditText PetsTV;
	@ViewInject(R.id.origin_tv)
	EditText OriginTV;
	@ViewInject(R.id.emergencyTel1_tv)
	EditText EmergencyTel1TV;
	@ViewInject(R.id.emergencyTel2_tv)
	EditText EmergencyTel2TV;
	@ViewInject(R.id.person_id_tv)
	EditText PersonIDTV;
	@ViewInject(R.id.nike_name_tv)
	EditText NickNameTV;
	@ViewInject(R.id.signature_tv)
	EditText SignatureTV;
	@ViewInject(R.id.alipay_tv)
	EditText AlipayTV;
	@ViewInject(R.id.education_tv)
	EditText EducationTV;
	@ViewInject(R.id.religion_tv)
	EditText ReligionTV;
	@ViewInject(R.id.bloodType_tv)
	EditText BloodTypeTV;
	@ViewInject(R.id.hospital_tv)
	EditText HospitalTV;
	@ViewInject(R.id.healthInsurance_tv)
	EditText HealthInsuranceTV;
	@ViewInject(R.id.medicalHistory_tv)
	EditText MedicalHistoryTV;
	@ViewInject(R.id.allergy_tv)
	EditText AllergyTV;
	private JSONObject userInfo;
	private int sexIndex = -1;
	private int agentIndex=-1;
	private JSONObject userInfoOld;

	private void initData() {
		ViewUtils.setTextData(FamilyIDTV, userInfo.getString("FamilyID"));
		if(OtherUtils.isNotEmpty(userInfo.getString("AgentName"))){
			ViewUtils.setTextData(AgentIDTV, userInfo.getString("AgentName"));
		}
		AgentIDTV.setOnClickListener(this);
		ViewUtils.setTextData(UserNameTV, userInfo.getString("UserName"));
		ViewUtils.setTextData(MarriageTV, userInfo.getString("Marriage"));
		ViewUtils.setTextData(BirthDayTV, userInfo.getString("BirthDay"));
		ViewUtils.setTextData(ageTV, userInfo.getString("Age"));
		if (OtherUtils.isNotEmpty(userInfo.getString("BirthDay"))) {
			BirthDayTV.setTextColor(getResources().getColor(R.color._282828));
		}
		BirthDayTV.setOnClickListener(this);
		if (userInfo.getString("SexName").equals("男")) {
			SexRadio.check(R.id.sex_nan_button);
		} else if (userInfo.getString("SexName").equals("女")) {
			SexRadio.check(R.id.sex_nv_button);
		}
		ViewUtils.setTextData(TelTV, userInfo.getString("Tel"));
		ViewUtils.setTextData(AddressTV, userInfo.getString("Address"));
		ViewUtils.setTextData(PostcodeTV, userInfo.getString("Postcode"));
		ViewUtils.setTextData(EmailTV, userInfo.getString("Email"));
		ViewUtils.setTextData(QQTV, userInfo.getString("QQ"));
		ViewUtils.setTextData(WechatTV, userInfo.getString("Wechat"));
		ViewUtils.setTextData(FavouriteTV, userInfo.getString("Favourite"));
		ViewUtils.setTextData(PetsTV, userInfo.getString("Pets"));
		ViewUtils.setTextData(OriginTV, userInfo.getString("Origin"));
		ViewUtils.setTextData(EmergencyTel1TV, userInfo.getString("EmergencyTel1"));
		ViewUtils.setTextData(EmergencyTel2TV, userInfo.getString("EmergencyTel2"));
		ViewUtils.setTextData(PersonIDTV, userInfo.getString("PersonID"));
		ViewUtils.setTextData(NickNameTV, userInfo.getString("NickName"));
		ViewUtils.setTextData(SignatureTV, userInfo.getString("Signature"));
		ViewUtils.setTextData(AlipayTV, userInfo.getString("Alipay"));
		ViewUtils.setTextData(EducationTV, userInfo.getString("Education"));
		ViewUtils.setTextData(ReligionTV, userInfo.getString("Religion"));
		ViewUtils.setTextData(BloodTypeTV, userInfo.getString("BloodType"));
		ViewUtils.setTextData(HospitalTV, userInfo.getString("Hospital"));
		ViewUtils.setTextData(HealthInsuranceTV, userInfo.getString("HealthInsurance"));
		ViewUtils.setTextData(MedicalHistoryTV, userInfo.getString("MedicalHistory"));
		ViewUtils.setTextData(AllergyTV, userInfo.getString("Allergy"));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_user_info_page);
		setColumnText("账户信息");
		x.view().inject(this);
		ViewUtils.setVisibility(View.VISIBLE, rightTV);
		ViewUtils.setOnClickListener(rightTV, this);
		ViewUtils.setTextData(rightTV, "保存");
		userInfo = JSONObject.parseObject(StarApplication.userInfo.toJSONString());
		userInfoOld = JSONObject.parseObject(StarApplication.userInfo.toJSONString());
		SexRadio.setOnCheckedChangeListener(this);
		initData();
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.banner_Base_activity_head_right_text_view:
			submitData();
			break;
		case R.id.agent_id_tv:
			if(agentArrays!=null&&agentArrays.size()>0){
				selectAgentInfo();
			}else{
				getAgentInfo();
			}
			break;
		case R.id.birthDay_tv:
			Calendar c = Calendar.getInstance();
			int mYear = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH);
			int mDay = c.get(Calendar.DAY_OF_MONTH);
			new DatePickerDialog(this, 0, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					ViewUtils.setTextData(BirthDayTV, String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
				}
			}, mYear, mMonth, mDay).show();
			break;
		}

	}

	private void submitData() {
		// TODO Auto-generated method stub
		userInfo.put("FamilyID", OtherUtils.isNotEmpty(FamilyIDTV.getText()) ? FamilyIDTV.getText().toString() : null);
		if(agentIndex!=-1&&agentArrays!=null){
			userInfo.put("AgentID",agentArrays.get(agentIndex).getIntValue("AgentID"));
		}
		userInfo.put("UserName", UserNameTV.getText().toString());
		userInfo.put("Age", ageTV.getText().toString());
		if (sexIndex != -1) {
			userInfo.put("Sex", sexIndex);
		}
		userInfo.put("Marriage", MarriageTV.getText().toString());
		userInfo.put("BirthDay", BirthDayTV.getText().toString());
		userInfo.put("Tel", TelTV.getText().toString());
		userInfo.put("Address", AddressTV.getText().toString());
		userInfo.put("Postcode", PostcodeTV.getText().toString());
		userInfo.put("Email", EmailTV.getText().toString());
		userInfo.put("QQ", QQTV.getText().toString());
		userInfo.put("Wechat", WechatTV.getText().toString());
		userInfo.put("Favourite", FavouriteTV.getText().toString());
		userInfo.put("Pets", PetsTV.getText().toString());
		userInfo.put("Origin", OriginTV.getText().toString());
		userInfo.put("EmergencyTel1", EmergencyTel1TV.getText().toString());
		userInfo.put("EmergencyTel2", EmergencyTel2TV.getText().toString());
		userInfo.put("PersonID", PersonIDTV.getText().toString());
		userInfo.put("NickName", NickNameTV.getText().toString());
		userInfo.put("Alipay", AlipayTV.getText().toString());
		userInfo.put("Education", EducationTV.getText().toString());
		userInfo.put("Religion", ReligionTV.getText().toString());
		userInfo.put("BloodType", BloodTypeTV.getText().toString());
		userInfo.put("Hospital", HospitalTV.getText().toString());
		userInfo.put("HealthInsurance", HealthInsuranceTV.getText().toString());
		userInfo.put("MedicalHistory", MedicalHistoryTV.getText().toString());
		userInfo.put("Allergy", AllergyTV.getText().toString());
		if (userInfo.toJSONString().equals(userInfoOld.toJSONString())) {
			ToastUtil.showInfo(getActivity(), "没有修改,不需要保存");
			return;
		}
		HttpSendMananger.sendPost(GlobalUrl.USER_SAVE_URL, new BaseSimpleHttpCallback(this, true, true) {
			@Override
			public void onSuccess(JSONObject data) {
				StarApplication.userInfo = userInfo;
			}

			@Override
			public OnClickListener getDialogMessageListener() {
				return new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (state == 0) {
							finish();
						}
					}
				};
			}
		}, "json", userInfo.toJSONString());
	}

	private List<JSONObject> agentArrays;

	private void getAgentInfo(){
		HttpSendMananger.sendPost(GlobalUrl.getAgentListInfo, new BaseSimpleHttpCallback(this, true, false) {
			@Override
			public void onSuccess(JSONObject data) {
				if (data != null) {
					agentArrays=JSONObject.parseArray(data.getString("data"),JSONObject.class);
					if(agentArrays!=null&&agentArrays.size()>0){
						selectAgentInfo();
					}else{
						ToastUtil.showInfo(getActivity(),"暂无代理商信息");
					}
				}
			}
		});
	}


	private void selectAgentInfo(){
		final SingleDataAdapter adapter = new SingleDataAdapter(this, agentArrays, "Name");
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("请选择代理商");
		builder.setSingleChoiceItems(adapter, agentIndex, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				agentIndex = which;
			}
		});

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int which) {
				if (agentIndex != which) {
					ViewUtils.setTextData(AgentIDTV, adapter.getItem(agentIndex).getString("Name"));
				}
			}
		});
		builder.create().show();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.sex_nan_button:
			sexIndex = 0;
			break;
		case R.id.sex_nv_button:
			sexIndex = 1;
			break;
		}
	}

}
