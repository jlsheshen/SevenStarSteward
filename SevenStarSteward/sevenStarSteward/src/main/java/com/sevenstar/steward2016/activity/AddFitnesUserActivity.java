package com.sevenstar.steward2016.activity;

import java.io.File;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.activity.BannerBaseActivity;
import com.lib.shiguotao.utils.BitmapChange;
import com.lib.shiguotao.utils.HeadImageManager;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ToastUtil;
import com.lib.shiguotao.utils.ViewUtils;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.StarApplication;
import com.sevenstar.steward2016.adapter.SingleDataAdapter;
import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;
import com.sevenstar.steward2016.constant.GlobalUrl;
import com.sevenstar.steward2016.manager.HttpSendMananger;

/**
 * ��� ����û�
 */
public class AddFitnesUserActivity extends BannerBaseActivity implements OnClickListener, OnCheckedChangeListener {
	@ViewInject(R.id.banner_Base_activity_head_right_text_view)
	TextView rightTV;
	@ViewInject(R.id.relationship)
	TextView relationshipTV;
	@ViewInject(R.id.name)
	EditText nameTV;
	@ViewInject(R.id.age)
	EditText ageTV;
	@ViewInject(R.id.sex_radio)
	RadioGroup sexRG;
	@ViewInject(R.id.headimage)
	ImageView headIV;
	private HeadImageManager headImageManager;
	private File headBitmapFile;
	private int sexIndex = -1;
	private int selectRelationshipIndex = -1;
	private List<JSONObject> relationshipData = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setColumnText("���");
		setBaseContextView(R.layout.activity_add_fitness_page);
		x.view().inject(this);
		ViewUtils.setVisibility(View.VISIBLE, rightTV);
		ViewUtils.setTextData(rightTV, "���");
		ViewUtils.setOnClickListener(rightTV, this);
		ViewUtils.setOnClickListener(headIV, this);
		ViewUtils.setOnClickListener(relationshipTV, this);
		sexRG.setOnCheckedChangeListener(this);
		HttpSendMananger.sendPost(GlobalUrl.getRelationsListDataData, new BaseSimpleHttpCallback(getActivity(), false, false) {
			@Override
			public void onSuccess(JSONObject data) {
				relationshipData = JSONObject.parseArray(data.getString("data"), JSONObject.class);
			}
		});
		headImageManager = new HeadImageManager();
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
			headIV.setImageBitmap(bm);
			headBitmapFile = headImageManager.saveBitmap(bm);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.headimage:
			headImageManager.doPickPhotoAction(getActivity(), "����", "�������ѡ��", "�ϴ�ͷ��");
			break;
		case R.id.relationship:
			if (relationshipData != null && relationshipData.size() > 0) {
				Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("��ѡ��ѪԵ��ϵ");
				final SingleDataAdapter adapter = new SingleDataAdapter(getActivity(), relationshipData, "FamilyID");
				builder.setSingleChoiceItems(adapter, selectRelationshipIndex, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						selectRelationshipIndex = which;
					}
				});

				builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int which) {
						if (selectRelationshipIndex != which) {
							ViewUtils.setTextData(relationshipTV, adapter.getItem(selectRelationshipIndex).getString("FamilyID"));
						}
					}
				});
				builder.create().show();
			} else {
				ToastUtil.showInfo(getActivity(), "�����쳣");
			}
			break;
		case R.id.banner_Base_activity_head_right_text_view:
			if (OtherUtils.isEmpty(nameTV.getText())) {
				ToastUtil.showInfo(getActivity(), "����������");
				return;
			}
			if (OtherUtils.isEmpty(ageTV.getText())) {
				ToastUtil.showInfo(getActivity(), "����������");
				return;
			}
			if (sexIndex == -1) {
				ToastUtil.showInfo(getActivity(), "�빴ѡ�Ա�");
				return;
			}
			if (selectRelationshipIndex == -1) {
				ToastUtil.showInfo(getActivity(), "��ѡ���ϵ");
				return;
			}
			if (headBitmapFile == null) {
				ToastUtil.showInfo(getActivity(), "�����ͷ��");
				return;
			}
			HttpSendMananger.sendPostFile(GlobalUrl.addHealthUser, new BaseSimpleHttpCallback(getActivity(), true, true) {
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
								finish();
							}
						}
					};
				}
			}, "FileFolder", headBitmapFile, "UserID", StarApplication.userInfo.getString("UserID"), "FamilyID", StarApplication.userInfo
					.getString("FamilyID"), "RelationsID", relationshipData.get(selectRelationshipIndex).getString("RelationsID"),"UserName", nameTV.getText().toString(),"Sex",
					String.valueOf(sexIndex), "Older", ageTV.getText().toString());
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.sex_nan_button:
			sexIndex = 0;
			break;
		case R.id.sex_nv_button:
			sexIndex = 1;
			break;
		}
	}

}
