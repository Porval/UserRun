package com.jp.userrun.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jp.userrun.database.Table;
import com.jp.userrun.database.Table.ToyColumn;

@DatabaseTable(tableName = Table.TOY)
public class Toy {

    @SerializedName(ToyColumn.ID)
    @DatabaseField(columnName = ToyColumn.ID, id = true)
    public int id;

    @SerializedName(ToyColumn.NAME)
    @DatabaseField(columnName = ToyColumn.NAME)
    public String name;

    @SerializedName(ToyColumn.STATUS)
    @DatabaseField(columnName = ToyColumn.STATUS)
    public int status;

    @SerializedName(ToyColumn.IS_PLUGIN)
    @DatabaseField(columnName = ToyColumn.IS_PLUGIN)
    public int isPlugin;

    @SerializedName(ToyColumn.LAST_OPERATION_TIME)
    @DatabaseField(columnName = ToyColumn.LAST_OPERATION_TIME)
    public long lastOperationTime;

    @SerializedName(ToyColumn.ICON)
    @DatabaseField(columnName = ToyColumn.ICON)
    public String icon;
}
