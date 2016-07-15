package com.sevenstar.steward2016.adapter.viewholder;

import org.xutils.x;
import android.view.View;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;

public abstract class DefHolderView<T> extends BaseViewHolder<T> {

	public DefHolderView(View layout) {
		super(layout);
		x.view().inject(this, layout);
	}
	
	public DefHolderView(View layout,Object... objs) {
		this(layout);
	}


}
