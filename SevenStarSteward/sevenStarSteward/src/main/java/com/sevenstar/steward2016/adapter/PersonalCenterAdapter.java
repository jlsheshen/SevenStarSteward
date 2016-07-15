package com.sevenstar.steward2016.adapter;

import java.util.List;
import org.xutils.view.annotation.ViewInject;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;

public class PersonalCenterAdapter extends SGTHolderDefAdapter<Integer> {

	public PersonalCenterAdapter(Context context, List<Integer> list, Object... objects) {
		super(context, list, objects);
	}

	@Override
	protected BaseViewHolder<Integer> getHolderView(View layout) {
		return new PersonalCenterViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.personal_center_item_layout;
	}

	class PersonalCenterViewHolder extends DefHolderView<Integer> {
		@ViewInject(R.id.imageview)
		ImageView imageView;

		public PersonalCenterViewHolder(View layout) {
			super(layout);
		}

		@Override
		public void update(Integer entity, Object... objects) {
			if (entity != 0) {
				imageView.setImageResource(entity);
			}
		}

	}

}
