package com.example.smartnotepad2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghonggang on 2018/6/29.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NotepadeAdapter.ClickFunction {

    private TextView tv_add;
    private ListView lv_contents;
    private List<NotepadWithDataBean> notepadWithDataBeanList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setListeners();
        initView();

    }

    private void findViews() {
        tv_add = (TextView) findViewById(R.id.tv_add);
        lv_contents = (ListView) findViewById(R.id.lv_content);
    }

    private void setListeners() {
        tv_add.setOnClickListener(this);
    }

    private void initView() {
        DataHelper helper = new DataHelper(MainActivity.this);
        notepadWithDataBeanList = new ArrayList<NotepadWithDataBean>();
        List<NotepadBean> notepadBeanList = helper.getNotepadList();
        for (int i = 0; i < notepadBeanList.size(); i++) {
            if (0 == notepadWithDataBeanList.size()) {
                NotepadWithDataBean notepadWithDataBean = new NotepadWithDataBean();
                notepadWithDataBean.setData(notepadBeanList.get(0).getDate());
                notepadWithDataBeanList.add(notepadWithDataBean);
            }
            boolean flag = true;
            for (int j = 0; j < notepadWithDataBeanList.size(); j++) {
                int date = notepadWithDataBeanList.get(j).getData();
                if (date == notepadBeanList.get(i).getDate()) {
                    notepadWithDataBeanList.get(j).getNotepadBeenList().add(notepadBeanList.get(i));
                    flag = false;
                    break;
                }
            }
            if (flag) {
                NotepadWithDataBean notepadWithDataBean = new NotepadWithDataBean();
                notepadWithDataBean.setData(notepadBeanList.get(i).getDate());
                notepadWithDataBeanList.add(notepadWithDataBean);
                notepadWithDataBeanList.get(notepadWithDataBeanList.size() - 1).getNotepadBeenList().add(notepadBeanList.get(i));
            }
        }
        NotepadeAdapter adapter = new NotepadeAdapter(MainActivity.this, notepadWithDataBeanList, this);
        lv_contents.setAdapter(adapter);
        // setListViewHeightBasedOnChildren(lv_contents);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(GlobalParams.TYPE_KEY, GlobalParams.TYPE_ADD);
                intent.putExtras(bundle);
                intent.setClass(MainActivity.this, AddContentActivity.class);
                startActivityForResult(intent, GlobalParams.ADD_REQUEST);
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GlobalParams.ADD_REQUEST:
                if (GlobalParams.ADD_RESULT_OK == resultCode) {
                    initView();
                }
                break;
        }
    }

    @Override
    public void clickItem(int position, int itemPosition) {
        Bundle bundle = new Bundle();
        bundle.putInt(GlobalParams.TYPE_KEY, GlobalParams.TYPE_EDIT);
        bundle.putSerializable(GlobalParams.BEAN_KEY, notepadWithDataBeanList.get(position));
        bundle.putInt(GlobalParams.ITEM_POSITION_KEY, itemPosition);
        Intent intent = new Intent(this, AddContentActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, GlobalParams.ADD_REQUEST);
    }

    

    @Override
    public void longClickItem(final int position, final int itemPostion) {


        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        String []items = new String[]{"delete","Create"};
        builder2.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("确认删除吗？");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataHelper helper = new DataHelper(MainActivity.this);
                            helper.deleteNotepad(notepadWithDataBeanList.get(position).getNotepadBeenList().get(itemPostion).getId());
                            initView();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            }
        });
        builder2.create().show();

    }


}
