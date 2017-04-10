package com.xmartlabs.base.ui;

import com.xmartlabs.base.ui.module.AndroidModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    AndroidModule.class,
})
public interface UIComponent {

}
