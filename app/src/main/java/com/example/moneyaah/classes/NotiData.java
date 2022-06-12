package com.example.moneyaah.classes;

import java.util.ArrayList;
import java.util.List;

public class NotiData {

    private List<MyNotification> mNotiList;

    private NotiData() {
        mNotiList = new ArrayList<MyNotification>();
    }

    public static NotiData globInstance;
    public static NotiData getInstance() {
        if (globInstance == null) {
            globInstance = new NotiData();
        }
        return globInstance;
    }
}
