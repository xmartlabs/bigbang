package com.scottruth.timeoffandroid.controller;

import com.scottruth.timeoffandroid.TimeOffApplication;

/**
 * Created by santiago on 17/09/15.
 */
public abstract class Controller {
    public Controller() {
        TimeOffApplication.getContext().inject(this);
    }
}
