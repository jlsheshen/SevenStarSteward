package com.sevenstar.steward2016.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.manager.AppManager;
import com.lib.shiguotao.utils.BitmapChange;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.HeadImageManager;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.SharedPreferencesUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.adapter.PersonalCenterAdapter;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.utils.StewardUtils;

public class PersonalCenterActivity extends BannerBaseActivity implements OnClickListener {
	@ViewInject(R.id.gridview)
	GridView gridview;
	@ViewInject(R.id.personal_nick_name_tv)
	TextView nickNameTV;
	@ViewInject(R.id.personal_balance_tv)
	TextView balanceTV;// 余额
	@ViewInject(R.id.personal_integral_tv)
	TextView integralTV; // 积分
	@ViewInject(R.id.personal_bianma_tv)
	TextView bianmaTV; // 家庭编码
	@ViewInject(R.id.personal_huiyuan_tv)
	TextView huiyuanTV; // 普通会员
	@ViewInject(R.id.personal_wanshan_info_tv)
	TextView wanshanInfoTV;
	@ViewInject(R.id.personal_make_money_master_item_icon)
	ImageView headImage;
	@ViewInject(R.id.banner_Base_activity_head_right_text_view)
	TextView rightTV;
	private HeadImageManager headImageManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_personal_center);
		x.view().inject(this);
		setColumnText("个人中心");
		headImageManager = new HeadImageManager();
		initPersonalInfo();
		initData();
		AppManager.getInstance().removeNotActivity(this);
	}

	private void initPersonalInfo() {
		ViewUtils.setVisibility(View.VISIBLE, rightTV);
		ViewUtils.setOnClickListener(rightTV, this);
		ViewUtils.setTextData(rightTV, "退出");
		if (StarApplication.userInfo != null) {
			if (OtherUtils.isNotEmpty(StarApplication.userInfo.getString("NickName"))) {
				ViewUtils.setTextManyColor(nickNameTV, "昵称:", StarApplication.userInfo.getString("NickName"), R.color.black);
			} else {
				ViewUtils.setTextManyColor(nickNameTV, "姓名:", StarApplication.userInfo.getString("UserName"), R.color.black);
			}
			ViewUtils.setTextManyColor(balanceTV, "余额:", StarApplication.userInfo.getString("Account"), R.color.black);
			ViewUtils.setTextManyColor(integralTV, "积分:", StarApplication.userInfo.getString("Point"), R.color.black);
			ViewUtils.setTextManyColor(bianmaTV, "家庭编码:", StarApplication.userInfo.getString("FamilyID"), R.color.black);
			if (OtherUtils.isNotEmpty(StarApplication.userInfo.getString("Membership"))) {
				int Membership = StarApplication.userInfo.getIntValue("Membership");
				ViewUtils.setTextManyColor(huiyuanTV, "普通会员:", StewardUtils.getStarGrade(Membership), R.color.black);
			}
			wanshanInfoTV.setOnClickListener(this);
			headImage.setOnClickListener(this);
			if (OtherUtils.isNotEmpty(StarApplication.userInfo.getString("FileFolder"))) {
				x.image().bind(headImage, GlobalUrl.ADDR + StarApplication.userInfo.getString("FileFolder"));
			}
		}
	}

	private void initData() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(R.drawable.personal_center_my_jinlin_icon);
		list.add(R.drawable.personal_center_my_dingdan_icon);
		list.add(R.drawable.personal_center_jiankang_icon);
		list.add(R.drawable.personal_center_my_fabu_icon);
		list.add(R.drawable.personal_center_my_huodong_icon);
		list.add(R.drawable.personal_center_my_shoucang_icon);
		list.add(R.drawable.personal_center_zhanghu_info_icon);
		list.add(R.drawable.personal_center_my_guanjia_icon);
		list.add(R.drawable.personal_center_my_kefu_icon);
		list.add(R.drawable.personal_center_my_xingcheng_icon);
		list.add(R.drawable.personal_center_my_heimingdan_icon);
		list.add(R.drawable.personal_center_my_fu_kuan);
		gridview.setAdapter(new PersonalCenterAdapter(getActivity(), list));
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Class<?> mClass = null;
				switch (arg2) {
					case 0:
						 mClass = MyNeighbourActivity.class;
						break;
					case 1:
						mClass = MyOrderActivity.class;
						break;
					case 2:
						Bundle ex=new Bundle();
						ex.putInt("type",2);
						IntentUtils.goTo(getActivity(),MainActivity.class,ex);
						finish();
						return;
					case 3:
						mClass = MyReleaseOrderActivity.class;
						break;
					case 4:
						mClass = MyActivityOrderActivity.class;
						break;
					case 5:
						mClass = MyCollectionListActivity.class;
						break;
					case 6:
						mClass = UserInfoActivity.class;
						break;
					case 7:
						mClass = MyHousekeeperActivity.class;
						break;
					case 8:
						mClass = MyKeFuActivity.class;
						break;
					case 9:
						mClass = MyTravelOrderActivity.class;
						break;
					case 10:
						mClass = MyBlackListActivity.class;
						break;
					case 11:
						mClass=MyFuKuanOrderActivity.class;
						break;
				}
				if (mClass != null) {
					IntentUtils.goTo(getActivity(), mClass);
				}
			}
		});
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap bm = headImageManager.getActivityResult(requestCode, resultCode, RESULT_OK, getActivity(), data);
		if (bm != null) {
			bm = BitmapChange.getCroppedRoundBitmap(bm, 120);
			headImage.setImageBitmap(bm);
			final File file = headImageManager.saveBitmap(bm);
			HttpSendMananger.sendPostFile(GlobalUrl.SEND_MY_HEAD_IMAGE_URL, new BaseSimpleHttpCallback(this, true, false) {
				@Override
				public void onSuccess(JSONObject data) {
					StarApplication.userInfo = data;
					if (file != null && file.exists()) {
						file.delete();
					}
					ToastUtil.showInfo(getActivity(), "头像上传成功");
				}

			}, "FileFolder", file, "UserID", StarApplication.userInfo.getString("UserID"));
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.personal_make_money_master_item_icon:
				headImageManager.doPickPhotoAction(getActivity(), "拍照", "从相册中选择", "更改头像");
				break;
			case R.id.personal_wanshan_info_tv:
				break;
			case R.id.banner_Base_activity_head_right_text_view:
				DialogUtils.showInfoDialog(getActivity(), new OnClickListener() {

					@Override
					public void onClick(View v) {
						StarApplication.userInfo = null;
						SharedPreferencesUtils.setStringDate("loginName", "");
						SharedPreferencesUtils.setStringDate("password", "");
						IntentUtils.goTo(getActivity(), LoginActivity.class);
						finish();
					}
				}, "再次确认是否退出", null, "退出", "取消", false);
				break;
		}

	}
}
