package com.sevenstar.steward2016.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.manager.AppManager;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.callback.PayCallBack;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.utils.PayUtils;
import com.sevenstar.steward2016.view.VideoView;

public class PrivateVideoContentActivity extends BannerBaseActivity implements OnClickListener {
	@ViewInject(R.id.banner_Base_activity_head_right_text_view)
	TextView rightTV;
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
	@ViewInject(R.id.activity_image_iv)
	ImageView imageIV;
	private JSONObject jsonObj;
	@ViewInject(R.id.videoview)
	VideoView videoview;
	private Uri mUri;
	private int mPositionWhenPaused;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContextView(R.layout.activity_private_video_content_page);
		setColumnText("私人视频");
		x.view().inject(this);
		ViewUtils.setVisibility(View.VISIBLE, rightTV);
		ViewUtils.setTextData(rightTV, "立即下单");
		ViewUtils.setOnClickListener(rightTV, this);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		Bundle extras = IntentUtils.getIntentBundle(this);
		if (extras != null) {
			String json = extras.getString("data");
			if (OtherUtils.isNotEmpty(json)) {
				jsonObj = JSONObject.parseObject(json);
				if (jsonObj != null) {
					String url = jsonObj.getString("FileFolder");
					if (OtherUtils.isNotEmpty(url)) {
						mUri = Uri.parse(GlobalUrl.ADDR + url);
						initVideoView();
					}
					ViewUtils.setTextData(textOne, jsonObj.getString("TypeName"));
					ViewUtils.setTextData(textTwo, jsonObj.getString("Price") + "元");
					ViewUtils.setTextData(textThree, jsonObj.getString("Introduction"));
					ViewUtils.setTextData(textFour, jsonObj.getString("Company"));
					ViewUtils.setTextData(textFive, jsonObj.getString("Notice"));
					x.image().bind(imageIV, GlobalUrl.ADDR + jsonObj.getString("Picture"));
				}
			}
		}
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void onClick(View v) {
		if (StarApplication.userInfo == null) {
			ToastUtil.showInfo(getActivity(), "您未登录,不能进行此操作");
			return;
		}
		DialogUtils.showInfoDialog(getActivity(), new OnClickListener() {

			@Override
			public void onClick(View v) {

				JSONObject tempData = new JSONObject();
				tempData.put("Price",jsonObj.getString("Price"));
				tempData.put("Name",jsonObj.getString("TypeName"));
				tempData.put("Content",jsonObj.getString("Introduction"));
				tempData.put("OrderID",jsonObj.getString("VideoServiceID"));
				PayUtils.pay(GlobalUrl.getPayOrderInfo, getActivity(), tempData, new PayCallBack() {
					@Override
					public void onSuccess() {

						HttpSendMananger.sendPost(GlobalUrl.INSTALL_Live_ORDER_URL, new BaseSimpleHttpCallback(getActivity(), true, true) {
							@Override
							public void onSuccess(JSONObject data) {

							}

							@Override
							public OnClickListener getDialogMessageListener() {
								// TODO Auto-generated method stub
								return new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										if (state == 0) {
											AppManager.getInstance().removeAllActivity();
										}
									}
								};
							}
						}, "serviceType", "5", "UserID", StarApplication.userInfo.getString("UserID"), "MyID", jsonObj.getString("VideoServiceID"));
					}

					@Override
					public void onFailure() {

					}
				});


			}}, "确认是否下此订单", null, null, null, false);
	}

	public void initVideoView() {
		MediaController controller = new MediaController(this, true);
		videoview.setMediaController(controller);
		controller.setMediaPlayer(videoview);
		videoview.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.start();
			}
		});
		//
		videoview.setVideoURI(mUri);
		videoview.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				videoview.start();
			}
		});

		videoview.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.start();
			}
		});

		videoview.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				return true;
			}
		});
	}

	public void onPause() {
		// Stop video when the activity is pause.
		if (videoview != null) {
			mPositionWhenPaused = videoview.getCurrentPosition();
			videoview.stopPlayback();
		}
		super.onPause();
	}

	public void onResume() {
		// Resume video player
		if (mPositionWhenPaused >= 0) {
			videoview.seekTo(mPositionWhenPaused);
			mPositionWhenPaused = -1;
		}
		super.onResume();
	}

	public boolean onError(MediaPlayer player, int arg1, int arg2) {
		return false;
	}

	public void onCompletion(MediaPlayer mp) {
		this.finish();
	}

}
