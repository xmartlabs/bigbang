package com.xmartlabs.template;

import com.xmartlabs.template.module.AndroidModule;
import com.xmartlabs.template.module.MockRestServiceModule;

import bullet.ObjectGraph;

public class TestApplication extends BaseProjectApplication {
  @Override
  protected ApplicationComponent createComponent() {
    return DaggerTestComponent.builder()
        .coreAndroidModule(new AndroidModule(this))
        .restServiceModule(new MockRestServiceModule())
        .build();
  }

  @Override
  protected ObjectGraph createBullet(ApplicationComponent component) {
    return new BulletTestComponent((TestComponent) component);
  }
}
