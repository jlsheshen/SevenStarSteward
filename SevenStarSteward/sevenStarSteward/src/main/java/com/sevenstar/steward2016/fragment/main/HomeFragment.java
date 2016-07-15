package com.sevenstar.steward2016.fragment.main;

import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.fragment.BaseFragment;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.activity.LiveServiceActivity;
import com.sevenstar.steward2016.activity.PersonalCenterActivity;
import com.sevenstar.steward2016.activity.ServiceForTheAgedActivity;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.utils.AdUtils;
/**
 * MAIN 主页
 */
public class HomeFragment extends BaseFragment implements OnClickListener {

	@ViewInject(R.id.live_service_btn)
	ImageView liveServiceBtn;
	@ViewInject(R.id.yanglao_service_btn)
	ImageView yangLaoServiceBtn;
	@ViewInject(R.id.guanjia_service_btn)
	ImageView guanJiaServiceBtn;
	@ViewInject(R.id.zhuanye_service_btn)
	ImageView zhuanYeServiceBtn;
	@ViewInject(R.id.home_head_imageView)
	ImageView headImageView;
	@ViewInject(R.id.home_head_layout)
	RelativeLayout headLayout;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		x.view().inject(this, layout);
		liveServiceBtn.setOnClickListener(this);
		yangLaoServiceBtn.setOnClickListener(this);
		guanJiaServiceBtn.setOnClickListener(this);
		zhuanYeServiceBtn.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
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
		}, "ClassID", "1");
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_main_home_pager;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Class<? extends Activity> mClass = null;
		Bundle bundle = new Bundle();
		String title = "";
		String type = "";
		switch (v.getId()) {
		case R.id.yanglao_service_btn:
			mClass = ServiceForTheAgedActivity.class;
			title = "养老服务";
			type = "1";
			break;
		case R.id.guanjia_service_btn:
			mClass = ServiceForTheAgedActivity.class;
			title = "管家服务";
			type = "2";
			break;
		case R.id.zhuanye_service_btn:
			mClass = ServiceForTheAgedActivity.class;
			title = "专业服务";
			type = "3";
			break;
		case R.id.live_service_btn:
			mClass = LiveServiceActivity.class;
			break;
		}
		bundle.putString("title", title);
		bundle.putString("type", type);
		if (mClass != null) {
			// Intent.FLAG_ACTIVITY_NEW_TASK
			IntentUtils.goTo(getActivity(), mClass, bundle);
		}

	}

}
