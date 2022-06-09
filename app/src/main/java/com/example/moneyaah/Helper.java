package com.example.moneyaah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Helper {
    public static void saveUser(Context context, String user) {
        String[] values = user.split("@");
        user = values[0];
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USERNAME, user);
        editor.apply();
    }

    public static String getUsername(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.USERNAME, "");
    }

    public static void updateNumber(String location, Double value) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(location);
        database.setValue(value);
    }

    public static void updateObject(String location, Record record) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(location);
        database.push().setValue(record);
    }

    public static void updateString(String location, String value) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(location);
        database.setValue(value);
    }

    public static void updateBoolean(String location, Boolean value) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(location);
        database.setValue(value);
    }

    public static DatabaseReference getDataRef(String location) {
        return FirebaseDatabase.getInstance().getReference(location);
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public static void navigate(Context context1, Class<?> context2, Context context) {
        Intent intent = new Intent(context1, context2);
        context.startActivity(intent);
    }

}
