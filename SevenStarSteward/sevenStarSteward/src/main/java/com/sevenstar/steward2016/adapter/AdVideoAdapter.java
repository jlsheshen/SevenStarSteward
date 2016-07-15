package com.sevenstar.steward2016.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.R;
import com.lib.shiguotao.adapter.ShiGuoTaoBaseAdapter;
import com.lib.shiguotao.manager.ImageManager;
import com.lib.shiguotao.view.NetImageView;
import com.sevenstar.steward2016.constant.GlobalUrl;

@SuppressWarnings("deprecation")
public class AdVideoAdapter extends ShiGuoTaoBaseAdapter<JSONObject> {
	private LayoutInflater inflater = null;
	private String imageTag;

	public AdVideoAdapter(Context context, List<JSONObject> list) {
		inflater = LayoutInflater.from(context);
		this.list = list;
		imageTag = "Picture";
	}

	public AdVideoAdapter(Context context, List<JSONObject> list, String imageTag) {
		this.imageTag = imageTag;
		inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.adgallery_image, null);
			Gallery.LayoutParams params = new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT);
			convertView.setLayoutParams(params);
			viewHolder = new ViewHolder();
			viewHolder.imageview = (NetImageView) convertView;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (viewHolder != null) {
			JSONObject entity = list.get(position % list.size());
			ImageManager.getInstance().setNetBitmap(0, GlobalUrl.ADDR + entity.getString(imageTag), viewHolder.imageview);
		}
		return convertView;
	}

	class ViewHolder {
		NetImageView imageview;
	}
}
