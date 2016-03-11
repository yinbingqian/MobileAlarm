package com.lnpdit.mobilealarm.utils.tag;

import java.util.List;

import com.lnpdit.mobilealarm.R;
import com.lnpdit.mobilealarm.entity.TagInfo;
import com.lnpdit.mobilealarm.page.activity.networkalarm.NetwortAlarmActivity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * @author fyales
 * @since 8/26/15.
 */
public class TagBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<TagInfo> mList;

    public TagBaseAdapter(Context context, List<TagInfo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public TagInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tagview, null);
            holder = new ViewHolder();
            holder.tagBtn = (ToggleButton) convertView.findViewById(R.id.tag_btn);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        final TagInfo text = getItem(position);
        holder.tagBtn.setText(text.getTagName());
        holder.tagBtn.setTextOff(text.getTagName());
        holder.tagBtn.setTextOn(text.getTagName());
        holder.tagBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    if(NetwortAlarmActivity.label_id.size()<3){                        
                        holder.tagBtn.setTextColor(Color.rgb(255, 255, 255));
                        NetwortAlarmActivity.label_id.add(text.getId());
                        NetwortAlarmActivity.label_name.add(text.getTagName());
                    }else{
                        Toast.makeText(mContext, "最多选择三个标签", Toast.LENGTH_SHORT).show();
                        holder.tagBtn.setChecked(false);
                        return;
                    }
                }else{
                    holder.tagBtn.setTextColor(Color.rgb(26, 207, 179));
                    for (int i = 0; i < NetwortAlarmActivity.label_id.size(); i++) {
                        if(NetwortAlarmActivity.label_name.get(i).equals(text.getTagName())){
                            NetwortAlarmActivity.label_id.remove(i);
                            NetwortAlarmActivity.label_name.remove(i);
                        }
                    }
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        ToggleButton tagBtn;
    }
}
