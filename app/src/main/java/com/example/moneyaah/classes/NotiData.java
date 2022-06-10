package com.example.moneyaah.classes;

import java.util.ArrayList;
import java.util.List;

public class NotiData {

    private List<Notification> mNotiList;

    private NotiData() {
        mNotiList = new ArrayList<Notification>();
    }

    public static NotiData globInstance;
    public static NotiData getInstance() {
        if (globInstance == null) {
            globInstance = new NotiData();
        }
        return globInstance;
    }
}
