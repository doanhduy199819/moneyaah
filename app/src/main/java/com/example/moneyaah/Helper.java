package com.example.moneyaah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Helper {
    public static void saveUser(Context context, String user) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USERNAME, user);
        editor.apply();
    }

    public static String getUsername(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.USERNAME, "");
    }

    public static void updateData(String location, String value) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(location);
        database.setValue(value);
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public static void navigate(Context context1, Class<?> context2, Context context) {
        Intent intent = new Intent(context1, context2);
        context.startActivity(intent);
    }

}
