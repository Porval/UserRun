package com.jp.userrun.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jp.userrun.App;
import com.jp.userrun.model.Toy;
import com.jp.userrun.utils.Logger;

import org.androidannotations.annotations.EBean;

import java.sql.SQLException;

@EBean(scope = EBean.Scope.Singleton)
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "userrun.db";
    private static final int DATABASE_VERSION = 0;

    static DatabaseHelper sDbHelper = new DatabaseHelper(App.get());

    static {
        try {
            sDbHelper.getConnectionSource().getReadWriteConnection();
        } catch (SQLException e) {
            Logger.e(TAG, e);
        }
    }

    public static DatabaseHelper getInstance() {
        return sDbHelper;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource source) {
        createTable(source, Toy.class);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource source, int oldVersion, int newVersion) {
        Logger.d(TAG, "Updating database. oldVersion=" + oldVersion + " newVersion=" + newVersion);
    }

    private void createTable(ConnectionSource source, Class<?> cls) {
        try {
            TableUtils.createTable(source, cls);
        } catch (SQLException e) {
            Logger.e(TAG, e);
        }
    }
}
