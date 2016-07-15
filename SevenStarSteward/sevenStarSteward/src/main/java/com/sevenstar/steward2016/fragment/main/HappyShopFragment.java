package com.sevenstar.steward2016.fragment.main;

import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.fragment.BaseFragment;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.activity.CommodityInfoActivity;
import com.sevenstar.steward2016.activity.FeatureOrderActivity;
import com.sevenstar.steward2016.activity.KickStarterActivity;
import com.sevenstar.steward2016.activity.RentInfoActivity;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.utils.AdUtils;
/**
 * MAIN ¿÷π∫
 */
public class HappyShopFragment extends BaseFragment implements OnClickListener {

	@ViewInject(R.id.shang_pin_xin_xi_btn)
	ImageView shangPinXinXiBtn;
	@ViewInject(R.id.zu_yong_xin_xi_btn)
	ImageView zuYongXinXiBtn;
	@ViewInject(R.id.zhong_chou_ping_tai_btn)
	ImageView zhongChouPingTaiBtn;
	@ViewInject(R.id.te_se_ding_gou_btn)
	ImageView teSeDingGouBtn;
	@ViewInject(R.id.happy_shop_head_imageView)
	ImageView headImageView;
	@ViewInject(R.id.happy_shop_head_layout)
	RelativeLayout headLayout;

	@Override
	protected void init() {
		x.view().inject(this, layout);
	}

	@Override
	protected void initData() {
		shangPinXinXiBtn.setOnClickListener(this);
		zuYongXinXiBtn.setOnClickListener(this);
		zhongChouPingTaiBtn.setOnClickListener(this);
		teSeDingGouBtn.setOnClickListener(this);
		HttpSendMananger.sendPost(GlobalUrl.GET_AppAdInfo_AD_LIST_URL, new BaseSimpleHttpCallback(getActivity(), false, false) {
			@Override
			public void onSuccess(JSONObject data) {
				if (data != null) {
					List<JSONObject> listData = JSONObject.parseArray(data.getString("data"), JSONObject.class);
					if (listData != null && listData.size() > 0) {
						headLayout.addView(AdUtils.getAdLayout(getActivity(), listData));
						ViewUtils.setVisibility(View.GONE, headImageView);
						ViewUtils.setVisibility(View.VISIBLE, headLayout);
					}
				}
			}
		}, "ClassID", "6");
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.fragmnt_main_happy_pager;
	}

	@Override
	public void onClick(View v) {
		Class<? extends Activity> mClass = null;
		switch (v.getId()) {
		case R.id.shang_pin_xin_xi_btn:
			mClass = CommodityInfoActivity.class;
			break;
		case R.id.zu_yong_xin_xi_btn:
			mClass = RentInfoActivity.class;
			break;
		case R.id.zhong_chou_ping_tai_btn:
			mClass = KickStarterActivity.class;
			break;
		case R.id.te_se_ding_gou_btn:
			mClass = FeatureOrderActivity.class;
			break;
		}
		if (mClass != null) {
			// Intent.FLAG_ACTIVITY_NEW_TASK
			IntentUtils.goTo(getActivity(), mClass);
		}

	}

}
