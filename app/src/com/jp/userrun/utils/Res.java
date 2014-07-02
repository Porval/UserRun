package com.jp.userrun.utils;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import com.jp.userrun.App;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 这是android.context.res.Resources的static版，方便使用。
 */
public class Res {
    private static final String TAG = Res.class.getName();

    private static final Resources sRes = App.get().getResources();

    public static Resources get() {
        return sRes;
    }

    public static int getColor(int id) {
        return sRes.getColor(id);
    }

    public static float getDimension(int id) {
        return sRes.getDimension(id);
    }

    public static float getDimensionPixelSize(int id) {
        return sRes.getDimensionPixelSize(id);
    }

    public static Drawable getDrawable(int id) {
        return sRes.getDrawable(id);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return sRes.getDisplayMetrics();
    }

    public static String getString(int id) {
        return sRes.getString(id);
    }

    public static String getString(int id, Object... formatArgs) {
        return sRes.getString(id, formatArgs);
    }

    public static List<String> getStringArray(int id) {
        List<String> itemStrings = new ArrayList<String>();
        TypedArray array = sRes.obtainTypedArray(id);
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                itemStrings.add(array.getString(i));
            }
        }
        return itemStrings;
    }

    public static List<Integer> getIntegerArray(int id) {
        List<Integer> itemIds = new ArrayList<Integer>();

        TypedArray array = sRes.obtainTypedArray(id);
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                itemIds.add(array.getResourceId(i, 0));
            }
        }

        return itemIds;
    }

    public static String readAssertFileContent(String fileName) {
        InputStream stream = null;
        try {
            stream = sRes.getAssets().open(fileName);
            return IOUtils.convertStreamToString(stream);
        } catch (Exception e) {
            Logger.e(TAG, e);
        } finally {
            IOUtils.closeSilently(stream);
        }

        return "";
    }
}