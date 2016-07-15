
package com.sevenstar.steward2016.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.IntentUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.adapter.FitnessHeadDataAdapter;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
/**
 * 体检详情记录界面
 */
public class HealthyUserRecordActivity extends BannerBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.kaishiceliang) TextView kaishiceliangTV;
    @ViewInject(R.id.lishishuju) TextView lishishujuTV;
    @ViewInject(R.id.listview)
    ListView listview;

    private String HealthUserID;
    private String[] title = new String[] { "体检日期", "体检时间", "高血压", "低血压", "血氧", "血糖", "血脂", "脉搏", "体温", "体重", "身高",
            "左视力", "右视力", "脂肪率", "脂肪等级", "肌肉量", "水分", "异常情况", "医学指导", "特殊说明" };
    private String[] attribute = new String[] { "Date", "Time", "HighPressure", "LowPressure", "Oxygen", "BloodGlucose",
            "BloodLipid", "Pulse", "Temperature", "Weight", "Height", "LeftVision", "RightVision", "FatRate",
            "FatClass", "Muscle", "Water", "Abnormal", "DoctorComment", "SpecialComment" };

    private FitnessHeadDataAdapter fitnessHeadDataAdapter;
    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContextView(R.layout.activity_healthy_user_record_layout);
        x.view().inject(this);
        kaishiceliangTV.setOnClickListener(this);
        lishishujuTV.setOnClickListener(this);
        fitnessHeadDataAdapter = new FitnessHeadDataAdapter(this, null);
        listview.setAdapter(fitnessHeadDataAdapter);
        initData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        HttpSendMananger.sendPost(GlobalUrl.getHealthRecordData,
                new BaseSimpleHttpCallback(getActivity(), fitnessHeadDataAdapter.getCount() <= 0, false) {
                    @Override
                    public void onSuccess(JSONObject data) {
                        initHeadData(data);
                    }
                    @Override
                    public void onError(Throwable throwable, boolean flag) {
                        super.onError(throwable, flag);
                        initHeadData(new JSONObject());
                    }

                }, "HealthUserID", HealthUserID);

    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
          json=  extras.getString("json");
           JSONObject jsonObj= JSONObject.parseObject(json);
            HealthUserID=jsonObj.getString("HealthUserID");
            setColumnText(jsonObj.getString("RelationsName"));
        }
    }


    private void initHeadData(JSONObject jsonObje) {
        List<JSONObject> listArray = new ArrayList<JSONObject>();
        for (int i = 0; i < title.length; i++) {
            JSONObject json = new JSONObject();
            json.put("title", title[i]);
            json.put("value", jsonObje.getString(attribute[i]));
            listArray.add(json);
        }
        fitnessHeadDataAdapter.setList(listArray);
        fitnessHeadDataAdapter.notifyDataSetChanged();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onClick(View v) {
        Class<?> mClass=null;
        String title="";
        switch (v.getId()){
            case R.id.kaishiceliang:
                title="测量";
                mClass=HealthyMeasureActivity.class;
                break;
            case R.id.lishishuju:
                    title="历史";
                    mClass=HealthyMeasureActivity.class;
                break;
        }
       if(mClass!=null){
           Bundle ex=new Bundle();
           ex.putString("json",json);
           ex.putString("title",title);
           IntentUtils.goTo(this,mClass,ex);
       }
    }
}
