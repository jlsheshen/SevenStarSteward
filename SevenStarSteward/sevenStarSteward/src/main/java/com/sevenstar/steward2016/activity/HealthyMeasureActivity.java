
package com.sevenstar.steward2016.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.adapter.FitnessHeadDataAdapter;
import com.sevenstar.steward2016.adapter.SingleDataAdapter;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.utils.MultiChoiceDialog;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
/**
 * ����������ݽ���
 */
public class HealthyMeasureActivity extends BannerBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.xingming)
    TextView xingMingTV;
    @ViewInject(R.id.xingbie)
    TextView xingBieTV;
    @ViewInject(R.id.nianling)
    TextView nianLingTV;
    @ViewInject(R.id.head_iv)
    ImageView headIV;
    @ViewInject(R.id.headTitle) TextView headTitleTV;

    @ViewInject(R.id.listview)
    ListView listview;

    private String HealthUserID;
    private String[] title = new String[]{"�������", "���ʱ��", "��Ѫѹ", "��Ѫѹ", "Ѫ��", "Ѫ��", "Ѫ֬", "����", "����", "����", "���",
            "������", "������", "֬����", "֬���ȼ�", "������", "ˮ��", "�쳣���", "ҽѧָ��", "����˵��"};
    private String[] attribute = new String[]{"Date", "Time", "HighPressure", "LowPressure", "Oxygen", "BloodGlucose",
            "BloodLipid", "Pulse", "Temperature", "Weight", "Height", "LeftVision", "RightVision", "FatRate",
            "FatClass", "Muscle", "Water", "Abnormal", "DoctorComment", "SpecialComment"};
    private FitnessHeadDataAdapter fitnessHeadDataAdapter;
    private String titleName;
    private List<JSONObject> listData;
    private int nowSelectDateIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContextView(R.layout.activity_healthy_measure_layout);
        x.view().inject(this);
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
                        if("��ʷ".equals(titleName)){
                            listData= JSONArray.parseArray( data.getString("listData"),JSONObject.class);
                            if(listData!=null&&listData.size()>0){
                                initHeadData(listData.get(0));
                            }else{
                                initHeadData(new JSONObject());
                            }
                        }else{
                            initHeadData(data);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable, boolean flag) {
                        super.onError(throwable, flag);
                        initHeadData(new JSONObject());
                    }

                }, "HealthUserID", HealthUserID,"type","��ʷ".equals(titleName)?"1":"0");

    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             titleName = extras.getString("title");
            setColumnText(titleName);
            if ("��ʷ".equals(titleName)) {
                headTitleTV.setText("��������");
                headTitleTV.setOnClickListener(this);
            }
            String json = extras.getString("json");
            JSONObject jsonObj = JSONObject.parseObject(json);
            HealthUserID = jsonObj.getString("HealthUserID");
            ViewUtils.setTextData(xingMingTV, jsonObj.getString("UserName"));
            ViewUtils.setTextData(xingBieTV, jsonObj.getIntValue("Sex") == 1 ? "Ů" : "��");
            ViewUtils.setTextData(nianLingTV, jsonObj.getString("Older"));
            x.image().bind(headIV, GlobalUrl.ADDR + jsonObj.getString("FileFolder"));
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

        if ("��ʷ".equals(titleName)) {
            String date=jsonObje.getString("Date");
            if(OtherUtils.isNotEmpty(date)){
                headTitleTV.setText("��������\t:\t"+jsonObje.getString("Date"));
            }
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
        if (listData != null && listData.size() > 1) {
            final SingleDataAdapter adapter = new SingleDataAdapter(getActivity(), listData, "Date");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("ѡ���������");
            builder.setSingleChoiceItems(adapter, nowSelectDateIndex, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    nowSelectDateIndex = which;
                }
            });

            builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    if (nowSelectDateIndex != which) {
                        initHeadData(listData.get(nowSelectDateIndex));
                    }
                }
            });
            builder.create().show();
        }
    }
}
