package com.xmartlabs.template;

import com.xmartlabs.template.module.AndroidModule;

import bullet.ObjectGraph;

/**
 * Created by medina on 21/09/2016.
 */
public class TestApplication extends BaseProjectApplication {
  @Override
  protected ApplicationComponent createComponent() {
    return DaggerTestComponent.builder()
        .androidModule(new AndroidModule(this))
        .build();
  }

  @Override
  protected ObjectGraph createBullet(ApplicationComponent component) {
    return new BulletTestComponent((TestComponent) component);
  }
}
