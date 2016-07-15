package com.sevenstar.steward2016.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.LogUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
/**
 *	服务套餐订单内容详情界面
 */
public class MyServicePackageOrderContentActivity extends BannerBaseActivity {

	@ViewInject(R.id.content_tv_one)
	TextView oneTV;
	@ViewInject(R.id.content_tv_two)
	TextView twoTV;
	@ViewInject(R.id.content_tv_three)
	TextView threeTV;
	@ViewInject(R.id.content_tv_four)
	TextView fourTV;
	@ViewInject(R.id.content_tv_five)
	TextView fiveTV;
	@ViewInject(R.id.content_tv_six)
	TextView sixTV;
	@ViewInject(R.id.content_tv_seven)
	TextView sevenTV;
	@ViewInject(R.id.content_tv_eight)
	TextView eightTV;
	@ViewInject(R.id.content_tv_nine)
	TextView nineTV;
	@ViewInject(R.id.content_tv_ten)
	TextView tenTV;
	private JSONObject jsonObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_my_service_package_order_content_page);
		setColumnText("服务套餐订单");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		Bundle bundle = IntentUtils.getIntentBundle(this);
		if (bundle != null) {
			try {
				String json = bundle.getString("json");
				jsonObj = JSONObject.parseObject(json);

				ViewUtils.setTextData(oneTV, jsonObj.getString("TypeName"));
				ViewUtils.setTextData(twoTV, jsonObj.getString("OrderNo"));
				ViewUtils.setTextData(threeTV, jsonObj.getString("OrderDate"));
				ViewUtils.setTextData(fourTV, jsonObj.getString("StatusName"));


				ViewUtils.setTextData(fiveTV,"价格："+ jsonObj.getString("Price")+"元");
				ViewUtils.setTextData(sixTV, "折扣："+((int)(jsonObj.getFloatValue("Discount")*100))+"%");
				ViewUtils.setTextData(sevenTV,"开始时间\n"+(OtherUtils.isNotEmpty(jsonObj.getString("StartDate"))?jsonObj.getString("StartDate"):"暂无"));
				ViewUtils.setTextData(eightTV, "截止时间\n"+(OtherUtils.isNotEmpty(jsonObj.getString("EndDate"))?jsonObj.getString("EndDate"):"暂无"));

				ViewUtils.setTextData(nineTV,"合同编号："+(OtherUtils.isNotEmpty(jsonObj.getString("SerialNumber"))?jsonObj.getString("SerialNumber"):"暂无"));
				ViewUtils.setTextData(tenTV,jsonObj.getString("Service"));



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
