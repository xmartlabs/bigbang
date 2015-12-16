package com.scottruth.timeoffandroid.helper;

import android.support.annotation.NonNull;

import com.scottruth.timeoffandroid.TimeOffApplication;
import com.scottruth.timeoffandroid.controller.SessionController;
import com.scottruth.timeoffandroid.model.Session;

import javax.inject.Inject;

/**
 * Created by santiago on 09/11/15.
 */
public class DatabaseHelper {
//    @Inject
//    Bucket<Lookaround> lookaroundBucket;
    @Inject
    SessionController sessionController;

    public DatabaseHelper() {
        TimeOffApplication.getContext().inject(this);
    }

    public void deleteAll() {
//        lookaroundBucket
//                .newQuery()
//                .delete();
    }

    public void migrate(@NonNull Session session) {
        if (session.getDatabaseVersion() == null || session.getDatabaseVersion() != Session.CURRENT_DATABASE_VERSION) { // Drop even if downgrading the version.
            deleteAll();
            session.setDatabaseVersion(Session.CURRENT_DATABASE_VERSION);
            sessionController.setSession(session);
        }
    }
}
