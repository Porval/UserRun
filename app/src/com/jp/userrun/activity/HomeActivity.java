package com.jp.userrun.activity;

import android.app.Activity;
import android.widget.ListView;

import com.jp.userrun.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.act_home)
public class HomeActivity extends Activity {

    @ViewById(R.id.list)
    public ListView mListView;

}
