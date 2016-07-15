package com.sevenstar.steward2016.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.LogUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.constant.GlobalUrl;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class NursingInstitutionForTheAgedContentActivity extends BannerBaseActivity {
	@ViewInject(R.id.head_image)
	ImageView headIV;
	@ViewInject(R.id.content_tv_one)
	TextView oneTV;
	@ViewInject(R.id.content_tv_two)
	TextView twoTV;
	@ViewInject(R.id.content_tv_three)
	TextView threeTV;
	@ViewInject(R.id.content_tv_four)
	TextView fourTV;	@ViewInject(R.id.content_tv_five)
	TextView fiveTV;
	private JSONObject jsonObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_nursing_institution_for_the_aged_content_page);
		setColumnText("养老机构");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		Bundle bundle = IntentUtils.getIntentBundle(this);
		if (bundle != null) {
			try {
				String json = bundle.getString("json");
				jsonObj = JSONObject.parseObject(json);
				ViewUtils.setTextData(oneTV, jsonObj.getString("Name"));
				ViewUtils.setTextData(twoTV, jsonObj.getString("TypeName"));
				ViewUtils.setTextData(threeTV, jsonObj.getString("Tel"));
				ViewUtils.setTextData(fourTV, jsonObj.getString("Price")+"元");
				ViewUtils.setTextData(fiveTV, jsonObj.getString("Address"));
				x.image().bind(headIV, GlobalUrl.ADDR+jsonObj.getString("Picture"));
			} catch (Exception e) {
				LogUtils.showErrorMessage(e);
			}
		}
	}

	@Override
	public Activity getActivity() {
		return this;
	}

}
