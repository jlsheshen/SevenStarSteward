package com.sevenstar.steward2016.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.view.listview.CustomListView;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.adapter.MyFuKuanOrderAdapter;
import com.sevenstar.steward2016.adapter.MyServicePackageOrderAdapter;
import com.sevenstar.steward2016.callback.HttpBaseCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
/**
 * 我的付款列表界面
 */
public class MyFuKuanOrderActivity extends BannerBaseActivity {
	@ViewInject(R.id.listview)
	CustomListView listView;
	@ViewInject(R.id.load_data_error_layout)
	LoadDataErrorLayout loadingLayout;
	private MyFuKuanOrderAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_my_fukuan_order_page);
		setColumnText("我的付款");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		List<JSONObject> showList = new ArrayList<JSONObject>();
		adapter = new MyFuKuanOrderAdapter(getActivity(), showList);
		listView.setAdapter(adapter);
		listView.initFooterView();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				position -= listView.getHeaderViewsCount();
				if (position >= 0) {
					Bundle extras = new Bundle();
					JSONObject entity = adapter.getItem(position);
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("Name",entity.getString("TypeName"));
					jsonObject.put("Type","1");
					jsonObject.put("Price",entity.getString("PaymentPlan"));
					jsonObject.put("Content",entity.getString("Service"));
					jsonObject.put("OrderID",entity.getString("ContractID"));
					extras.putString("json",jsonObject.toJSONString());
					IntentUtils.goTo(getActivity(), PayActivity.class,0x1000,-1, extras);
				}
			}
		});
		listView.setGetDataCallBack(new HttpBaseCallBack<JSONObject>(getBaseContext(), showList, adapter, listView, loadingLayout, JSONObject.class,
				GlobalUrl.MY_HE_TONG_LIST_URL, "UserID", StarApplication.userInfo.getString("UserID"),"StatusID","1"));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
//		if(requestCode==0x1000&&resultCode==0x1000&&data!=null){
//			String orderID=data.getStringExtra("OrderID");
//			for(int i=0;i<adapter.getCount();i++){
//				JSONObject entity = adapter.getItem(i);
//				if(orderID.equals(entity.getString("ContractID"))){
//					entity.put("StatusID",2);
//					break;
//				}
//			}
//			adapter.notifyDataSetChanged();
//		}

	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}
}
