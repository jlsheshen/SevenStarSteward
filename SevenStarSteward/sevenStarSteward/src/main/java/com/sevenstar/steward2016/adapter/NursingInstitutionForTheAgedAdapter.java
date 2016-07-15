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

public class NursingInstitutionForTheAgedAdapter extends SGTHolderDefAdapter<JSONObject> {

	public NursingInstitutionForTheAgedAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new NursingInstitutionForTheAgedViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.nursing_institution_for_the_aged_item_layout;
	}

	class NursingInstitutionForTheAgedViewHolder extends DefHolderView<JSONObject> {
		@ViewInject(R.id.name_tv)
		TextView nameTV;
		@ViewInject(R.id.address_tv)
		TextView addressTV;
		@ViewInject(R.id.phone_tv)
		TextView phoneTV;
		@ViewInject(R.id.money_tv)
		TextView moneyTV;
		@ViewInject(R.id.head_iv)
		ImageView headIV;

		public NursingInstitutionForTheAgedViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(nameTV, entity.getString("Name"));
			ViewUtils.setTextData(addressTV, entity.getString("Address"));
			ViewUtils.setTextData(phoneTV, entity.getString("Tel"));
			ViewUtils.setTextData(moneyTV, entity.getString("Price") + "Ԫ");
			x.image().bind(headIV, GlobalUrl.ADDR + entity.getString("Picture"));
		}

	}

}
