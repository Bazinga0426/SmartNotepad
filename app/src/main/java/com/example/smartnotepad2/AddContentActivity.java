package com.example.smartnotepad2;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
/**
 * Created by wanghonggang on 2018/6/29.
 */


//新建主界面
public class AddContentActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_save;
    private TextView tv_date;
    private TextView tv_time;
    private TextView tv_cancel;
    private EditText et_content;
    private String time = "";
    private String date = "";
    private Bundle bundle;
    private int type;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (item.getItemId()) {

            case R.id.remove_item:
                Snackbar snackbar = Snackbar.make(
                        findViewById(R.id.tv_cancel), "You can enter content and adjust the alarm clock to remind yourself.",
                        Snackbar.LENGTH_LONG);
                snackbar.show();
                break;
            case R.id.about_us:
                Intent intent =new Intent(AddContentActivity.this,MemberActivity.class);
                startActivity(intent);
            break;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);
        bundle = getIntent().getExtras();
        type = bundle.getInt(GlobalParams.TYPE_KEY);

        findViews();
        setListeners();
        initDate();
    }

    private void findViews() {//查找返回视图
        et_content = findViewById(R.id.et_content);
        tv_save = findViewById(R.id.tv_save);
        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
        tv_cancel = findViewById(R.id.tv_cancel);
    }

    private void setListeners() {
        tv_save.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    private void initDate() {
        Calendar c = Calendar.getInstance();//创建一个单例模式创建的实例，返回相同的对象
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        date = getDate(year, month, day);
        if (type == GlobalParams.TYPE_EDIT) {
            NotepadWithDataBean notepadWithDataBean = (NotepadWithDataBean) (bundle.getSerializable(GlobalParams.BEAN_KEY));
            et_content.setText(notepadWithDataBean.getNotepadBeenList().get(bundle.getInt(GlobalParams.ITEM_POSITION_KEY)).getContent());
            date = notepadWithDataBean.getData() + "";
            tv_date.setText(date);
            time = notepadWithDataBean.getNotepadBeenList().get(bundle.getInt(GlobalParams.ITEM_POSITION_KEY)).getTime();
            tv_time.setText(time);
        }
    }

    private String getDate(int year, int month, int day) {
        String date = "";
        date += year;
        if (month < 9) {
            date = date + "0" + (month + 1);
        } else {
            date += (month + 1);
        }
        if (day < 10) {
            date = date + "0" + day;
        } else {
            date += day;
        }
        return date;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save:


                if (type == GlobalParams.TYPE_EDIT) {
                    update();
                } else {
                    save();
                }
                break;

            case R.id.tv_date:
                selectDateDialog();
                break;
            case R.id.tv_time:
                selectTimeDialog();
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    private void selectDateDialog() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(AddContentActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = getDate(year, monthOfYear, dayOfMonth);
                tv_date.setText(date);
            }
        }, year, month, day).show();
    }

    private void selectTimeDialog() {
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        new TimePickerDialog(AddContentActivity.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {
                        time = formatTime(hourOfDay, minute);
                        tv_time.setText(time);
                    }
                }, mHour, mMinute, true).show();
    }

    private String formatTime(int hour, int minute) {
        String time = hour + ":";
        if (minute < 10) {
            time = time + "0" + minute;
        } else {
            time += minute;
        }
        return time;
    }

    private void save() {
        String content = et_content.getText().toString();
        if ("".equals(content)) {
            Toast.makeText(AddContentActivity.this, "Please enter the content. ", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(time)) {
            Toast.makeText(AddContentActivity.this, "Please set the time.", Toast.LENGTH_SHORT).show();
            return;
        }
        NotepadBean notepadBean = new NotepadBean();
        notepadBean.setContent(content);
        notepadBean.setDate(Integer.parseInt(date));
        notepadBean.setTime(time);
        DataHelper helper = new DataHelper(AddContentActivity.this);
        helper.insertData(notepadBean);
        setResult(GlobalParams.ADD_RESULT_OK);

        ProgressDialog progressDialog = new ProgressDialog(AddContentActivity.this);
        progressDialog.setTitle("Saving the content.");
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.dismiss();
        finish();
    }

    private void update() {
        DataHelper helper = new DataHelper(AddContentActivity.this);
        NotepadWithDataBean bean = (NotepadWithDataBean) (bundle.getSerializable(GlobalParams.BEAN_KEY));
        int itemPosition = bundle.getInt(GlobalParams.ITEM_POSITION_KEY);
        helper.update(bean.getNotepadBeenList().get(itemPosition).getId(), date, time, et_content.getText().toString());
        setResult(GlobalParams.ADD_RESULT_OK);
        finish();
    }
}
