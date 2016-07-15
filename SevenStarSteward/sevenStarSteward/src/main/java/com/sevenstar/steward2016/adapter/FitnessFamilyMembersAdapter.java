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

public class FitnessFamilyMembersAdapter extends SGTHolderDefAdapter<JSONObject> {

	public FitnessFamilyMembersAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		return new HappyShopMainViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fitness_family_members_item_layout;
	}

	class HappyShopMainViewHolder extends DefHolderView<JSONObject> {
		@ViewInject(R.id.imageview)
		ImageView imageView;
		@ViewInject(R.id.title)
		TextView titleTV;

		public HappyShopMainViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			switch (entity.getIntValue("type")) {
			case 1:
				ViewUtils.setVisibility(View.GONE, titleTV);
				imageView.setImageResource(R.drawable.fitness_jiahao_icon);
				break;
			case -1:
				ViewUtils.setVisibility(View.GONE, titleTV);
				imageView.setImageResource(R.drawable.fitness_jianhao_icon);
				break;
			default:
				x.image().bind(imageView, GlobalUrl.ADDR + entity.getString("FileFolder"));
				ViewUtils.setVisibility(View.VISIBLE, titleTV);
				ViewUtils.setTextData(titleTV, entity.getString("RelationsName"));
				break;
			}
		}

	}

}
