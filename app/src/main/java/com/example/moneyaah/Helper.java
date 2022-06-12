package com.example.moneyaah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.moneyaah.classes.Record;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Helper {

    public static void saveUser(Context context, String user) {
        String[] users = user.split("@");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USERNAME, users[0]);
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
        Log.d("LOCATION", location);
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
        Log.d("LOCATION", location);
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
