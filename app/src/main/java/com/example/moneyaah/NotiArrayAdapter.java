package com.example.moneyaah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moneyaah.classes.Notification;

import java.util.List;

public class NotiArrayAdapter extends ArrayAdapter<Notification> {

    List<Notification> mNotificationList;

    public NotiArrayAdapter(@NonNull Context context, List<Notification> notificationList) {
        super(context, R.layout.list_item_noti, notificationList);

        this.mNotificationList = notificationList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_noti, parent, false);

        TextView title = rowView.findViewById(R.id.noti_title);
        title.setText(mNotificationList.get(position).getTitle());

        TextView content = rowView.findViewById(R.id.noti_content);
        content.setText(mNotificationList.get(position).getContent());

        return rowView;
    }
}
