package com.example.baitap;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class save_thucan {
    private static final String PREF_NAME = "ThucAnPref";
    private static final String KEY_LIST = "DanhSachThucAn";

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
        String json = prefs.getString(KEY_LIST, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<thucanngay>>() {}.getType();
        if (json != null) {
            return gson.fromJson(json, type);
        }
        return new ArrayList<>();
    }
}
