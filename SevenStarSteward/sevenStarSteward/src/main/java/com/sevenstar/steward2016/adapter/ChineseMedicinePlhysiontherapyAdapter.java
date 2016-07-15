package com.sevenstar.steward2016.adapter;

import java.util.List;

import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.activity.HourPointContentActivity;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;

public class ChineseMedicinePlhysiontherapyAdapter extends SGTHolderDefAdapter<JSONObject> {

	public ChineseMedicinePlhysiontherapyAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new ChineseMedicinePlhysiontherapyViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.chinese_medicine_physiotherapy_item_layout;
	}

	class ChineseMedicinePlhysiontherapyViewHolder extends DefHolderView<JSONObject> implements OnClickListener {

		@ViewInject(R.id.plhysiontherapy_title_tv)
		TextView titleTV;
		@ViewInject(R.id.plhysiontherapy_time_tv)
		TextView timeTV;
		@ViewInject(R.id.plhysiontherapy_money_tv)
		TextView moneyTV;
		@ViewInject(R.id.plhysiontherapy_yuyue_iv)
		View yuYueTV;

		public ChineseMedicinePlhysiontherapyViewHolder(View layout) {
			super(layout);
			yuYueTV.setOnClickListener(this);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			yuYueTV.setTag(entity);
			ViewUtils.setTextData(titleTV, entity.getString("PhysicalTypeName"));
			ViewUtils.setTextData(timeTV, entity.getString("Hour") + "·ÖÖÓ");
			ViewUtils.setTextData(moneyTV, entity.getString("Cost") + "Ôª");
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			JSONObject json = (JSONObject) v.getTag();
			Bundle ext = new Bundle();
			ext.putInt("type", 1);
			ext.putString("title", json.getString("PhysicalTypeName"));
			ext.putString("json", json.toJSONString());
			IntentUtils.goTo(context, HourPointContentActivity.class, ext);
		}

	}

}
