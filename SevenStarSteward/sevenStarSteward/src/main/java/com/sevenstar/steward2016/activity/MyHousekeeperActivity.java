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
 *	我的管家界面
 */
public class MyHousekeeperActivity extends BannerBaseActivity implements OnClickListener {

	@ViewInject(R.id.name)
	TextView nameTV;
	@ViewInject(R.id.phone)
	TextView phoneTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_my_housekeeper_page);
		setColumnText("我的管家");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		String employeeName = StarApplication.userInfo.getString("EmployeeName");
		String employeeTel = StarApplication.userInfo.getString("EmployeeTel");
		ViewUtils.setTextData(nameTV, OtherUtils.isEmpty(employeeName) ? "暂无" : employeeName);
		if (OtherUtils.isEmpty(employeeTel)) {
			ViewUtils.setTextData(phoneTV, "暂无");
		} else {
			ViewUtils.setTextData(phoneTV, employeeTel);
			phoneTV.setOnClickListener(this);
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
		IntentUtils.callPhone(getBaseContext(), "tel:" + phoneTV.getText().toString());
	}

}
