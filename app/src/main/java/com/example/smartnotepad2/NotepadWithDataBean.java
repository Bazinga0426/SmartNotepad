package com.example.smartnotepad2;

import com.example.smartnotepad2.NotepadBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghonggang on 2018/6/29.
 */

public class NotepadWithDataBean implements Serializable {
    private int data;
    private List<NotepadBean> notepadBeenList;
    public NotepadWithDataBean(){
     notepadBeenList=new ArrayList<NotepadBean>();
    }
    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public List<NotepadBean> getNotepadBeenList() {
        return notepadBeenList;
    }

    public void setNotepadBeenList(List<NotepadBean> notepadBeenList) {
        this.notepadBeenList = notepadBeenList;
    }
}
