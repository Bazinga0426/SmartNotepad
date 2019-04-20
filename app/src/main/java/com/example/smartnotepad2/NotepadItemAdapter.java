package com.example.smartnotepad2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;



public class NotepadItemAdapter extends BaseAdapter {
    List<NotepadBean> datas;
    Context context;
    LayoutInflater layoutInflater;

    public NotepadItemAdapter(Context context, List<NotepadBean> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  viewHolder;
        if(null==convertView){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.view_notepad_item,null);
            viewHolder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
            viewHolder.tv_content=(TextView)convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_time.setText(datas.get(position).getTime());
        viewHolder.tv_content.setText(formatLongText(datas.get(position).getContent(),14));
        return convertView;
    }
    private String formatLongText(String content, int lenth) {
        if (content.length() > lenth) {
            return content.substring(0, lenth - 1) + "...";
        } else {
            return content;
        }
    }
    public class ViewHolder{
        TextView tv_time;
        TextView tv_content;
    }
}
