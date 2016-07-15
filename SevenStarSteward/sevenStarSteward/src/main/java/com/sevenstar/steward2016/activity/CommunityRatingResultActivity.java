package com.sevenstar.steward2016.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.LogUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.constant.GlobalUrl;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
/**
 * 社区评比结果详情界面
 */
public class CommunityRatingResultActivity extends BannerBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.head_image_one)
    ImageView headOneIV;
    @ViewInject(R.id.head_image_two)
    ImageView headTwoIV;
    @ViewInject(R.id.head_image_three)
    ImageView headThreeIV;
    @ViewInject(R.id.content_tv_one)
    TextView oneTV;
    @ViewInject(R.id.content_tv_two)
    TextView twoTV;
    @ViewInject(R.id.content_tv_three)
    TextView threeTV;

    private JSONObject jsonObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContextView(R.layout.activity_community_rating_result_page);
        setColumnText("社区评比");
        x.view().inject(this);
        initData();
    }

    private void initData() {
        Bundle bundle = IntentUtils.getIntentBundle(this);
        if (bundle != null) {
            try {
                String json = bundle.getString("json");
                jsonObj = JSONObject.parseObject(json);
                ViewUtils.setTextData(oneTV, jsonObj.getString("FirstName"));
                ViewUtils.setTextData(twoTV, jsonObj.getString("SecondName"));
                ViewUtils.setTextData(threeTV, jsonObj.getString("ThirdName"));
                x.image().bind(headOneIV, GlobalUrl.ADDR + jsonObj.getString("FirstPicture"));
                x.image().bind(headTwoIV, GlobalUrl.ADDR + jsonObj.getString("SecondPicture"));
                x.image().bind(headThreeIV, GlobalUrl.ADDR + jsonObj.getString("ThirdPicture"));
            } catch (Exception e) {
                LogUtils.showErrorMessage(e);
            }
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onClick(View v) {

    }
}
