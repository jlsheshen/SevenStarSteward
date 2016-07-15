package com.sevenstar.steward2016.adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.ShiGuoTaoBaseAdapter;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.activity.ShopContentActivity;
import com.sevenstar.steward2016.constant.GlobalUrl;

public class CommodityAdapter extends ShiGuoTaoBaseAdapter<JSONObject> implements OnClickListener {

	public CommodityAdapter(Context context, List<JSONObject> list) {
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (list != null && list.size() > 0) {
			int size = list.size() % 2;
			if (size != 0) {
				return list.size() / 2 + 1;
			}
			return list.size() / 2;
		}
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommodityViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(getLayoutRes(), null);
			viewHolder = getHolderView(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (CommodityViewHolder) convertView.getTag();
		}
		position = position * 2;
		JSONObject data = position + 1 < list.size() ? getItem(position + 1) : null;
		viewHolder.update(getItem(position), data);
		return convertView;
	}

	private CommodityViewHolder getHolderView(View convertView) {
		// TODO Auto-generated method stub
		return new CommodityViewHolder(convertView);
	}

	private int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.commodity_item_layout;
	}

	class CommodityViewHolder {
		@ViewInject(R.id.commodity_image_one_iv)
		ImageView imageOneIV;
		@ViewInject(R.id.commodity_image_two_iv)
		ImageView imageTwoIV;
		@ViewInject(R.id.commodity_title_one_tv)
		TextView titleOneTV;
		@ViewInject(R.id.commodity_title_two_tv)
		TextView titleTwoTV;
		@ViewInject(R.id.commodity_money_one_tv)
		TextView moneyOneTV;
		@ViewInject(R.id.commodity_money_two_tv)
		TextView moneyTwoTV;

		public CommodityViewHolder(View layout) {
			x.view().inject(this, layout);
			imageOneIV.setOnClickListener(CommodityAdapter.this);
			imageTwoIV.setOnClickListener(CommodityAdapter.this);
		}

		public void update(JSONObject entity, JSONObject data) {
			ViewUtils.setTextData(titleOneTV, entity.getString("Name"));
			ViewUtils.setTextData(moneyOneTV, "гд" + entity.getString("SellPrice"));
			String url = entity.getString("FileFolder");
			x.image().bind(imageOneIV, GlobalUrl.ADDR + OtherUtils.toUtf8String(url));
			imageOneIV.setTag(entity);
			if (data != null) {
				ViewUtils.setTextData(titleTwoTV, data.getString("Name"));
				ViewUtils.setTextData(moneyTwoTV, "гд" + data.getString("SellPrice"));
				url = data.getString("FileFolder");
				x.image().bind(imageTwoIV, GlobalUrl.ADDR + OtherUtils.toUtf8String(url));
				imageTwoIV.setTag(data);
			} else {
				ViewUtils.setBackgroundColorRes(imageTwoIV, android.R.color.transparent);
				imageTwoIV.setTag(null);
			}
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getTag() != null) {
			JSONObject jsonObject = (JSONObject) v.getTag();
			Bundle extras = new Bundle();
			extras.putString("json", jsonObject.toJSONString());
			extras.putInt("serviceType", 1);
			IntentUtils.goTo(context, ShopContentActivity.class, extras);
		}
	}

}
