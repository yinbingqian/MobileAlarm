package com.lnpdit.mobilealarm.page.adapter;

import java.util.List;

import com.lnpdit.mobilealarm.R;
import com.lnpdit.mobilealarm.entity.AlarmList;
import com.lnpdit.mobilealarm.entity.TransforDate;
import com.lnpdit.mobilealarm.page.activity.alarmnote.AlarmInformationActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class AlarmListAdapter extends BaseAdapter {
    private class buttonViewHolder {
        TextView type_tv;
        TextView time_tv;
    }

    private List<TransforDate> mAppList;
    private LayoutInflater mInflater;
    private Context mContext;
    private buttonViewHolder holder;

    public AlarmListAdapter(Context c, List<TransforDate> appList) {
        mAppList = appList;
        mContext = c;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int positon) {
        mAppList.remove(positon);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            holder = (buttonViewHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.list_in_alarmlist, null);
            holder = new buttonViewHolder();
            holder.time_tv = (TextView) convertView.findViewById(R.id.time_tv);
            holder.type_tv = (TextView) convertView.findViewById(R.id.type_tv);
            convertView.setTag(holder);
        }
        TransforDate appInfo = mAppList.get(position);
        if (appInfo != null) {
            String type_str = appInfo.getTagName();
            String time = appInfo.getcTime();
            try{              
                holder.type_tv.setText("事件类型："+type_str);
                holder.time_tv.setText("时间："+time);
                convertView.setOnClickListener(new AdapterListener(position, appInfo));
            }catch(Exception e){
                
            }
        }
        return convertView;
    }

    class AdapterListener implements OnClickListener{
        private int position;
        private TransforDate transfordate;

        public AdapterListener(int pos, TransforDate td) {
            // TODO Auto-generated constructor stub
            position = pos;
            transfordate = td;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            String MobileNumber = transfordate.getMobileNumber();
            String TagName = transfordate.getTagName();
            String CoordinateX = transfordate.getCoordinateX();
            String CoordinateY = transfordate.getCoordinateY();
            String Adress = transfordate.getAdress();
            String VideoName = transfordate.getVideoName();
            String AudioName = transfordate.getAudioName();
            String TextContents = transfordate.getTextContents();
            String Pic1 = transfordate.getPic1();
            String Pic2 = transfordate.getPic2();
            String Pic3 = transfordate.getPic3();
            String Pic4 = transfordate.getPic4();
            String Pic5 = transfordate.getPic5();
            String Pic6 = transfordate.getPic6();
            String cTime = transfordate.getcTime();
            
            Intent intent = new Intent();
            intent.putExtra("MobileNumber", MobileNumber);
            intent.putExtra("TagName", TagName);
            intent.putExtra("CoordinateX", CoordinateX);
            intent.putExtra("CoordinateY", CoordinateY);
            intent.putExtra("Adress", Adress);
            intent.putExtra("VideoName", VideoName);
            intent.putExtra("AudioName", AudioName);
            intent.putExtra("TextContents", TextContents);
            intent.putExtra("Pic1", Pic1);
            intent.putExtra("Pic2", Pic2);
            intent.putExtra("Pic3", Pic3);
            intent.putExtra("Pic4", Pic4);
            intent.putExtra("Pic5", Pic5);
            intent.putExtra("Pic6", Pic6);
            intent.putExtra("cTime", cTime);
            
            
            intent.setClass(mContext, AlarmInformationActivity.class);
            mContext.startActivity(intent);
            
        }
    }
}