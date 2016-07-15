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

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MyShopOrderContentActivity extends BannerBaseActivity {
	@ViewInject(R.id.head_image)
	ImageView headIV;
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
//	@ViewInject(R.id.content_tv_seven)
//	TextView sevenTV;
//	@ViewInject(R.id.content_tv_eight)
//	TextView eightTV;
//	@ViewInject(R.id.content_tv_nine)
//	TextView nineTV;
//	@ViewInject(R.id.content_tv_ten)
//	TextView tenTV;
	private JSONObject jsonObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_my_shop_order_content_page);
		setColumnText("购物订单");
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
				ViewUtils.setTextData(threeTV, jsonObj.getString("StatusName"));
				ViewUtils.setTextData(fourTV, jsonObj.getString("Date"));
				ViewUtils.setTextData(fiveTV,"价格:"+ jsonObj.getString("Price")+"元");
				ViewUtils.setTextData(sixTV,"购买数量:"+jsonObj.getString("OrderNumber"));
				x.image().bind(headIV, GlobalUrl.ADDR+jsonObj.getString("FileFolder"));
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
