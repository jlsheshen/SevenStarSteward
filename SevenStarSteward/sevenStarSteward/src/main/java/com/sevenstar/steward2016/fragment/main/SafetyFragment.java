package com.sevenstar.steward2016.fragment.main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.fragment.BaseFragment;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.SafetyMainAdapter;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.utils.AdUtils;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZOpenSDK;

/**
 * MAIN 安全
 */
public class SafetyFragment extends BaseFragment {

	@ViewInject(R.id.gridview)
	GridView gridview;
	@ViewInject(R.id.safety_head_imageView)
	ImageView headImageView;
	@ViewInject(R.id.safety_head_layout)
	RelativeLayout headLayout;

	@ViewInject(R.id.hongwai) ImageView hongwaiIV;
	@ViewInject(R.id.menci) ImageView menciIV;
	@ViewInject(R.id.yangan) ImageView yanganIV;
	String Serial=null;


	@Override
	protected void init() {
		// TODO Auto-generated method stub
		x.view().inject(this, layout);

	}

	@Override
	protected void initData() {

		if (Serial == null){
			getSerizal();
		}
		List<Integer> list = new ArrayList<Integer>();
		list.add(R.drawable.zai_jia_safety_icon);
		list.add(R.drawable.shui_mian_safety_icon);
		list.add(R.drawable.li_jia_safety_icon);
		list.add(R.drawable.jing_bao_safety_icon);
		list.add(R.drawable.jie_chu_jing_bao_safety_icon);
		list.add(R.drawable.shi_ping_safety_icon);
		gridview.setAdapter(new SafetyMainAdapter(getActivity(), list));
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				boolean[] isShows=new boolean[3];
				switch (position){
					case 0:
						isShows[2]=true;
						isKaiGuan(isShows);

						doSecurity(0);
						break;
					case 1:
						isShows[1]=true;
						isShows[2]=true;
						isKaiGuan(isShows);


						doSecurity(8);
						break;
					case 2:
						isShows[0]=true;
						isShows[1]=true;
						isShows[2]=true;
						isKaiGuan(isShows);

						doSecurity(16);
						break;
				}
			}
		});



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
	void doSecurity(final int b){
		new AsyncTask<Void,Void,Void>(){

			@Override
			protected Void doInBackground(Void... params) {

				Log.d("SafetyFragment", "出现点击事件");
				/**
				 * 布防状态, IPC布防状态只有0和1，
				 A1有0:睡眠 8:在家 16:外出
				 */
				try {
					EZOpenSDK.getInstance().setDeviceDefence(Serial,b);
					Log.d("SafetyFragment", "成功--" + b + EZOpenSDK.getInstance().getDeviceInfoBySerial(Serial));
				} catch (BaseException e) {
					Log.d("SafetyFragment", "失败--" + b + e.toString());
				}
				return null;
			}
		}.execute();

	}


	public void isKaiGuan(boolean[] isShows){
		if(isShows!=null){
			ImageView[] imageViews=new ImageView[]{hongwaiIV,menciIV,yanganIV};
			for(int i=0;i<isShows.length;i++){
				if(isShows[i]){
					imageViews[i].setImageResource(R.drawable.safety_kai_icon);
				}else{
					imageViews[i].setImageResource(R.drawable.safety_guan_icon);
				}
			}
		}
	}

	@Override
	protected int getLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.fragment_main_safety_pager;
	}

	public void getSerizal() {
		HttpSendMananger.sendPost("http://139.196.58.106/Service/getMonitorSNRo?UserID=14",
				new BaseSimpleHttpCallback(getActivity(),false,false){
					@Override
					public void onSuccess(String data) {
						super.onSuccess(data);
						Pattern p=Pattern.compile("SRNo\":\"(\\d*)");
						Matcher m=p.matcher(data);

						if(m.find()){
							String serial=m.group(1);
							Serial=serial;
							Log.d("SafetyFragment","Serial" + Serial);
						}

					}
				});



	}
}
