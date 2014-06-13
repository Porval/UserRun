package com.jp.userrun;

import android.app.Activity;
import android.os.Bundle;

import org.androidannotations.annotations.EActivity;


@EActivity(R.layout.act_home)
public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
    }
}
