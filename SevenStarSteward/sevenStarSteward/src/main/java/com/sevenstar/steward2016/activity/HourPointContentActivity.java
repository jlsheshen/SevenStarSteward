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
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
/**
 * 钟点服务:故障服务，理疗服务，私人厨师 内容详情页面
 */
public class HourPointContentActivity extends BannerBaseActivity implements OnClickListener {
	private int type = 0;// 0=故障服务,1=理疗服务,2=私人厨师

	@ViewInject(R.id.edittext_one)
	EditText oneET;
	@ViewInject(R.id.edittext_two)
	EditText twoET;
	@ViewInject(R.id.edittext_three)
	EditText threeET;
	@ViewInject(R.id.edittext_four)
	EditText fourET;
	@ViewInject(R.id.title_one_tv)
	TextView oneTV;
	@ViewInject(R.id.title_two_tv)
	TextView twoTV;
	@ViewInject(R.id.three_tv)
	TextView threeTV;
	@ViewInject(R.id.banner_Base_activity_head_right_text_view)
	TextView rightTV;

	private String[][] infos = new String[][] { { "上门服务费", "工时费" }, { "理疗价格", "理疗时长" }, { "原材料价格", "加工费" } };

	private JSONObject jsonObj = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_hour_point_content_info_pager);
		x.view().inject(this);
		Bundle extras = IntentUtils.getIntentBundle(this);
		if (extras != null) {
			type = extras.getInt("type");
			setColumnText(extras.getString("title"));
			String json = extras.getString("json");
			if (OtherUtils.isNotEmpty(json)) {
				jsonObj = JSONObject.parseObject(json);
			}
		}
		ViewUtils.setVisibility(View.VISIBLE, rightTV);
		ViewUtils.setTextData(rightTV, "立即下单");
		ViewUtils.setOnClickListener(rightTV, this);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		ViewUtils.setTextData(oneTV, infos[type][0]);
		ViewUtils.setTextData(twoTV, infos[type][1]);
		String content = "";
		String oneEtContent = "";
		String twoEtContent = "";
		String threeEtContent = "";
		if (jsonObj != null) {
			switch (type) {
			case 0:
				content = jsonObj.getString("Fault");
				oneEtContent = jsonObj.getString("ToDoorPrice");
				twoEtContent = jsonObj.getString("RepairPrice");
				threeEtContent = jsonObj.getString("Coments");
				break;
			case 1:
				ViewUtils.setTextData(threeTV, "分钟");
				content = jsonObj.getString("Service");
				oneEtContent = jsonObj.getString("Cost");
				twoEtContent = jsonObj.getString("Hour");
				threeEtContent = jsonObj.getString("Notice");
				break;
			case 2:
				content = jsonObj.getString("Service");
				oneEtContent = jsonObj.getString("MaterialCost");
				twoEtContent = jsonObj.getString("Price");
				threeEtContent = jsonObj.getString("Coments");
				break;
			}
			ViewUtils.setTextData(oneET, content);
			ViewUtils.setTextData(twoET, oneEtContent);
			ViewUtils.setTextData(threeET, twoEtContent);
			ViewUtils.setTextData(fourET, threeEtContent);
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
		if (jsonObj != null) {
			if (StarApplication.userInfo != null) {
				switch (type) {
				case 0:
					jsonObj.put("Coments", oneET.getText().toString());
					jsonObj.put("ToDoorPrice", twoET.getText().toString());
					jsonObj.put("RepairPrice", threeET.getText().toString());
					break;
				case 1:
					jsonObj.put("Service", oneET.getText().toString());
					jsonObj.put("Cost", twoET.getText().toString());
					jsonObj.put("Hour", threeET.getText().toString());
					break;
				case 2:
					jsonObj.put("Coments", oneET.getText().toString());
					jsonObj.put("MaterialCost", twoET.getText().toString());
					jsonObj.put("Price", threeET.getText().toString());
					break;
				}
				Bundle ext = new Bundle();
				ext.putString("json", jsonObj.toJSONString());
				ext.putInt("type", type);
				IntentUtils.goTo(this, HourPointContactActivity.class, ext);
			} else {
				ToastUtil.showInfo(getActivity(), "您未登录,不能进行此操作");
				return;
			}
		}
	}

}
