package com.sevenstar.steward2016.utils;
/**
 * 广告Banner工具类
 */
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.AdVideoAdapter;
import com.sevenstar.steward2016.view.ad.AdVideoGalleryHelper;
public class AdUtils {

	public static View getAdLayout(Context context, List<JSONObject> bannerList) {
		FrameLayout layout = ViewUtils.getLayout(context, R.layout.adgallery_hellper);
		AdVideoGalleryHelper galleryHelper = new AdVideoGalleryHelper(layout, context);
		galleryHelper.init(new AdVideoAdapter(context, bannerList), 5000);
		galleryHelper.initPicTitle();
		if (bannerList.size() > 1) {
			galleryHelper.addRadioGroup(context, bannerList.size(), R.drawable.dot_n, R.drawable.selector_dot);
		}
		galleryHelper.startAutoSwitch();
		return galleryHelper.getLayout();
	}

	public static void getAdLayout(Context context, List<JSONObject> bannerList, RelativeLayout rootView, String tag) {
		FrameLayout layout = ViewUtils.getLayout(context, R.layout.adgallery_hellper_two);
		AdVideoGalleryHelper galleryHelper = new AdVideoGalleryHelper(layout, context);
		galleryHelper.init(new AdVideoAdapter(context, bannerList,tag), 5000);
		galleryHelper.initPicTitle();
		if (bannerList.size() > 1) {
			galleryHelper.addRadioGroup(context, bannerList.size(), R.drawable.dot_n, R.drawable.selector_dot);
		}
		galleryHelper.startAutoSwitch();
		rootView.addView(galleryHelper.getLayout(), new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT));
	}
}
