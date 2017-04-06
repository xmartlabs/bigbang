package com.xmartlabs.template.helper;

import android.support.annotation.NonNull;

import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.controller.SessionController;
import com.xmartlabs.template.database.AppDataBase;
import com.xmartlabs.template.model.Session;

import javax.inject.Inject;

@SuppressWarnings("unused")
public class DatabaseHelper {
  @Inject
  SessionController sessionController;

  public DatabaseHelper() {
    BaseProjectApplication.getContext().inject(this);
  }

  public void deleteAll() {
    // TODO
  }

  public void migrate(@NonNull Session session) {
    if (session.getDatabaseVersion() == null || session.getDatabaseVersion() != AppDataBase.VERSION) { // Drop even if downgrading the version.
      deleteAll();
      session.setDatabaseVersion(AppDataBase.VERSION);
      sessionController.setSession(session);   }
  }
}
