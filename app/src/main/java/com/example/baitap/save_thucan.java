package com.example.baitap;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class save_thucan {
    private static final String PREF_NAME = "THUCAN_PREF";
    private static final String KEY_LIST = "LIST_THUCAN";

    public static void saveList(Context context, ArrayList<thucanngay> list) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(KEY_LIST, json);
        editor.apply();
    }

    public static ArrayList<thucanngay> loadList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(KEY_LIST, null);
        Type type = new TypeToken<ArrayList<thucanngay>>() {}.getType();
        ArrayList<thucanngay> list = gson.fromJson(json, type);
        if (list == null) list = new ArrayList<>();
        return list;
    }
}
