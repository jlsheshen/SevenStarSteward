package com.sevenstar.steward2016.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabWidget;

import com.lib.shiguotao.utils.ConvertUtils;
import com.lib.shiguotao.utils.ViewUtils;

public class TabStarButton extends FrameLayout {

	public TabStarButton(Context context) {
		super(context);
	}

	public TabStarButton(Context context, int drawable) {
		super(context);
		TabWidget.LayoutParams params = new TabWidget.LayoutParams(0, TabWidget.LayoutParams.WRAP_CONTENT);
		params.weight = 1;
		params.gravity = Gravity.CENTER;
		setLayoutParams(params);
		ImageView imageView = new ImageView(getContext());
		ViewUtils.setImageResource(imageView, drawable);
		addView(imageView, new FrameLayout.LayoutParams(ConvertUtils.dp2px(getContext(), 66.33f), ConvertUtils.dp2px(getContext(), 56.66f),
				Gravity.CENTER));
	}

}
