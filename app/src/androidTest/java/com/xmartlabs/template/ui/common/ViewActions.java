package com.xmartlabs.template.ui.common;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * Created by medina on 21/09/2016.
 */
public class ViewActions {
  public static ViewAction nestedScrollViewScrollTo() {
    return android.support.test.espresso.action.ViewActions.actionWithAssertions(
        new ScrollToActionForNestedScrollView());
  }

  // http://stackoverflow.com/a/33516360/5170805
  public static ViewAction withCustomConstraints(final ViewAction action, final Matcher<View> constraints) {
    return new ViewAction() {
      @Override
      public Matcher<View> getConstraints() {
        return constraints;
      }

      @Override
      public String getDescription() {
        return action.getDescription();
      }

      @Override
      public void perform(UiController uiController, View view) {
        action.perform(uiController, view);
      }
    };
  }
}
