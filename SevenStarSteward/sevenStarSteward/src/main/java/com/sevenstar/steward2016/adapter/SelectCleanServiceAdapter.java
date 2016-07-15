package com.sevenstar.steward2016.adapter;

import java.util.List;

import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;

public class SelectCleanServiceAdapter extends SGTHolderDefAdapter<JSONObject> implements OnCheckedChangeListener {

	public int index = -1;

	public SelectCleanServiceAdapter(Context context, List<JSONObject> list, Object... objects) {
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
		return R.layout.select_clean_service_item_layout;
	}

	// implements TextWatche
	public class FeatureOrderViewHolder extends DefHolderView<JSONObject> {
		@ViewInject(R.id.service_et_one)
		public EditText oneET;
		// @ViewInject(R.id.service_et_two)
		// EditText twoET;
		@ViewInject(R.id.service_et_three)
		public EditText threeET;
		@ViewInject(R.id.service_et_four)
		public EditText fourET;
		@ViewInject(R.id.checkbox)
		public CheckBox checkbox;

		public FeatureOrderViewHolder(View layout) {
			super(layout);
			checkbox.setOnCheckedChangeListener(SelectCleanServiceAdapter.this);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			if (entity != null) {
				// oneET.removeTextChangedListener(this);
				// threeET.removeTextChangedListener(this);
				// fourET.removeTextChangedListener(this);
				ViewUtils.setTextData(oneET, entity.getIntValue("Price") + "Ԫ");
				// ViewUtils.setTextData(twoET,
				// entity.getString("HouseTypeName"));
				ViewUtils.setTextData(threeET, entity.getString("Target"));
				ViewUtils.setTextData(fourET, entity.getString("Hours") + "Сʱ");
				checkbox.setTag(getIndex());
				if (index == getIndex()) {
					// ViewUtils.requestEtFocus(oneET, true);
					// ViewUtils.requestEtFocus(twoET, true);
					// ViewUtils.requestEtFocus(threeET, true);
					// ViewUtils.requestEtFocus(fourET, true);
					// oneET.addTextChangedListener(this);
					// threeET.addTextChangedListener(this);
					// fourET.addTextChangedListener(this);
				} else {
					// ViewUtils.requestEtFocus(oneET, false);
					// ViewUtils.requestEtFocus(twoET, false);
					// ViewUtils.requestEtFocus(threeET, false);
					// ViewUtils.requestEtFocus(fourET, false);
					checkbox.setChecked(false);
				}
			}
		}
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// // TODO Auto-generated method stub
		// if (oneET.isFocusable() && index != -1) {
		// JSONObject json = getItem(index);
		// json.put("Price", oneET.getText().toString().replace("Ԫ", ""));
		// json.put("Target", threeET.getText().toString());
		// json.put("Hours", fourET.getText().toString().replace("Сʱ", ""));
		// }
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before, int
		// count) {
		// // TODO Auto-generated method stub
		//
		// }

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {
			Object obj = buttonView.getTag();
			if (obj != null) {
				index = (Integer) obj;
			}
			notifyDataSetChanged();
		}
	}

}