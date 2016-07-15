package com.sevenstar.steward2016.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSONObject;
import com.sevenstar.steward2016.adapter.SingleDataAdapter;

import java.util.List;

/**
 * �Զ����ѡ��
 */
public class MultiChoiceDialog {
    private boolean[] checkItems;
    private List<JSONObject> listData;
    private String[] items;
    private String itemKey;
    private String title;
    private String checkKey;
    private DialogInterface.OnClickListener positiveListener;
    private DialogInterface.OnClickListener negativeListener;

    public MultiChoiceDialog(String title,String itemKey,String checkKey){
        this.title=title;
        this.checkKey=checkKey;
        this.itemKey=itemKey;
    }

    public MultiChoiceDialog setListener(DialogInterface.OnClickListener positiveListener,DialogInterface.OnClickListener negativeListener){

        this.positiveListener=positiveListener;
        this.negativeListener=negativeListener;
        return this;
    }

    public void showDialog(Activity activity,final List<JSONObject> listData){
        if(listData!=null&&listData.size()>0){
            checkItems=new boolean[listData.size()];
            items=new String[listData.size()];
            int count=0;
            for (JSONObject obj:listData){
                checkItems[count]=obj.getBooleanValue(checkKey);
                items[count]=obj.getString(itemKey);
                count++;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(title);
            final SingleDataAdapter adapter = new SingleDataAdapter(activity, listData,itemKey);
            builder.setMultiChoiceItems(items, checkItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    checkItems[which]=isChecked;
                    listData.get(which).put(checkKey,isChecked);
                }
            });
            builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(positiveListener!=null){
                        positiveListener.onClick(dialog,which);
                    }
                    if(dialog!=null){
                        dialog.dismiss();
                        dialog=null;
                    }
                }
            });
            builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(negativeListener!=null){
                        negativeListener.onClick(dialog,which);
                    }
                    if(dialog!=null){
                        dialog.dismiss();
                        dialog=null;
                    }
                }
            });
            builder.create().show();
        }
    }

}
