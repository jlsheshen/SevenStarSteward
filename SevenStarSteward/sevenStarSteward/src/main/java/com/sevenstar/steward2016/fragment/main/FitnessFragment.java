package com.sevenstar.steward2016.fragment.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.fragment.BaseFragment;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.activity.AddFitnesUserActivity;
import com.sevenstar.steward2016.activity.HealthyUserRecordActivity;
import com.sevenstar.steward2016.activity.MainActivity;
import com.sevenstar.steward2016.adapter.FitnessFamilyMembersAdapter;
import com.sevenstar.steward2016.adapter.FitnessHeadDataAdapter;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.utils.MultiChoiceDialog;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * MAIN 健康
 */
public class FitnessFragment extends BaseFragment {

	@ViewInject(R.id.gridview)
	GridView gridview;
	@ViewInject(R.id.listview)
	ListView listview;
	private String[] title = new String[] { "体检日期", "体检时间", "高血压", "低血压", "血氧", "血糖", "血脂", "脉搏", "体温", "体重", "身高",
			"左视力", "右视力", "脂肪率", "脂肪等级", "肌肉量", "水分", "异常情况", "医学指导", "特殊说明" };
	private String[] attribute = new String[] { "Date", "Time", "HighPressure", "LowPressure", "Oxygen", "BloodGlucose",
			"BloodLipid", "Pulse", "Temperature", "Weight", "Height", "LeftVision", "RightVision", "FatRate",
			"FatClass", "Muscle", "Water", "Abnormal", "DoctorComment", "SpecialComment" };
	private FitnessFamilyMembersAdapter fitnessFamilyMembersAdapter;
	private FitnessHeadDataAdapter fitnessHeadDataAdapter;
	private View headLayout;
	private List<JSONObject> listData;

	@Override
	protected void init() {
		x.view().inject(this, layout);
		fitnessFamilyMembersAdapter = new FitnessFamilyMembersAdapter(getActivity(), null);
		gridview.setAdapter(fitnessFamilyMembersAdapter);
		fitnessHeadDataAdapter = new FitnessHeadDataAdapter(getActivity(), null);
		headLayout = LayoutInflater.from(getActivity()).inflate(R.layout.fitness_head_item_layout, null);
		listview.addHeaderView(headLayout);
		listview.setVisibility(View.INVISIBLE);
		listview.setAdapter(fitnessHeadDataAdapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (StarApplication.userInfo == null) {
					ToastUtil.showInfo(getActivity(), "您未登录,不能进行此操作");
					return;
				}
				JSONObject entity = fitnessFamilyMembersAdapter.getItem(arg2);
				switch (entity.getIntValue("type")) {
				case 1:
					IntentUtils.goTo(getActivity(), AddFitnesUserActivity.class);
					break;
				case -1:
					new MultiChoiceDialog("删除家庭成员","RelationsName","Check").setListener(new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							StringBuilder sb=new StringBuilder();
							for (int i = 0; i <listData.size() ; i++) {
								boolean isCheck=listData.get(i).getBooleanValue("Check");
								if(isCheck){
									sb.append(listData.get(i).getString("HealthUserID")).append(",");
								}
							}
							if(sb.length()>1){
								HttpSendMananger.sendPost(GlobalUrl.removeHealthUser,
										new BaseSimpleHttpCallback(getActivity(), true, true){
											@Override
											public void onSuccess(JSONObject data) {
												refreshData();
											}
										},"UserID", StarApplication.userInfo.getString("UserID"),"IDS",sb.substring(0,sb.length()-1));
							}
						}
					},null).showDialog(getActivity(),listData);
					break;
				default:
					Bundle ex=new Bundle();
					ex.putString("json",entity.toJSONString());
					IntentUtils.goTo(getActivity(),HealthyUserRecordActivity.class,ex);
					break;
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		refreshData();
	}

	private void refreshData() {
		String userID = StarApplication.userInfo == null ? "-1" : StarApplication.userInfo.getString("UserID");
		HttpSendMananger.sendPost(GlobalUrl.getFitnessHomeData,
				new BaseSimpleHttpCallback(getActivity(), fitnessHeadDataAdapter.getCount() <= 0, false) {
					@Override
					public void onSuccess(JSONObject data) {
						initHeadData(data.getJSONObject("headData"));
						listData = JSONObject.parseArray(data.getString("familyMembersData"), JSONObject.class);
						initFamilyMembersData(
								JSONObject.parseArray(data.getString("familyMembersData"), JSONObject.class));
					}

					@Override
					public void onError(Throwable throwable, boolean flag) {
						super.onError(throwable, flag);
						initHeadData(new JSONObject());
						initFamilyMembersData(null);
					}

				}, "UserID", userID,"isShowMyFitness", MainActivity.isShowMyFitness?"1":"0");
	}

	private void initFamilyMembersData(List<JSONObject> familyMembersListData) {
		if (familyMembersListData == null) {
			familyMembersListData = new ArrayList<JSONObject>();
		}
		familyMembersListData.add(getType(1));
		if (familyMembersListData.size() > 1) {
			familyMembersListData.add(getType(-1));
		}
		fitnessFamilyMembersAdapter.setList(familyMembersListData);
		fitnessFamilyMembersAdapter.notifyDataSetChanged();
	}

	private JSONObject getType(int type) {
		JSONObject json = new JSONObject();
		json.put("type", type);
		return json;
	}

	private void initHeadData(JSONObject jsonObje) {
		List<JSONObject> listArray = new ArrayList<JSONObject>();
		for (int i = 0; i < title.length; i++) {
			JSONObject json = new JSONObject();
			json.put("title", title[i]);
			json.put("value", jsonObje.getString(attribute[i]));
			listArray.add(json);
		}
		listview.setVisibility(View.VISIBLE);
		ImageView headImage = ViewUtils.find(headLayout, R.id.imageview);
		x.image().bind(headImage, GlobalUrl.ADDR + jsonObje.getString("FileFolder"));
		TextView headTitle = ViewUtils.find(headLayout, R.id.title);
		ViewUtils.setTextData(headTitle, jsonObje.getString("RelationsName"));
		headLayout.invalidate();
		fitnessHeadDataAdapter.setList(listArray);
		fitnessHeadDataAdapter.notifyDataSetChanged();
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.fragment_main_fitness_pager;
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

}
