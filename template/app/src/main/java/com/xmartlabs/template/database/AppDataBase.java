package com.xmartlabs.template.database;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDataBase.NAME, version = AppDataBase.VERSION)
public class AppDataBase {
  //TODO: change db name
  public static final String NAME = "template_database";
  public static final int VERSION = 1;
}
