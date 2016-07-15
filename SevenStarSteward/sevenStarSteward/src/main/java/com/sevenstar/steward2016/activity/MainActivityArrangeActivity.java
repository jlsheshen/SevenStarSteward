package com.sevenstar.steward2016.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;

/**
 * �����������
 */
public class MainActivityArrangeActivity extends BannerBaseActivity implements OnClickListener {

	private int[] imageRes = new int[] { R.drawable.city_activity_arrange_city_icon, R.drawable.city_activity_arrange_gongyi_icon,
			R.drawable.city_activity_arrange_quanzi_icon };
	@ViewInject(R.id.city_activity_layout_id)
	LinearLayout cityActivityLayout;
	@ViewInject(R.id.gongyi_activity_layout_id)
	LinearLayout gongYiActivityLayout;
	@ViewInject(R.id.quanzi_activity_layout_id)
	LinearLayout quanZiActivityLayout;

	// private String[] cityInfo = new String[] { "1���Ż����ݳ���", "2������һ���������",
	// "3��ͼ���������", "4���������ʳ�չ" };
	// private String[] gongYiInfo = new String[] { "1����ͯ��ػ��", "2������Ժ�ذ��",
	// "3���ذ��¹����˻" };
	// private String[] quanZiInfo = new String[] { "1������", "2��è�ֻ" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_activity_arrange_main_pager);
		setColumnText("�����");
		x.view().inject(this);
		initData();
	}

	private void initData() {
		HttpSendMananger.sendPost(GlobalUrl.GET_MAIN_LIVE_SERVICE, new BaseSimpleHttpCallback(this, true, false) {
			@Override
			public void onSuccess(JSONObject data) {
				JSONArray cityData = data.getJSONArray("cityData");
				JSONArray sociallyData = data.getJSONArray("sociallyData");
				JSONArray communityData = data.getJSONArray("communityData");
				setData(cityActivityLayout, imageRes[0], cityData);
				setData(gongYiActivityLayout, imageRes[1], sociallyData);
				setData(quanZiActivityLayout, imageRes[2], communityData);
				cityActivityLayout.setOnClickListener(MainActivityArrangeActivity.this);
				gongYiActivityLayout.setOnClickListener(MainActivityArrangeActivity.this);
				quanZiActivityLayout.setOnClickListener(MainActivityArrangeActivity.this);
			}
		}, "serviceType", "3", "childType", "0", "page", "1");

	}

	public void setData(LinearLayout linLayout, int imageRes, JSONArray info) {
		linLayout.setVisibility(View.VISIBLE);
		ImageView iconIV = ViewUtils.find(linLayout, R.id.activity_item_image_icon);
		iconIV.setImageResource(imageRes);
		LinearLayout childLayout = ViewUtils.find(linLayout, R.id.activity_item_info_layout);
		for (int i = 0, size = childLayout.getChildCount(); i < size; i++) {
			View view = childLayout.getChildAt(i);
			if (info != null && i / 2 < info.size()) {
				if (view instanceof TextView) {
					ViewUtils.setTextData((TextView) view, (i / 2 + 1) + "��" + info.getJSONObject(i / 2).getString("Title"));
				}
			} else {
				view.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void onClick(View v) {
		Class<? extends Activity> mClass = ChildActivityArrangeActivity.class;
		int type = 0;
		switch (v.getId()) {
		case R.id.city_activity_layout_id:
			type = 1;
			break;
		case R.id.gongyi_activity_layout_id:
			type = 2;
			break;
		case R.id.quanzi_activity_layout_id:
			type = 3;
			break;
		}
		if (mClass != null) {
			// Intent.FLAG_ACTIVITY_NEW_TASK
			Bundle bundle = new Bundle();
			bundle.putInt("type", type);
			IntentUtils.goTo(getActivity(), mClass, bundle);
		}
	}

}
