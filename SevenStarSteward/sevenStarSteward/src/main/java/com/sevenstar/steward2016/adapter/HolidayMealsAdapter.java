package com.sevenstar.steward2016.adapter;

import java.util.List;

import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.activity.FestivalPackageContentActivity;
import com.sevenstar.steward2016.activity.PrivateVideoContentActivity;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;

public class HolidayMealsAdapter extends SGTHolderDefAdapter<JSONObject> {

	public HolidayMealsAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new HolidayMealsViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.holiday_meals_item_layout;
	}

	class HolidayMealsViewHolder extends DefHolderView<JSONObject> {
		@ViewInject(R.id.holiday_meals_title_tv)
		TextView holidayTitleTV;
		@ViewInject(R.id.holiday_meals_date_tv)
		TextView holidayDateTV;
		@ViewInject(R.id.holiday_meals_number_tv)
		TextView holidayNumberTV;
		@ViewInject(R.id.holiday_meals_money_tv)
		TextView holidayMoneyTV;
		@ViewInject(R.id.holiday_meals_item_size_tv)
		TextView itemSizeTV;

		public HolidayMealsViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(holidayTitleTV, entity.getString("PackageName"));
			ViewUtils.setTextData(holidayDateTV, entity.getString("ReserveDate"));
			ViewUtils.setTextData(holidayNumberTV, entity.getString("PackageCount"));
			ViewUtils.setTextData(holidayMoneyTV, entity.getString("PackagePrice"));
			ViewUtils.setTextData(itemSizeTV, entity.getString("ItemCount"));
		}

	}

}
