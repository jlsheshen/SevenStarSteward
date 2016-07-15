package com.sevenstar.steward2016.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.sevenstar.steward2016.R;
/**
 *	我的近邻
 */
public class MyNeighbourActivity extends BannerBaseActivity implements OnClickListener {
	@ViewInject(R.id.community_news_tv)
	View communityNewsBT;
	@ViewInject(R.id.community_activity_tv)
	View communityActivityBT;
	@ViewInject(R.id.community_complaint_tv)
	View communityComplaintBT;
	@ViewInject(R.id.community_rating_tv)
	View communityRatingBT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_my_neighbour_page);
		setColumnText("我的近邻");
		x.view().inject(this);
		communityNewsBT.setOnClickListener(this);
		communityActivityBT.setOnClickListener(this);
		communityComplaintBT.setOnClickListener(this);
		communityRatingBT.setOnClickListener(this);
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Class<?> mClass = null;
		switch (v.getId()) {
		case R.id.community_news_tv:
			mClass = CommunityNewsActivity.class;
			break;
		case R.id.community_activity_tv:
			mClass = CommunityActivityActivity.class;
			break;
		case R.id.community_complaint_tv:
			mClass = CommunityComplaintActivity.class;
			break;
		case R.id.community_rating_tv:
			mClass = CommunityRatingActivity.class;
			break;
		}
		if (mClass != null) {
			IntentUtils.goTo(getActivity(), mClass);
		}

	}

}
