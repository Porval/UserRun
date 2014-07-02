package com.jp.userrun.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jp.userrun.App;

public class Pref {
    private SharedPreferences mPref;
    private static final Pref sAppPref = new Pref(PreferenceManager.getDefaultSharedPreferences(App.get()));

    public Pref(SharedPreferences pref) {
        mPref = pref;
    }

    /**
     * 获得整个App的设置项目。
     *
     * @return Pref for this App.
     */
    public static Pref ofApp() {
        return sAppPref;
    }

    /**
     *
     * @param key
     * @return
     */
    public static Pref build(String key) {
        String name = String.format("key_%s", key);
        return new Pref(App.get().getSharedPreferences(name, Context.MODE_PRIVATE));
    }

    public SharedPreferences getPref() {
        return mPref;
    }

    public void clear() {
        mPref.edit().clear().commit();
    }

    public void set(String key, Object value) {
        SharedPreferences.Editor e = mPref.edit();
        if (value instanceof Boolean) {
            e.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            e.putFloat(key, (Float) value);
        } else if (value instanceof Integer) {
            e.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            e.putLong(key, (Long) value);
        } else if (value instanceof String) {
            e.putString(key, (String) value);
        } else if (value != null) {
            e.putString(key, JsonUtils.toJson(value));
        }
        e.commit();
    }

    public Object getObject(String key, Class<?> cls) {
        String json = getString(key);
        return JsonUtils.fromJson(json, cls);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPref.getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return mPref.getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mPref.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return mPref.getLong(key, defValue);
    }

    /**
     * default value empty string
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return mPref.getString(key, "");
    }

    public String getString(String key, String defValue) {
        return mPref.getString(key, defValue);
    }

    public String getString(String key, int defValueRes) {
        return mPref.getString(key, Res.getString(defValueRes));
    }

    public boolean contains(String key) {
        return mPref.contains(key);
    }

    public void remove(String key) {
        mPref.edit().remove(key).commit();
    }
}

