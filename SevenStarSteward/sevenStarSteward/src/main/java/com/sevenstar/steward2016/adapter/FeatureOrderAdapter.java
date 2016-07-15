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
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;
import com.sevenstar.steward2016.constant.GlobalUrl;

public class FeatureOrderAdapter extends SGTHolderDefAdapter<JSONObject> {

	public FeatureOrderAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new FeatureOrderViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.feature_order_item_layout;
	}

	class FeatureOrderViewHolder extends DefHolderView<JSONObject> {
		@ViewInject(R.id.feature_order_image_iv)
		ImageView imageIV;
		@ViewInject(R.id.feature_order_title_tv)
		TextView titleTV;
		@ViewInject(R.id.feature_order_merchant_tv)
		TextView merchantTV;
		@ViewInject(R.id.feature_order_money_tv)
		TextView moneyTV;
		@ViewInject(R.id.feature_order_place_tv)
		TextView placeTV;

		public FeatureOrderViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(titleTV, entity.getString("Name"));
			ViewUtils.setTextData(merchantTV, entity.getString("MerchantName"));
			ViewUtils.setTextData(moneyTV, entity.getString("Price") + "Ԫ");
			ViewUtils.setTextData(placeTV, entity.getString("Address"));
			String url = entity.getString("FileFolder");
			x.image().bind(imageIV, GlobalUrl.ADDR + OtherUtils.toUtf8String(url));
		}

	}

}
