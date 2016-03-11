package com.lnpdit.mobilealarm.page.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lnpdit.mobilealarm.R;
import com.lnpdit.mobilealarm.entity.Picture;
import com.lnpdit.mobilealarm.page.activity.networkalarm.NetwortAlarmActivity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class ItemAdapter extends BaseAdapter{ 
        private LayoutInflater inflater; 
        private List<Picture> pictures; 
     
        public ItemAdapter(String[] titles, Context context) 
        { 
            super(); 
            pictures = new ArrayList<Picture>(); 
            inflater = LayoutInflater.from(context); 
            for (int i = 0; i < titles.length; i++) 
            { 
                Picture picture = new Picture(titles[i]); 
                pictures.add(picture); 
            } 
        } 
     
        @Override
        public int getCount() 
        { 
            if (null != pictures) 
            { 
                return pictures.size(); 
            } else
            { 
                return 0; 
            } 
        } 
     
        @Override
        public Object getItem(int position) 
        { 
            return pictures.get(position); 
        } 
     
        @Override
        public long getItemId(int position) 
        { 
            return position; 
        } 
     
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) 
        { 
            final ViewHolder viewHolder; 
            if (convertView == null) 
            { 
                convertView = inflater.inflate(R.layout.activity_network_labelitem, null); 
                viewHolder = new ViewHolder(); 
                viewHolder.title = (ToggleButton) convertView.findViewById(R.id.ItemText); 
                convertView.setTag(viewHolder); 
            } else
            { 
                viewHolder = (ViewHolder) convertView.getTag(); 
            } 
            viewHolder.title.setText(pictures.get(position).getTitle()); 
            viewHolder.title.setTextOn(pictures.get(position).getTitle());
            viewHolder.title.setTextOff(pictures.get(position).getTitle());
            viewHolder.title.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked){
                        viewHolder.title.setTextColor(Color.rgb(255, 255, 255));
//                        NetwortAlarmActivity.labelname_hash.put(position + "", pictures.get(position).getTitle());
                    }else{
                        viewHolder.title.setTextColor(Color.rgb(77, 77, 77));
//                        NetwortAlarmActivity.labelname_hash.remove(position + "");
                    }
                }
            });
            return convertView; 
        } 
     
    } 
     
    class ViewHolder 
    { 
        public ToggleButton title; 
    } 
     
     
