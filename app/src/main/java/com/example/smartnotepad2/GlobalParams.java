package com.example.smartnotepad2;

/**
 * Created by wanghonggang on 2018/6/29.
 */
/**在类中定义静态的要传递的参数**/
public class GlobalParams {
    public static final String START_PHONE_ACTION="android.intent.action.BOOT_COMPLETED";

    public static final String DB_NAME="smart_notepad";
    public static final String DB_VALUE_ID_KEY="id";
    public static final String DB_VALUE_CONTENT_KEY="content";
    public static final String DB_VALUE_DATE_KEY="date";
    public static final String DB_VALUE_TIME_KEY="time";

    public static final int ADD_RESULT_OK=1;
    public static final int ADD_REQUEST=1;
    public static final String BEAN_KEY="bean_key";
    public static final String TYPE_KEY="type_key";
    public static final int TYPE_ADD=1;
    public static final int TYPE_EDIT=2;
    public static final String PARENT_POSITION_KEY="parent_position_key";
    public static final String ITEM_POSITION_KEY="item_position_key";
    public static final String CONTENT_KEY="content_key";

    public static final String START_REMIND_ACTIVITY_ACTION="start_remind_activity_actioin";
}
