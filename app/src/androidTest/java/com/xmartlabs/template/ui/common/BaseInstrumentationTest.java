package com.xmartlabs.template.ui.common;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.annotation.CallSuper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import com.annimon.stream.Objects;
import com.xmartlabs.template.BaseProjectApplication;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.Locale;

import javax.inject.Inject;

import io.appflate.restmock.RESTMockServer;
import lombok.Getter;
/**
 * Created by medina on 21/09/2016.
 */
@RunWith(AndroidJUnit4.class)
public abstract class BaseInstrumentationTest {
  @Getter
  private Instrumentation instrumentation;

  @Before
  @CallSuper
  public void setUp() {
    instrumentation = InstrumentationRegistry.getInstrumentation();
    BaseProjectApplication app = (BaseProjectApplication) instrumentation.getTargetContext().getApplicationContext();
    app.inject(this);
    RESTMockServer.reset();
  }

  protected void sleep() {
    sleep(1);
  }

  protected void sleep(int seconds) {
    try {
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public Activity getActivityInstance() {
    final Activity[] currentActivity = new Activity[1];
    getInstrumentation().runOnMainSync(() -> {
      Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
      if (resumedActivities.iterator().hasNext()) {
        currentActivity[0] = (Activity) resumedActivities.iterator().next();
      }
    });

    return currentActivity[0];
  }

  protected void assertCurrentActivityIs(Class<? extends Activity> activityClass) throws Throwable {
    sleep();
    Class<? extends Activity> currentActivityName = getActivityInstance().getClass();
    if (!Objects.equals(activityClass, activityClass)) {
      throw new IllegalStateException(
          String.format(
              Locale.US,
              "Expected %s but the current activity is %s",
              activityClass,
              currentActivityName
          )
      );
    }
  }
}