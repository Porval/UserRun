package com.jp.userrun.database;

import com.jp.userrun.model.Toy;

import java.util.ArrayList;
import java.util.List;

public class UserRunDB {

    private static DatabaseHelper sDBHelper = DatabaseHelper.getInstance();

    public List<Toy> getToyList() {
        ArrayList<Toy> list = new ArrayList<Toy>();

        return list;
    }

}
