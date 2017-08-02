package com.xmartlabs.template.database

import com.raizlabs.android.dbflow.annotation.Database

@Database(name = AppDataBase.NAME, version = AppDataBase.VERSION)
object AppDataBase {
  const val NAME = "base_project_database"
  const val VERSION = 1
}
