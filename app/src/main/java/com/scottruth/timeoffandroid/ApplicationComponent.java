package com.scottruth.timeoffandroid;

import com.scottruth.timeoffandroid.controller.SessionController;
import com.scottruth.timeoffandroid.controller.demo.DemoController;
import com.scottruth.timeoffandroid.helper.DatabaseHelper;
import com.scottruth.timeoffandroid.helper.GeneralErrorHelper;
import com.scottruth.timeoffandroid.module.AndroidModule;
import com.scottruth.timeoffandroid.module.ControllerModule;
import com.scottruth.timeoffandroid.module.DatabaseModule;
import com.scottruth.timeoffandroid.module.GeneralErrorHelperModule;
import com.scottruth.timeoffandroid.module.ReceiverModule;
import com.scottruth.timeoffandroid.module.RestServiceModule;
import com.scottruth.timeoffandroid.module.SessionInterceptor;
import com.scottruth.timeoffandroid.ui.DrawerHeaderFragment;
import com.scottruth.timeoffandroid.ui.MainActivity;
import com.scottruth.timeoffandroid.ui.SettingsActivity;
import com.scottruth.timeoffandroid.ui.SettingsFragment;
import com.scottruth.timeoffandroid.ui.StartActivity;
import com.scottruth.timeoffandroid.ui.WelcomeActivity;
import com.scottruth.timeoffandroid.ui.WelcomeFragment;
import com.scottruth.timeoffandroid.ui.demo.DemoDrawerItemFragment;
import com.scottruth.timeoffandroid.ui.demo.RepoDetailActivity;
import com.scottruth.timeoffandroid.ui.demo.RepoDetailFragment;
import com.scottruth.timeoffandroid.ui.demo.ReposListFragment;

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
    // FIXME: DON'T inject base classes. Dagger may use the base class definition to inject the
    // dependencies in some cases instead of the concrete class making injected members be always null

//    void inject(BaseActivity baseActivity);
//    void inject(BaseAppCompatActivity baseAppCompatActivity);
//    void inject(SingleFragmentActivity singleFragmentActivity);

    void inject(MainActivity mainActivity);
    void inject(RepoDetailActivity repoDetailActivity);
    void inject(StartActivity startActivity);
    void inject(SettingsActivity settingsActivity);
    void inject(WelcomeActivity welcomeActivity);

//    void inject(BaseFragment baseFragment);
//    void inject(FragmentWithDrawer fragmentWithDrawer);
//    void inject(ValidatableFragment validatableFragment);

    void inject(DemoDrawerItemFragment demoDrawerItemFragment);
    void inject(DrawerHeaderFragment drawerHeaderFragment);
    void inject(RepoDetailFragment repoDetailFragment);
    void inject(ReposListFragment reposListFragment);
    void inject(SettingsFragment settingsFragment);
    void inject(WelcomeFragment welcomeFragment);

//    void inject(Controller controller);
//    void inject(ServiceController serviceController);

    //    void inject(AuthController authController);
    void inject(DemoController demoController);
    void inject(SessionController sessionController);

    void inject(SessionInterceptor sessionInterceptor);

    void inject(DatabaseHelper databaseHelper);
    void inject(GeneralErrorHelper generalErrorHelper);
}
