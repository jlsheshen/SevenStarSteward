package com.sevenstar.steward2016.view;

import java.util.Calendar;

import com.lib.shiguotao.utils.ConvertUtils;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.lib.shiguotao.view.wheel.OnWheelChangedListener;
import com.lib.shiguotao.view.wheel.WheelView;
import com.lib.shiguotao.view.wheel.adapters.DateWheelAdapter;
import com.sevenstar.steward2016.R;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SetTimeDialogLayout implements OnClickListener, OnWheelChangedListener {

	private Activity activity;
	private View diagLayout;
	private WheelView yearWheelView;
	private WheelView monthWheelView;
	private WheelView dayWheelView;
	private WheelView hourWheelView;
	private WheelView minuteWheelView;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int nowYear;
	private DateWheelAdapter yearAdapter;
	private DateWheelAdapter monthAdapter;
	private DateWheelAdapter dayAdapter;
	private DateWheelAdapter hourAdapter;
	private DateWheelAdapter minuteAdapter;

	public SetTimeDialogLayout(Activity activity, final TextView showTimeTV, final OnClickListener onClickListener) {
		this.activity = activity;
		diagLayout = LayoutInflater.from(activity).inflate(R.layout.set_time_dialog_layout, null);
		yearWheelView = (WheelView) diagLayout.findViewById(R.id.set_time_dialog_wheelView_1);
		monthWheelView = (WheelView) diagLayout.findViewById(R.id.set_time_dialog_wheelView_2);
		dayWheelView = (WheelView) diagLayout.findViewById(R.id.set_time_dialog_wheelView_3);
		hourWheelView = (WheelView) diagLayout.findViewById(R.id.set_time_dialog_wheelView_4);
		minuteWheelView = (WheelView) diagLayout.findViewById(R.id.set_time_dialog_wheelView_5);
		yearAdapter = new DateWheelAdapter(activity, 0);
		monthAdapter = new DateWheelAdapter(activity, 1);
		dayAdapter = new DateWheelAdapter(activity, 2);
		hourAdapter = new DateWheelAdapter(activity, 3);
		minuteAdapter = new DateWheelAdapter(activity, 4);
		yearWheelView.setViewAdapter(yearAdapter);
		monthWheelView.setViewAdapter(monthAdapter);
		dayWheelView.setViewAdapter(dayAdapter);
		hourWheelView.setViewAdapter(hourAdapter);
		minuteWheelView.setViewAdapter(minuteAdapter);
		Calendar cal = Calendar.getInstance();
		nowYear = cal.get(Calendar.YEAR);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
		yearWheelView.addChangingListener(this);
		monthWheelView.addChangingListener(this);
		View confirm = ViewUtils.find(diagLayout, R.id.confirm_button);
		View cancel = ViewUtils.find(diagLayout, R.id.cancel_button);
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				year = yearAdapter.getItemData(yearWheelView.getCurrentItem());
				month = monthAdapter.getItemData(monthWheelView.getCurrentItem());
				day = dayAdapter.getItemData(dayWheelView.getCurrentItem());
				hour = hourAdapter.getItemData(hourWheelView.getCurrentItem());
				minute = minuteAdapter.getItemData(minuteWheelView.getCurrentItem());
				String dateTime = String.format("%s-%s-%s\t%s:%s", ConvertUtils.intTwoString(year), ConvertUtils.intTwoString(month),
						ConvertUtils.intTwoString(day), ConvertUtils.intTwoString(hour), ConvertUtils.intTwoString(minute));
				ViewUtils.setTextData(showTimeTV, dateTime);
				month = month - 1;
				DialogUtils.closeDialog();
				if (onClickListener != null) {
					v.setTag(dateTime);
					onClickListener.onClick(v);
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogUtils.closeDialog();
			}
		});
	}

	public void show() {
		// String time = select_time_tv.getText().toString();
		// if (OtherUtils.isNotEmpty(time) && !time.equals("请选择您的时间")) {
		// String[] dateTimes = time.split("\t");
		// String[] dates = dateTimes[0].split("-");
		// year = Integer.parseInt(dates[0]);
		// month = Integer.parseInt(dates[1]) - 1;
		// day = Integer.parseInt(dates[2]);
		// String[] times = dateTimes[1].split(":");
		// hour = Integer.parseInt(times[0]);
		// minute = Integer.parseInt(times[1]);
		// }
		ViewGroup parent = (ViewGroup) diagLayout.getParent();
		if (parent != null) {
			parent.removeView(diagLayout);
		}
		yearWheelView.setCurrentItem(year - nowYear, false);
		monthWheelView.setCurrentItem(month - 1, false);
		dayWheelView.setCurrentItem(day - 1, false);
		hourWheelView.setCurrentItem(hour, false);
		minuteWheelView.setCurrentItem(minute, false);
		DialogUtils.show(activity, R.style.custom_dialog_style, 0, diagLayout, Gravity.CENTER, false);
		DialogUtils.showDialog();
	}

	@Override
	public void onClick(View v) {
		show();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		int year = 0;
		int month = 0;
		if (wheel == yearWheelView) {
			year = yearAdapter.getItemData(newValue);
			month = monthAdapter.getItemData(monthWheelView.getCurrentItem());
		} else if (wheel == monthWheelView) {
			year = yearAdapter.getItemData(yearWheelView.getCurrentItem());
			month = newValue;
		}
		dayAdapter.refreshDayInfo(year, month);
	}
}
