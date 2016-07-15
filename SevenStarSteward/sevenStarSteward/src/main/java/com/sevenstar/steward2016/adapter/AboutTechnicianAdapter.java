package com.sevenstar.steward2016.adapter;

import java.util.List;
import android.content.Context;
import android.view.View;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;

public class AboutTechnicianAdapter extends SGTHolderDefAdapter<Object> {

	public AboutTechnicianAdapter(Context context, List<Object> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<Object> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new AboutTechnicianViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.about_technician_item_layout;
	}

	class AboutTechnicianViewHolder extends DefHolderView<Object> {

		public AboutTechnicianViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(Object entity, Object... objects) {

		}

	}

}
