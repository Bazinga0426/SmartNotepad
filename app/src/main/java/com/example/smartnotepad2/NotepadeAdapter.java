package com.example.smartnotepad2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;



public class NotepadeAdapter extends BaseAdapter {
    Context mContext;
    List<NotepadWithDataBean> datas;
    LayoutInflater layoutInflater;
    ClickFunction clickFunction;
    public NotepadeAdapter(Context context,List<NotepadWithDataBean> datas,ClickFunction clickFunction){
        this.mContext=context;
        this.datas=datas;
        this.clickFunction=clickFunction;
        this.layoutInflater=LayoutInflater.from(context);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(null==convertView){
            convertView=layoutInflater.inflate(R.layout.view_notepad,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_date=(TextView)convertView.findViewById(R.id.tv_date);
            viewHolder.lv_list=(ListView)convertView.findViewById(R.id.lv_list);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.tv_date.setText(datas.get(position).getData()+"");
        NotepadItemAdapter adapter=new NotepadItemAdapter(mContext,datas.get(position).getNotepadBeenList());
        viewHolder.lv_list.setAdapter(adapter);
        viewHolder.lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int itemposition, long id) {
                clickFunction.clickItem(position,itemposition);
            }
        });
        viewHolder.lv_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int itemposition, long id) {
                clickFunction.longClickItem(position,itemposition);
                return false;
            }
        });
        setListViewHeightBasedOnChildren(viewHolder.lv_list);
        return convertView;
    }

    public  void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    public class ViewHolder{
        TextView tv_date;
        ListView lv_list;
    }
    public interface ClickFunction{
        void clickItem(int position,int itemPosition);
        void longClickItem(int position,int itemPostion);
    }
}
