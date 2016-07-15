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

public class RentInfoAdapter extends SGTHolderDefAdapter<JSONObject> {

	public RentInfoAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new RentInfoViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.rent_info_item_layout;
	}

	class RentInfoViewHolder extends DefHolderView<JSONObject> {
		@ViewInject(R.id.rent_info_image_iv)
		ImageView imageIV;
		@ViewInject(R.id.rent_info_title_tv)
		TextView titleTV;
		@ViewInject(R.id.rent_info_gongnen_tv)
		TextView gongnenTV;
		@ViewInject(R.id.rent_info_money_tv)
		TextView moneyTV;

		public RentInfoViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(titleTV, entity.getString("Name"));
			ViewUtils.setTextData(gongnenTV, entity.getString("Status"));
			ViewUtils.setTextData(moneyTV, entity.getString("Price") + "Ԫ");
			String url = entity.getString("FileFolder");
			x.image().bind(imageIV, GlobalUrl.ADDR + OtherUtils.toUtf8String(url));
		}

	}

}
