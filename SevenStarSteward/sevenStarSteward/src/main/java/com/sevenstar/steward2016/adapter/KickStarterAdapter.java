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

public class KickStarterAdapter extends SGTHolderDefAdapter<JSONObject> {

	public KickStarterAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new KickStarterViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.kick_starter_item_layout;
	}

	class KickStarterViewHolder extends DefHolderView<JSONObject> {
		@ViewInject(R.id.kick_starter_image_iv)
		ImageView imageIV;
		@ViewInject(R.id.kick_starter_title_tv)
		TextView titleTV;
		@ViewInject(R.id.kick_starter_number_tv)
		TextView numberTV;
		@ViewInject(R.id.kick_starter_money_tv)
		TextView moneyTV;

		public KickStarterViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(titleTV, entity.getString("Name"));
			ViewUtils.setTextData(numberTV, entity.getString("Number")+"ธ๖");
			ViewUtils.setTextData(moneyTV, entity.getString("Price") + "ิช");
			String url = entity.getString("FileFolder");
			x.image().bind(imageIV, GlobalUrl.ADDR + OtherUtils.toUtf8String(url));
		}

	}

}
