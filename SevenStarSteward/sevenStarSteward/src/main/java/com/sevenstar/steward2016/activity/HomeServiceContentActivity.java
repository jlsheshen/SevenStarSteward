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
import android.widget.MediaController;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.manager.AppManager;
import com.lib.shiguotao.utils.DialogUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.view.VideoView;
/**
 * 服务套餐详情界面
 */
public class HomeServiceContentActivity extends BannerBaseActivity implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    @ViewInject(R.id.videoview)
    VideoView videoview;
    @ViewInject(R.id.home_service_content_one_tv)
    TextView oneTV;
    @ViewInject(R.id.home_service_content_two_tv)
    TextView twoTV;
    @ViewInject(R.id.home_service_content_three_tv)
    TextView threeTV;
    @ViewInject(R.id.home_service_content_four_tv)
    TextView fourTV;
    @ViewInject(R.id.banner_Base_activity_head_right_text_view)
    TextView rightTextView;
    private Uri mUri;
    private int mPositionWhenPaused;
    private int cuIndex = 0;
    private JSONObject json = null;
    private String[] urls = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setBaseContextView(R.layout.activity_home_service_content_layout);
        x.view().inject(this);
        initData();
        if (mUri != null) {
            initVideoView();
        }
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            json = JSONObject.parseObject(extras.getString("json"));
            String url = json.getString("FileFolder");
            if (OtherUtils.isNotEmpty(url)) {
                urls = url.split(",");
                mUri = Uri.parse(GlobalUrl.ADDR + urls[0]);
                initVideoView();
            }
            setColumnText(json.getString("TypeName"));
            ViewUtils.setTextData(oneTV, json.getString("Service"));
            ViewUtils.setTextData(twoTV, json.getString("Notes"));
            ViewUtils.setTextData(threeTV, json.getString("TypeName"));
            ViewUtils.setTextData(fourTV, json.getString("Price") + "元");
            ViewUtils.setTextData(rightTextView, "立即预约");
            ViewUtils.setVisibility(View.VISIBLE, rightTextView);
            rightTextView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (StarApplication.userInfo == null) {
                        ToastUtil.showInfo(getActivity(), "您未登录,不能进行此操作");
                        return;
                    }
                    DialogUtils.showInfoDialog(getActivity(), new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            HttpSendMananger.sendPost(GlobalUrl.INSTALL_Service_ORDER_URL, new BaseSimpleHttpCallback(getActivity(), true,
                                    true) {
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
                            }, "TypeID", json.getString("TypeID"), "UserID", StarApplication.userInfo.getString("UserID"));
                        }
                    }, "确认是否下此订单", null, null, null, false);

                }
            });
        } else {
            finish();
        }
    }

    public void onStart() {
        super.onStart();
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
                cuIndex++;
                if (cuIndex >= urls.length) {
                    cuIndex = 0;
                }
                mUri = Uri.parse(GlobalUrl.ADDR + urls[cuIndex]);
                videoview.setVideoURI(mUri);
                mp.start();
            }
        });

        videoview.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // ToastUtil.showInfo(getBaseContext(), "文件不错在");
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

    @Override
    public Activity getActivity() {
        return this;
    }

}
