package com.sevenstar.steward2016.view.ad;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.ShiGuoTaoBaseAdapter;
import com.lib.shiguotao.listener.OnGallerySwitchListener;
import com.lib.shiguotao.view.ad.AdBaseGalleryHelper;

public class AdVideoGalleryHelper extends AdBaseGalleryHelper<JSONObject> {

	public AdVideoGalleryHelper(FrameLayout galleryLayout, Context context) {
		super(galleryLayout, context);
	}

	@Override
	public void init(final ShiGuoTaoBaseAdapter<JSONObject> adapter, int switchTime) {
		super.init(adapter, switchTime);
		mAdGallery.init(adapter, switchTime, new OnGallerySwitchListener() {
			@Override
			public void onGallerySwitch(int position) {
				if (mRadioGroup != null) {
					mRadioGroup.check(mRadioGroup.getChildAt(position).getId());
				}
				// if (mPicTitle != null) {
				// mPicTitle.setText(adapter.getItem(position).getTopicname());
				// }
			}
		});

		mAdGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//JSONObject entity = adapter.getItem(position);
			}
		});
	}
}
