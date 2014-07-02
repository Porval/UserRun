package com.jp.userrun.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    // DEBUG在App启动的时候会读取AndroidManifest.xml里边的DBLOG的meta信息，
    // 来确定是设置成true或者false。
    // 平时调试时设置成true，qaci打包的时候会设置成false。
    public static boolean DEBUG = false;

    private static long sLastTimeStamp = 0L;

    private static final ThreadLocal<SimpleDateFormat> sDateFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss.SSS");
        }
    };

    private static String getTag(String tag) {
        return String.format(":DOUBAN:%s:(%s):%s:",
                sDateFormatter.get().format(new Date(System.currentTimeMillis())),
                Thread.currentThread().getName(),
                tag);
    }

    private static String getMessage(String msg, Object... params) {
        try {
            return String.format(msg, params);
        } catch (Throwable e) {
            return Log.getStackTraceString(e);
        }
    }

    public static int i(String tag, String msg, Object... params) {
        return DEBUG ? Log.i(getTag(tag), getMessage(msg, params)) : 0;
    }

    public static int d(String tag, String msg, Object... params) {
        return DEBUG ? Log.d(getTag(tag), getMessage(msg, params)) : 0;
    }

    public static int v(String tag, String msg, Object... params) {
        return DEBUG ? Log.v(getTag(tag), getMessage(msg, params)) : 0;
    }

    public static int e(String tag, String msg, Object... params) {
        return DEBUG ? Log.e(getTag(tag), getMessage(msg, params)) : 0;
    }

    public static int w(String tag, String msg, Object... params) {
        return DEBUG ? Log.w(getTag(tag), getMessage(msg, params)) : 0;
    }

    public static int e(String tag, Throwable e) {
        return DEBUG ? Log.e(getTag(tag), Log.getStackTraceString(e)) : 0;
    }

    public static int e(String tag, Throwable e, String msg, Object... params) {
        return DEBUG ? Log.e(getTag(tag), getMessage(msg, params) + "\n" + Log.getStackTraceString(e)) : 0;
    }

    public static int t(String tag, String msg, Object... params) {
        if (DEBUG) {
            long currentTimeStamp = System.currentTimeMillis();
            long diff = currentTimeStamp - sLastTimeStamp;
            int result = Log.d(getTag(tag), "" + currentTimeStamp
                    + ((sLastTimeStamp > 0) ? " (+" + diff + "ms) " : " ")
                    + getMessage(msg, params));
            sLastTimeStamp = currentTimeStamp;
            return result;
        }
        return 0;
    }

    public static void printStackTrace(String tag) {
        if (!DEBUG) {
            return;
        }
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        Logger.d(tag, "\n---------------------");
        // 不需要打印前三项，因为他们都是Logger.printStackTrace自身相关的信息
        for (int i = 3; i < stackTraceElements.length; i++) {
            StackTraceElement element = stackTraceElements[i];
            Logger.d(tag, "    " + element.getClassName() + "." + element.getMethodName() +
                    "(" + element.getFileName() + ":" + element.getLineNumber() + ")");
        }
        Logger.d(tag, "---------------------\n");
    }

    public static class Feedback {
        public static String content = "";

        public static void d(String tag, String msg) {
            content += "\n" + tag + " " + msg;
        }

        public static void e(String tag, Throwable e) {
            content += "\n" + tag + Log.getStackTraceString(e);
        }

        public static void clear() {
            content = "";
        }

        public static String get() {
            return content;
        }
    }

}
