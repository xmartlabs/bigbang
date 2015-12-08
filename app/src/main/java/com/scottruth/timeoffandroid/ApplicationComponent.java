package com.scottruth.timeoffandroid;

import com.scottruth.timeoffandroid.controller.Controller;
import com.scottruth.timeoffandroid.controller.ServiceController;
import com.scottruth.timeoffandroid.controller.SessionController;
import com.scottruth.timeoffandroid.helper.DatabaseHelper;
import com.scottruth.timeoffandroid.helper.GeneralErrorHelper;
import com.scottruth.timeoffandroid.module.AndroidModule;
import com.scottruth.timeoffandroid.module.ControllerModule;
import com.scottruth.timeoffandroid.module.DatabaseModule;
import com.scottruth.timeoffandroid.module.GeneralErrorHelperModule;
import com.scottruth.timeoffandroid.module.ReceiverModule;
import com.scottruth.timeoffandroid.module.RestServiceModule;
import com.scottruth.timeoffandroid.ui.BaseActivity;
import com.scottruth.timeoffandroid.ui.BaseAppCompatActivity;
import com.scottruth.timeoffandroid.ui.BaseFragment;
import com.scottruth.timeoffandroid.ui.MainActivity;
import com.scottruth.timeoffandroid.ui.MainFragment;
import com.scottruth.timeoffandroid.ui.SingleFragmentActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by santiago on 06/10/15.
 */
@Singleton
@Component(modules = {
        AndroidModule.class,
        ControllerModule.class,
        DatabaseModule.class,
        GeneralErrorHelperModule.class,
        ReceiverModule.class,
        RestServiceModule.class,
})
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);
    void inject(BaseAppCompatActivity baseAppCompatActivity);
    void inject(MainActivity mainActivity);
    void inject(SingleFragmentActivity singleFragmentActivity);

    void inject(BaseFragment baseFragment);
    void inject(MainFragment mainFragment);

//    void inject(AuthController authController);
    void inject(Controller controller);
    void inject(ServiceController serviceController);
    void inject(SessionController sessionController);

    void inject(DatabaseHelper databaseHelper);
    void inject(GeneralErrorHelper generalErrorHelper);
}
