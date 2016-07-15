package com.sevenstar.steward2016.adapter;

import java.util.List;

import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.lib.shiguotao.utils.ConvertUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;

public class HappyShopMainAdapter extends SGTHolderDefAdapter<Integer> {

	private Object[] objects;

	public HappyShopMainAdapter(Context context, List<Integer> list, Object... objects) {
		super(context, list, objects);
		this.objects = objects;
	}

	@Override
	protected BaseViewHolder<Integer> getHolderView(View layout) {
		return new HappyShopMainViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.happy_shop_main_item_layout;
	}

	class HappyShopMainViewHolder extends DefHolderView<Integer> {
		@ViewInject(R.id.imageview)
		ImageView imageView;

		public HappyShopMainViewHolder(View layout) {
			super(layout);
			LayoutParams params = imageView.getLayoutParams();
			int height = 0;
			if (objects != null && objects.length > 0) {
				height = (Integer) objects[0];
			} else {
				height = ConvertUtils.dp2px(context, 114);
			}
			if (params == null) {
				params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, height);
			} else {
				params.height = height;
			}
			imageView.setLayoutParams(params);
		}

		@Override
		public void update(Integer entity, Object... objects) {
			if (entity == 0) {
				imageView.setImageResource(R.color.white);
			} else {
				imageView.setImageResource(entity);
			}

		}

	}

}
