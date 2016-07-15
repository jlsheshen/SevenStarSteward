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
 *	�����ײͶ��������������
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
		setColumnText("�����ײͶ���");
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


				ViewUtils.setTextData(fiveTV,"�۸�"+ jsonObj.getString("Price")+"Ԫ");
				ViewUtils.setTextData(sixTV, "�ۿۣ�"+((int)(jsonObj.getFloatValue("Discount")*100))+"%");
				ViewUtils.setTextData(sevenTV,"��ʼʱ��\n"+(OtherUtils.isNotEmpty(jsonObj.getString("StartDate"))?jsonObj.getString("StartDate"):"����"));
				ViewUtils.setTextData(eightTV, "��ֹʱ��\n"+(OtherUtils.isNotEmpty(jsonObj.getString("EndDate"))?jsonObj.getString("EndDate"):"����"));

				ViewUtils.setTextData(nineTV,"��ͬ��ţ�"+(OtherUtils.isNotEmpty(jsonObj.getString("SerialNumber"))?jsonObj.getString("SerialNumber"):"����"));
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
