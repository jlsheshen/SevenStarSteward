package com.sevenstar.steward2016.view;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.constant.GlobalUrl;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class FestivalPackageContentLayout extends ScrollView {
	@ViewInject(R.id.text_one)
	TextView textOne;
	@ViewInject(R.id.text_two)
	TextView textTwo;
	@ViewInject(R.id.text_three)
	TextView textThree;
	@ViewInject(R.id.text_four)
	TextView textFour;
	@ViewInject(R.id.text_five)
	TextView textFive;
	@ViewInject(R.id.text_six)
	TextView textSix;
	@ViewInject(R.id.text_seven)
	TextView textSeven;
	@ViewInject(R.id.activity_image_iv)
	ImageView imageIV;
	private JSONObject jsonObj;
	private JSONObject childJSON;

	public FestivalPackageContentLayout(Context context, JSONObject jsonObj, JSONObject childJSON) {
		super(context);
		this.jsonObj = jsonObj;
		this.childJSON = childJSON;
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setBackgroundColor(getResources().getColor(R.color.home_background_color));
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
			setOverScrollMode(View.OVER_SCROLL_NEVER);
		}
		initView(context);
	}

	private void initView(Context context) {
		inflate(context, R.layout.activity_festival_package_content_item, this);
		x.view().inject(this);
		if (jsonObj != null) {
			ViewUtils.setTextData(textOne, jsonObj.getString("PackageName"));
			ViewUtils.setTextData(textTwo, jsonObj.getString("PackagePrice") + "元");
			ViewUtils.setTextData(textSix, "截止日期:\t" + jsonObj.getString("ReserveDate"));
			ViewUtils.setTextData(textSeven, "数量:\t" + jsonObj.getString("PackageCount"));
		}
		if (childJSON != null) {
			ViewUtils.setTextData(textThree, childJSON.getString("ItemName"));
			ViewUtils.setTextData(textFour, childJSON.getString("ItemContents"));
			ViewUtils.setTextData(textFive, childJSON.getString("Notes"));
			x.image().bind(imageIV, GlobalUrl.ADDR + childJSON.getString("Picture"));
		}
	}

	public FestivalPackageContentLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

}
