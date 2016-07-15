package com.sevenstar.steward2016.activity;

import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.LogUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.utils.AdUtils;
/**
 * 社区新闻内容详情界面
 */
public class CommunityNewsContentActivity extends BannerBaseActivity {
	@ViewInject(R.id.news_content_head_layout)
	RelativeLayout headLayout;
	@ViewInject(R.id.news_content_tv_one)
	TextView oneTV;
	@ViewInject(R.id.news_content_tv_two)
	TextView twoTV;
	@ViewInject(R.id.news_content_tv_three)
	TextView threeTV;
	@ViewInject(R.id.news_content_tv_four)
	TextView fourTV;
//	@ViewInject(R.id.news_content_tv_five)
//	TextView fiveTV;
//	@ViewInject(R.id.news_content_tv_six)
//	TextView sixTV;
	private JSONObject jsonObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_community_news_content_page);
		setColumnText("社区新闻");
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
//				ViewUtils.setTextData(twoTV, jsonObj.getString("TypeName"));
//				ViewUtils.setTextData(threeTV, jsonObj.getString("ComminityName"));
				ViewUtils.setTextData(twoTV, jsonObj.getString("Date"));
				ViewUtils.setTextData(threeTV, jsonObj.getString("Time"));
				ViewUtils.setTextData(fourTV, jsonObj.getString("Content"));
				HttpSendMananger.sendPost(GlobalUrl.GET_COMMUNITY_BANNER_IMAGE_URL, new BaseSimpleHttpCallback(this, true, false) {
					@Override
					public void onSuccess(JSONObject data) {
						List<JSONObject> listData = JSONObject.parseArray(data.getString("images"), JSONObject.class);
						if (listData != null && listData.size() > 0) {
							AdUtils.getAdLayout(getActivity(), listData, headLayout, "FileFolder");
						}
					}
				}, "NewsID", jsonObj.getString("NewsID"), "communityType", "1");
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
