package com.sevenstar.steward2016.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.HappyShopMainAdapter;

/**
 * 钟点服务 列表界面
 */
public class HourPointServiceActivity extends BannerBaseActivity {
	@ViewInject(R.id.gridview)
	GridView gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_huor_point_service_pager);
		setColumnText("钟点服务");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		list.add(R.drawable.hour_point_service_baojie_qingxi_icon);
		list.add(R.drawable.hour_point_service_jianxiu_fuwu_icon);
		list.add(R.drawable.hour_point_service_siren_chushi_icon);
		list.add(R.drawable.hour_point_service_zhongyi_liliao_icon);
		list.add(R.drawable.hour_point_service_baojing_fuwu_icon);
		list.add(R.drawable.hour_point_service_jiesong_fuwu_icon);
		gridview.setAdapter(new HappyShopMainAdapter(getActivity(), list));
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Class<? extends Activity> mClass = null;
				switch (position) {
				case 0:// 保洁清洗
					mClass = CleanKeepingCleanoutActivity.class;
					break;
				case 1:// 检修服务
					mClass = OverhaulServiceActivity.class;
					break;
				case 2:// 私人厨师
					mClass = PrivateChefActivity.class;
					break;
				case 3:// 中医理疗
					mClass = ChineseMedicinePhysiotherapyActivity.class;
					break;
				case 5://接送服务
					mClass = ShuttleServiceActivity.class;
					break;
				}
				if (mClass != null) {
					// Intent.FLAG_ACTIVITY_NEW_TASK
					IntentUtils.goTo(getActivity(), mClass);
				}
			}
		});
	}

	@Override
	public Activity getActivity() {
		return this;
	}

}
