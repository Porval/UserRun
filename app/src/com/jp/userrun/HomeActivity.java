package com.jp.userrun;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.act_home)
public class HomeActivity extends Activity {

    @ViewById(R.id.list)
    public ListView mListView;

    @AfterViews
    void init() {

    }

    @Background
    void loadPluginData() {

    }


}
