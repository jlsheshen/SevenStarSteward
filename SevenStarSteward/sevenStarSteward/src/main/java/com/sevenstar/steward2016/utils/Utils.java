package com.sevenstar.steward2016.utils;

import com.lib.shiguotao.manager.AppManager;
import com.lib.shiguotao.utils.ConvertUtils;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.sevenstar.steward2016.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class Utils {

	@SuppressLint("InflateParams")
	public static void showEditTextDialog(Context context, String content, final OnClickListener comfirmOnClickListener,
			final OnClickListener cancelOnClickListener, String title, boolean isSingle) {
		View view = LayoutInflater.from(context).inflate(R.layout.clean_order_input_content_dialog_layout, null);
		DialogUtils.show(context, R.style.custom_dialog_style, 0, view, Gravity.CENTER, false);
		DialogUtils.setLayoutParams(AppManager.getInstance().getWindowSizeEntity().getWidth() - ConvertUtils.dip2px(context, 80),
				WindowManager.LayoutParams.WRAP_CONTENT);
		TextView comfirmTV = (TextView) view.findViewById(R.id.error_dialog_layout_comfirm_tv);
		TextView cancelTV = (TextView) view.findViewById(R.id.error_dialog_layout_cancel_tv);
		View lineView = view.findViewById(R.id.error_dialog_layout_line);
		final EditText contentET = (EditText) view.findViewById(R.id.error_dialog_layout_content_tv);
		TextView titleTV = (TextView) view.findViewById(R.id.error_dialog_layout_ti_shi_tv);
		if (OtherUtils.isNotEmpty(title)) {
			titleTV.setText(title);
		}
		if (OtherUtils.isNotEmpty(content)) {
			contentET.setText(content);
		}
		comfirmTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogUtils.closeDialog();
				if (comfirmOnClickListener != null) {
					v.setTag(contentET.getText().toString());
					comfirmOnClickListener.onClick(v);
				}
			}
		});
		if (isSingle) {
			cancelTV.setVisibility(View.GONE);
			lineView.setVisibility(View.GONE);
		} else {
			cancelTV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					DialogUtils.closeDialog();
					if (cancelOnClickListener != null) {
						cancelOnClickListener.onClick(v);
					}

				}
			});
		}
		DialogUtils.showDialog();
	}
}
