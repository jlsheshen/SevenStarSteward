package com.sevenstar.steward2016.adapter;

import java.util.List;

import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.lib.shiguotao.utils.ConvertUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;

public class LiveServiceAdapter extends SGTHolderDefAdapter<Integer> {

	public LiveServiceAdapter(Context context, List<Integer> list, Object... objects) {
		super(context, list, objects);
		this.context = context;
	}

	@Override
	protected BaseViewHolder<Integer> getHolderView(View layout) {
		return new LiveServiceViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.happy_shop_main_item_layout;
	}

	class LiveServiceViewHolder extends DefHolderView<Integer> {
		@ViewInject(R.id.imageview)
		ImageView imageView;

		public LiveServiceViewHolder(View layout) {
			super(layout);
			imageView.getLayoutParams().height = ConvertUtils.dp2px(context, 120);
		}

		@Override
		public void update(Integer entity, Object... objects) {
			if (entity != 0) {
				imageView.setImageResource(entity);
			}
		}

	}

}
