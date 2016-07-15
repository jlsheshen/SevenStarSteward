package com.sevenstar.steward2016.adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;
import com.sevenstar.steward2016.constant.GlobalUrl;

public class VideoServiceAdapter extends SGTHolderDefAdapter<JSONObject> {

	public VideoServiceAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new VideoServiceViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.activity_video_service_item_layout;
	}

	class VideoServiceViewHolder extends DefHolderView<JSONObject> {

		@ViewInject(R.id.video_head_iv)
		ImageView headIV;
		@ViewInject(R.id.video_content_tv)
		TextView contentTV;

		public VideoServiceViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(contentTV, entity.getString("Introduction"));
			x.image().bind(headIV, GlobalUrl.ADDR+entity.getString("Picture"));
		}

	}

}
