package com.sevenstar.steward2016.adapter;

import java.util.List;

import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.SGTHolderDefAdapter;
import com.lib.shiguotao.adapter.holder.BaseViewHolder;
import com.lib.shiguotao.manager.ImageManager;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.lib.shiguotao.view.NetImageView;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.activity.HourPointContactActivity;
import com.sevenstar.steward2016.activity.HourPointContentActivity;
import com.sevenstar.steward2016.adapter.viewholder.DefHolderView;
import com.sevenstar.steward2016.constant.GlobalUrl;

public class TechnicianStraightTrystAdapter extends SGTHolderDefAdapter<JSONObject> {

	public TechnicianStraightTrystAdapter(Context context, List<JSONObject> list, Object... objects) {
		super(context, list, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected BaseViewHolder<JSONObject> getHolderView(View layout) {
		// TODO Auto-generated method stub
		return new TechnicianStraightTrystViewHolder(layout);
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.technician_item_layout;
	}

	class TechnicianStraightTrystViewHolder extends DefHolderView<JSONObject> implements OnClickListener {

		@ViewInject(R.id.head_iv)
		NetImageView headIV;
		@ViewInject(R.id.title_tv)
		TextView titleTV;
		@ViewInject(R.id.jibie_tv)
		TextView jiBieTV;
		@ViewInject(R.id.jingyan_tv)
		TextView jingYanTV;
		@ViewInject(R.id.diqu_tv)
		TextView diQuTV;
		@ViewInject(R.id.yuyue_tv)
		View yuYueBT;

		public TechnicianStraightTrystViewHolder(View layout) {
			super(layout);
			yuYueBT.setOnClickListener(this);
		}

		@Override
		public void update(JSONObject entity, Object... objects) {
			ViewUtils.setTextData(titleTV, "技师:\t" + entity.getString("EmployeeName"));
			ViewUtils.setTextData(jiBieTV, entity.getString("StarValue"));
			ViewUtils.setTextData(jingYanTV, entity.getString("ServiceHours"));
			ViewUtils.setTextData(diQuTV, entity.getString("Address"));
			ImageManager.getInstance().setNetBitmap(0, GlobalUrl.ADDR + entity.getString("Picture"), headIV);
			yuYueBT.setTag(entity);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			JSONObject entity = (JSONObject) v.getTag();
			if (entity != null) {
				if (StarApplication.userInfo == null) {
					ToastUtil.showInfo(context, "您未登录,不能进行此操作");
					return;
				}
				Bundle ext = new Bundle();
				ext.putString("json", entity.toJSONString());
				ext.putInt("type", 7);
				IntentUtils.goTo(context, HourPointContactActivity.class, ext);
			}
		}

	}
}
