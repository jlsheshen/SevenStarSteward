package com.sevenstar.steward2016.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
/**
 *	我的客服
 */
public class MyKeFuActivity extends BannerBaseActivity implements OnClickListener {

	@ViewInject(R.id.name)
	TextView nameTV;
	@ViewInject(R.id.phone)
	TextView phoneTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_my_kefu_page);
		setColumnText("我的客服");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		// String employeeName =
		// StarApplication.userInfo.getString("EmployeeTwoName");
		// String employeeTel =
		// StarApplication.userInfo.getString("EmployeeTwoTel");
		// ViewUtils.setTextData(nameTV, OtherUtils.isEmpty(employeeName) ? "暂无"
		// : employeeName);
		// ViewUtils.setTextData(phoneTV, OtherUtils.isEmpty(employeeTel) ?
		// "400-8080-9090" : employeeTel);
		ViewUtils.setTextData(phoneTV, "400-8080-9090");
		phoneTV.setOnClickListener(this);

	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		IntentUtils.callPhone(getBaseContext(), "tel:" + phoneTV.getText().toString());
	}

}
