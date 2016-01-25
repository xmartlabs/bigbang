package com.xmartlabs.template.module;

import com.xmartlabs.template.helper.DatabaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiago on 09/11/15.
 */
@Module
public class DatabaseModule {
//    private static final String BUCKET_LOOKAROUND = "lookaround";

    @Provides
    @Singleton
    public DatabaseHelper provideDatabaseHelper() {
        return new DatabaseHelper();
    }


//    @Named(BUCKET_SWIPED_USERS)
//    @Provides
//    @Singleton
//    public Bucket<User> provideSwipedUsersBucket() {
//        return new Bucket<>(NO_SQL, User.class, BUCKET_SWIPED_USERS);
//    }
}
