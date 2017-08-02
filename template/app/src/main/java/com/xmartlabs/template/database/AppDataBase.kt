package com.xmartlabs.template.database

import com.raizlabs.android.dbflow.annotation.Database

@Database(name = AppDataBase.NAME, version = AppDataBase.VERSION)
object AppDataBase {
  //TODO: change db name
  const val NAME = "template_database"
  const val VERSION = 1
}
