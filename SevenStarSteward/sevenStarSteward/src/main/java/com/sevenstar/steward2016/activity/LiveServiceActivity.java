package com.sevenstar.steward2016.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.LiveServiceAdapter;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.utils.AdUtils;

/**
 * 生活服务 列表界面
 */
public class LiveServiceActivity extends BannerBaseActivity {
	@ViewInject(R.id.gridview)
	GridView gridview;
	@ViewInject(R.id.live_service_head_imageView)
	ImageView headImageView;
	@ViewInject(R.id.live_service_head_layout)
	RelativeLayout headLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_live_service_pager);
		setColumnText("生活服务");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		list.add(R.drawable.live_service_jieri_taocan_icon);
		list.add(R.drawable.live_service_yanglao_jigou_icon);
		list.add(R.drawable.live_service_huodong_anpai_icon);
		list.add(R.drawable.live_service_xiuxian_lvxing_icon);
		list.add(R.drawable.live_service_shipin_fuwu_icon);
		list.add(R.drawable.live_service_zhongdian_fuwu_icon);
		gridview.setAdapter(new LiveServiceAdapter(getActivity(), list));
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Class<? extends Activity> mClass = null;
				switch (position) {
				case 0: // 节日套餐
					mClass = HolidayMealsActivity.class;
					break;
				case 1:// 养老机构
					mClass = NursingInstitutionForTheAgedActivity.class;
					break;
				case 2:// 活动安排
					mClass = MainActivityArrangeActivity.class;
					break;
				case 3:// 休闲旅游
					mClass = LeisureTourismActivity.class;
					break;
				case 4:// 视频服务
					mClass = VideoServiceActivity.class;
					break;
				case 5:// 钟点服务
					mClass = HourPointServiceActivity.class;
					break;
				}
				if (mClass != null) {
					// Intent.FLAG_ACTIVITY_NEW_TASK
					IntentUtils.goTo(getActivity(), mClass);
				}
			}
		});

		HttpSendMananger.sendPost(GlobalUrl.GET_AppAdInfo_AD_LIST_URL, new BaseSimpleHttpCallback(getActivity(), false, false) {
			@Override
			public void onSuccess(JSONObject data) {
				if (data != null) {
					List<JSONObject> listData = JSONObject.parseArray(data.getString("data"), JSONObject.class);
					if (listData != null && listData.size() > 0) {
						headLayout.addView(AdUtils.getAdLayout(getActivity(), listData));
						ViewUtils.setVisibility(View.GONE, headImageView);
						ViewUtils.setVisibility(View.VISIBLE, headLayout);
					}
				}
			}
		}, "ClassID", "6");
	}

	@Override
	public Activity getActivity() {
		return this;
	}

}
